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
import java.util.List;
import java.util.UUID;
import model.Assignment;
import model.User;
import service.AssignmentService;
import service.SectionsService;
import service.CourseServiceImpl;
import model.Sections;
import model.Courses;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageAssignment", urlPatterns = {"/ManageAssignment"})
public class ManageAssignment extends HttpServlet {

    AssignmentService assignmentService = new AssignmentService();
    SectionsService sectionsService = new SectionsService();
    CourseServiceImpl courseService = new CourseServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            listAssignments(request, response);
            return;
        }

        switch (action) {
            case "list":
                listAssignments(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteAssignment(request, response);
                break;
            default:
                listAssignments(request, response);
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
                createAssignment(request, response);
                break;
            case "update":
                updateAssignment(request, response);
                break;
            case "delete":
                deleteAssignment(request, response);
                break;
            default:
                listAssignments(request, response);
                break;
        }
    }

    private void listAssignments(HttpServletRequest request, HttpServletResponse response)
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

            // Lấy danh sách assignments của instructor
            List<Assignment> assignments = assignmentService.findByUserID(userID);
            
            // Lấy danh sách courses và sections để hiển thị trong form
            List<Courses> courses = courseService.findAll();
            List<Sections> sections = sectionsService.findAll();

            request.setAttribute("assignments", assignments);
            request.setAttribute("courses", courses);
            request.setAttribute("sections", sections);
            request.setAttribute("fromServlet", "true");
            
            request.getRequestDispatcher("/instructor/jsp/AssignmentDetails.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách bài tập: " + e.getMessage());
            request.getRequestDispatcher("/instructor/jsp/AssignmentDetails.jsp").forward(request, response);
        }
    }

    private void createAssignment(HttpServletRequest request, HttpServletResponse response)
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

            String name = request.getParameter("assignmentName");
            String description = request.getParameter("assignmentDescription");
            String orderStr = request.getParameter("assignmentOrder");
            String sectionIDStr = request.getParameter("sectionID");

            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Tên bài tập không được để trống!");
                listAssignments(request, response);
                return;
            }

            UUID sectionID = null;
            if (sectionIDStr != null && !sectionIDStr.isEmpty()) {
                sectionID = UUID.fromString(sectionIDStr);
            }

            int order = 1;
            if (orderStr != null && !orderStr.trim().isEmpty()) {
                try {
                    order = Integer.parseInt(orderStr);
                } catch (NumberFormatException e) {
                    order = 1;
                }
            }

            Assignment assignment = new Assignment();
            assignment.setAssignmentID(UUID.randomUUID());
            assignment.setUserID(userID);
            assignment.setName(name);
            assignment.setDescription(description != null ? description : "");
            assignment.setOrder(order);
            assignment.setSectionID(sectionID);

            assignmentService.create(assignment);

            response.sendRedirect(request.getContextPath() + "/ManageAssignment?action=list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tạo bài tập: " + e.getMessage());
            listAssignments(request, response);
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

            String assignmentIdParam = request.getParameter("id");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/ManageAssignment?action=list");
                return;
            }

            UUID assignmentID = UUID.fromString(assignmentIdParam);
            Assignment assignment = assignmentService.findById(assignmentID);

            if (assignment == null || !assignment.getUserID().equals(userID)) {
                request.setAttribute("error", "Không tìm thấy bài tập hoặc bạn không có quyền!");
                listAssignments(request, response);
                return;
            }

            List<Courses> courses = courseService.findAll();
            List<Sections> sections = sectionsService.findAll();

            request.setAttribute("assignment", assignment);
            request.setAttribute("courses", courses);
            request.setAttribute("sections", sections);
            request.setAttribute("isEdit", true);
            request.setAttribute("fromServlet", "true");

            listAssignments(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải form sửa: " + e.getMessage());
            listAssignments(request, response);
        }
    }

    private void updateAssignment(HttpServletRequest request, HttpServletResponse response)
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

            String assignmentIdParam = request.getParameter("id");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu thông tin bài tập!");
                listAssignments(request, response);
                return;
            }

            UUID assignmentID = UUID.fromString(assignmentIdParam);
            Assignment assignment = assignmentService.findById(assignmentID);

            if (assignment == null || !assignment.getUserID().equals(userID)) {
                request.setAttribute("error", "Không tìm thấy bài tập hoặc bạn không có quyền!");
                listAssignments(request, response);
                return;
            }

            String name = request.getParameter("assignmentName");
            String description = request.getParameter("assignmentDescription");
            String orderStr = request.getParameter("assignmentOrder");
            String sectionIDStr = request.getParameter("sectionID");

            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Tên bài tập không được để trống!");
                listAssignments(request, response);
                return;
            }

            UUID sectionID = null;
            if (sectionIDStr != null && !sectionIDStr.isEmpty()) {
                sectionID = UUID.fromString(sectionIDStr);
            }

            int order = assignment.getOrder();
            if (orderStr != null && !orderStr.trim().isEmpty()) {
                try {
                    order = Integer.parseInt(orderStr);
                } catch (NumberFormatException e) {
                    // Giữ nguyên giá trị cũ
                }
            }

            assignment.setName(name);
            assignment.setDescription(description != null ? description : "");
            assignment.setOrder(order);
            assignment.setSectionID(sectionID);

            int updated = assignmentService.update(assignment);
            if (updated > 0) {
                request.setAttribute("message", "✅ Cập nhật bài tập thành công!");
            } else {
                request.setAttribute("error", "❌ Cập nhật bài tập thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageAssignment?action=list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi cập nhật bài tập: " + e.getMessage());
            listAssignments(request, response);
        }
    }

    private void deleteAssignment(HttpServletRequest request, HttpServletResponse response)
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

            String assignmentIdParam = request.getParameter("id");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/ManageAssignment?action=list");
                return;
            }

            UUID assignmentID = UUID.fromString(assignmentIdParam);
            Assignment assignment = assignmentService.findById(assignmentID);

            if (assignment != null && assignment.getUserID().equals(userID)) {
                assignmentService.deleteById(assignmentID);
                request.setAttribute("message", "✅ Xóa bài tập thành công!");
            } else {
                request.setAttribute("error", "Không tìm thấy bài tập hoặc bạn không có quyền!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageAssignment?action=list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi xóa bài tập: " + e.getMessage());
            listAssignments(request, response);
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

