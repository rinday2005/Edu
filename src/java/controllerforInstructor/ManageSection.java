/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerforInstructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.Sections;
import model.User;
import model.Courses;
import service.SectionsService;
import service.CourseServiceImpl;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageSection", urlPatterns = {"/ManageSection"})
public class ManageSection extends HttpServlet {

    SectionsService sectionsService = new SectionsService();
    CourseServiceImpl courseService = new CourseServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            listSections(request, response);
            return;
        }

        switch (action) {
            case "list":
                listSections(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteSection(request, response);
                break;
            default:
                listSections(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                createSection(request, response);
                break;
            case "update":
                updateSection(request, response);
                break;
            case "delete":
                deleteSection(request, response);
                break;
            default:
                listSections(request, response);
                break;
        }
    }

    private void listSections(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            UUID userID = getUserIdFromSession(session);
            if (userID == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            // Lấy courseID từ parameter
            String courseIdParam = request.getParameter("course");
            UUID courseID = null;
            Courses course = null;
            
            if (courseIdParam != null && !courseIdParam.isEmpty()) {
                try {
                    courseID = UUID.fromString(courseIdParam);
                    // Lấy thông tin course
                    List<Courses> allCourses = courseService.getAllCourses();
                    for (Courses c : allCourses) {
                        if (c.getCourseID().equals(courseID)) {
                            course = c;
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Nếu không phải UUID, có thể là course name
                    List<Courses> allCourses = courseService.getAllCourses();
                    for (Courses c : allCourses) {
                        if (c.getName() != null && c.getName().equals(courseIdParam)) {
                            course = c;
                            courseID = c.getCourseID();
                            break;
                        }
                    }
                }
            }

            // Lấy danh sách sections theo courseID (nếu có) hoặc của instructor
            List<Sections> sections;
            if (courseID != null) {
                sections = sectionsService.findByCourseId(courseID);
                // Filter theo userID của instructor
                List<Sections> filteredSections = new ArrayList<>();
                for (Sections s : sections) {
                    if (s.getUserID() != null && s.getUserID().equals(userID)) {
                        filteredSections.add(s);
                    }
                }
                sections = filteredSections;
            } else {
                // Lấy tất cả sections của instructor
                sections = sectionsService.findAll();
                List<Sections> filteredSections = new ArrayList<>();
                for (Sections s : sections) {
                    if (s.getUserID() != null && s.getUserID().equals(userID)) {
                        filteredSections.add(s);
                    }
                }
                sections = filteredSections;
            }

            // Lấy danh sách tất cả courses để hiển thị course name
            List<Courses> allCourses = courseService.getAllCourses();

            request.setAttribute("sections", sections);
            request.setAttribute("course", course);
            request.setAttribute("courseID", courseID);
            request.setAttribute("allCourses", allCourses);
            request.setAttribute("fromServlet", "true");
            
            request.getRequestDispatcher("/instructor/jsp/CourseSession.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách chương: " + e.getMessage());
            request.setAttribute("fromServlet", "true");
            request.getRequestDispatcher("/instructor/jsp/CourseSession.jsp").forward(request, response);
        }
    }

    private void createSection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            UUID userID = getUserIdFromSession(session);
            if (userID == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            String name = request.getParameter("sectionName");
            String description = request.getParameter("sectionDescription");
            String courseIdParam = request.getParameter("courseID");
            String statusStr = request.getParameter("status");

            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Tên chương không được để trống!");
                listSections(request, response);
                return;
            }

            UUID courseID = null;
            if (courseIdParam != null && !courseIdParam.isEmpty()) {
                courseID = UUID.fromString(courseIdParam);
            }

            boolean status = true; // Default active
            if (statusStr != null) {
                status = Boolean.parseBoolean(statusStr);
            }

            Sections section = new Sections();
            section.setSectionID(UUID.randomUUID());
            section.setUserID(userID);
            section.setName(name);
            section.setDescription(description != null ? description : "");
            section.setStatus(status);
            section.setCourseID(courseID);

            boolean created = sectionsService.insert(section);
            if (created) {
                request.setAttribute("message", "✅ Tạo chương thành công!");
            } else {
                request.setAttribute("error", "❌ Tạo chương thất bại!");
            }

            String redirectUrl = request.getContextPath() + "/ManageSection";
            if (courseID != null) {
                redirectUrl += "?course=" + courseID;
            }
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tạo chương: " + e.getMessage());
            listSections(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            UUID userID = getUserIdFromSession(session);
            if (userID == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            String sectionIdParam = request.getParameter("id");
            if (sectionIdParam == null || sectionIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/ManageSection");
                return;
            }

            UUID sectionID = UUID.fromString(sectionIdParam);
            Sections section = sectionsService.findById(sectionID);

            if (section == null || !section.getUserID().equals(userID)) {
                request.setAttribute("error", "Không tìm thấy chương hoặc bạn không có quyền!");
                listSections(request, response);
                return;
            }

            // Lấy course info nếu có
            Courses course = null;
            if (section.getCourseID() != null) {
                List<Courses> allCourses = courseService.getAllCourses();
                for (Courses c : allCourses) {
                    if (c.getCourseID().equals(section.getCourseID())) {
                        course = c;
                        break;
                    }
                }
            }

            request.setAttribute("section", section);
            request.setAttribute("course", course);
            request.setAttribute("isEdit", true);
            request.setAttribute("fromServlet", "true");

            listSections(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải form sửa: " + e.getMessage());
            listSections(request, response);
        }
    }

    private void updateSection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            UUID userID = getUserIdFromSession(session);
            if (userID == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            String sectionIdParam = request.getParameter("id");
            if (sectionIdParam == null || sectionIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu thông tin chương!");
                listSections(request, response);
                return;
            }

            UUID sectionID = UUID.fromString(sectionIdParam);
            Sections section = sectionsService.findById(sectionID);

            if (section == null || !section.getUserID().equals(userID)) {
                request.setAttribute("error", "Không tìm thấy chương hoặc bạn không có quyền!");
                listSections(request, response);
                return;
            }

            String name = request.getParameter("sectionName");
            String description = request.getParameter("sectionDescription");
            String courseIdParam = request.getParameter("courseID");
            String statusStr = request.getParameter("status");

            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Tên chương không được để trống!");
                listSections(request, response);
                return;
            }

            UUID courseID = section.getCourseID();
            if (courseIdParam != null && !courseIdParam.isEmpty()) {
                courseID = UUID.fromString(courseIdParam);
            }

            boolean status = section.isStatus();
            if (statusStr != null) {
                status = Boolean.parseBoolean(statusStr);
            }

            section.setName(name);
            section.setDescription(description != null ? description : "");
            section.setStatus(status);
            section.setCourseID(courseID);

            boolean updated = sectionsService.update(section);
            if (updated) {
                request.setAttribute("message", "✅ Cập nhật chương thành công!");
            } else {
                request.setAttribute("error", "❌ Cập nhật chương thất bại!");
            }

            String redirectUrl = request.getContextPath() + "/ManageSection";
            if (courseID != null) {
                redirectUrl += "?course=" + courseID;
            }
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi cập nhật chương: " + e.getMessage());
            listSections(request, response);
        }
    }

    private void deleteSection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            UUID userID = getUserIdFromSession(session);
            if (userID == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            String sectionIdParam = request.getParameter("id");
            if (sectionIdParam == null || sectionIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/ManageSection");
                return;
            }

            UUID sectionID = UUID.fromString(sectionIdParam);
            Sections section = sectionsService.findById(sectionID);

            if (section != null && section.getUserID().equals(userID)) {
                boolean deleted = sectionsService.delete(sectionID);
                if (deleted) {
                    request.setAttribute("message", "✅ Xóa chương thành công!");
                } else {
                    request.setAttribute("error", "❌ Xóa chương thất bại!");
                }
            } else {
                request.setAttribute("error", "Không tìm thấy chương hoặc bạn không có quyền!");
            }

            String redirectUrl = request.getContextPath() + "/ManageSection";
            if (section != null && section.getCourseID() != null) {
                redirectUrl += "?course=" + section.getCourseID();
            }
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi xóa chương: " + e.getMessage());
            listSections(request, response);
        }
    }

    private UUID getUserIdFromSession(HttpSession session) {
        UUID userID = null;
        Object u = session.getAttribute("user");
        if (u instanceof User userObj) {
            Object idObj = userObj.getUserID();
            if (idObj instanceof UUID) {
                userID = (UUID) idObj;
            } else if (idObj != null) {
                userID = UUID.fromString(String.valueOf(idObj));
            }
        }

        if (userID == null) {
            Object uidAttr = session.getAttribute("userID");
            if (uidAttr != null) {
                if (uidAttr instanceof UUID) {
                    userID = (UUID) uidAttr;
                } else {
                    userID = UUID.fromString(String.valueOf(uidAttr));
                }
            }
        }

        return userID;
    }
}

