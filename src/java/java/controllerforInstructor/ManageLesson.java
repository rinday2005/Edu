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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.Lession;
import model.Sections;
import model.User;
import LessionDAO.LessionDAO;
import LessionDAO.ILessionDAO;
import SectionsDAO.SectionDAO;
import SectionsDAO.ISectionDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageLesson", urlPatterns = {"/ManageLesson"})
public class ManageLesson extends HttpServlet {

    private ILessionDAO lessonDAO = new LessionDAO();
    private ISectionDAO sectionDAO = new SectionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            listLessons(request, response);
            return;
        }

        switch (action) {
            case "list":
                listLessons(request, response);
                break;
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteLesson(request, response);
                break;
            default:
                listLessons(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            listLessons(request, response);
            return;
        }

        switch (action) {
            case "create":
                createLesson(request, response);
                break;
            case "update":
                updateLesson(request, response);
                break;
            default:
                listLessons(request, response);
                break;
        }
    }

    private void listLessons(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sectionIdParam = request.getParameter("section");
            if (sectionIdParam == null || sectionIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số section");
                request.getRequestDispatcher("/instructor/jsp/LessonManagement.jsp").forward(request, response);
                return;
            }

            UUID sectionId = UUID.fromString(sectionIdParam);
            
            // Kiểm tra quyền sở hữu section
            Sections section = sectionDAO.findById(sectionId);
            if (section == null) {
                request.setAttribute("error", "Không tìm thấy chương");
                request.getRequestDispatcher("/instructor/jsp/LessonManagement.jsp").forward(request, response);
                return;
            }

            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || !section.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền xem bài học này");
                request.getRequestDispatcher("/instructor/jsp/LessonManagement.jsp").forward(request, response);
                return;
            }

            // Lấy danh sách lessons
            List<Lession> lessons = lessonDAO.findBySectionId(sectionId);

            request.setAttribute("section", section);
            request.setAttribute("lessons", lessons);
            request.setAttribute("fromServlet", "true");
            request.getRequestDispatcher("/instructor/jsp/LessonManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách bài học: " + e.getMessage());
            request.getRequestDispatcher("/instructor/jsp/LessonManagement.jsp").forward(request, response);
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sectionIdParam = request.getParameter("section");
            if (sectionIdParam == null || sectionIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số section");
                listLessons(request, response);
                return;
            }

            UUID sectionId = UUID.fromString(sectionIdParam);
            Sections section = sectionDAO.findById(sectionId);
            
            if (section == null) {
                request.setAttribute("error", "Không tìm thấy chương");
                listLessons(request, response);
                return;
            }

            request.setAttribute("section", section);
            request.setAttribute("isCreate", true);
            listLessons(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            listLessons(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String lessonIdParam = request.getParameter("id");
            if (lessonIdParam == null || lessonIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu ID bài học");
                listLessons(request, response);
                return;
            }

            UUID lessonId = UUID.fromString(lessonIdParam);
            Lession lesson = lessonDAO.findById(lessonId);

            if (lesson == null) {
                request.setAttribute("error", "Không tìm thấy bài học");
                listLessons(request, response);
                return;
            }

            // Kiểm tra quyền
            Sections section = sectionDAO.findById(lesson.getSectionID());
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || section == null || !section.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền chỉnh sửa bài học này");
                listLessons(request, response);
                return;
            }

            request.setAttribute("lesson", lesson);
            request.setAttribute("section", section);
            request.setAttribute("isEdit", true);
            request.setAttribute("lessons", lessonDAO.findBySectionId(lesson.getSectionID()));
            request.setAttribute("fromServlet", "true");
            request.getRequestDispatcher("/instructor/jsp/LessonManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            listLessons(request, response);
        }
    }

    private void createLesson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sectionIdParam = request.getParameter("sectionId");
            if (sectionIdParam == null || sectionIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số section");
                response.sendRedirect(request.getContextPath() + "/ManageLesson?action=list&section=" + sectionIdParam);
                return;
            }

            UUID sectionId = UUID.fromString(sectionIdParam);
            Sections section = sectionDAO.findById(sectionId);

            if (section == null) {
                request.setAttribute("error", "Không tìm thấy chương");
                response.sendRedirect(request.getContextPath() + "/ManageLesson?action=list&section=" + sectionIdParam);
                return;
            }

            // Kiểm tra quyền
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || !section.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền tạo bài học");
                response.sendRedirect(request.getContextPath() + "/ManageLesson?action=list&section=" + sectionIdParam);
                return;
            }

            // Lấy dữ liệu từ form
            String name = request.getParameter("lessonName");
            String description = request.getParameter("lessonDescription");
            String videoUrl = request.getParameter("videoUrl");
            String durationStr = request.getParameter("videoDuration");
            String statusStr = request.getParameter("status");

            int duration = 0;
            if (durationStr != null && !durationStr.isEmpty()) {
                try {
                    duration = Integer.parseInt(durationStr);
                } catch (NumberFormatException e) {
                    // Giữ mặc định là 0
                }
            }

            boolean status = true;
            if (statusStr != null) {
                status = Boolean.parseBoolean(statusStr);
            }

            // Tạo lesson mới
            Lession lesson = new Lession();
            lesson.setLessionID(UUID.randomUUID());
            lesson.setUserID(userID);
            lesson.setName(name);
            lesson.setDescription(description);
            lesson.setVideoUrl(videoUrl);
            lesson.setSectionID(sectionId);
            lesson.setVideoDuration(duration);
            lesson.setStatus(status);

            boolean created = lessonDAO.insert(lesson);

            if (created) {
                request.setAttribute("message", "Tạo bài học thành công!");
            } else {
                request.setAttribute("error", "Tạo bài học thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageLesson?action=list&section=" + sectionIdParam);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tạo bài học: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/ManageLesson?action=list&section=" + request.getParameter("sectionId"));
        }
    }

    private void updateLesson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String lessonIdParam = request.getParameter("lessonId");
            if (lessonIdParam == null || lessonIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu ID bài học");
                listLessons(request, response);
                return;
            }

            UUID lessonId = UUID.fromString(lessonIdParam);
            Lession existingLesson = lessonDAO.findById(lessonId);

            if (existingLesson == null) {
                request.setAttribute("error", "Không tìm thấy bài học");
                listLessons(request, response);
                return;
            }

            // Kiểm tra quyền
            Sections section = sectionDAO.findById(existingLesson.getSectionID());
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || section == null || !section.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền chỉnh sửa bài học này");
                listLessons(request, response);
                return;
            }

            // Lấy dữ liệu từ form
            String name = request.getParameter("lessonName");
            String description = request.getParameter("lessonDescription");
            String videoUrl = request.getParameter("videoUrl");
            String durationStr = request.getParameter("videoDuration");
            String statusStr = request.getParameter("status");

            int duration = existingLesson.getVideoDuration();
            if (durationStr != null && !durationStr.isEmpty()) {
                try {
                    duration = Integer.parseInt(durationStr);
                } catch (NumberFormatException e) {
                    // Giữ nguyên giá trị cũ
                }
            }

            boolean status = existingLesson.isStatus();
            if (statusStr != null) {
                status = Boolean.parseBoolean(statusStr);
            }

            // Cập nhật lesson
            existingLesson.setName(name);
            existingLesson.setDescription(description);
            existingLesson.setVideoUrl(videoUrl);
            existingLesson.setVideoDuration(duration);
            existingLesson.setStatus(status);

            boolean updated = lessonDAO.update(existingLesson);

            if (updated) {
                request.setAttribute("message", "Cập nhật bài học thành công!");
            } else {
                request.setAttribute("error", "Cập nhật bài học thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageLesson?action=list&section=" + existingLesson.getSectionID().toString());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi cập nhật bài học: " + e.getMessage());
            listLessons(request, response);
        }
    }

    private void deleteLesson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String lessonIdParam = request.getParameter("id");
            if (lessonIdParam == null || lessonIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu ID bài học");
                listLessons(request, response);
                return;
            }

            UUID lessonId = UUID.fromString(lessonIdParam);
            Lession lesson = lessonDAO.findById(lessonId);

            if (lesson == null) {
                request.setAttribute("error", "Không tìm thấy bài học");
                listLessons(request, response);
                return;
            }

            // Kiểm tra quyền
            Sections section = sectionDAO.findById(lesson.getSectionID());
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || section == null || !section.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền xóa bài học này");
                listLessons(request, response);
                return;
            }

            UUID sectionId = lesson.getSectionID();
            boolean deleted = lessonDAO.deleteById(lessonId);

            if (deleted) {
                request.setAttribute("message", "Xóa bài học thành công!");
            } else {
                request.setAttribute("error", "Xóa bài học thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageLesson?action=list&section=" + sectionId.toString());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi xóa bài học: " + e.getMessage());
            listLessons(request, response);
        }
    }

    private UUID getUserIdFromSession(jakarta.servlet.http.HttpSession session) {
        if (session == null) return null;
        
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
