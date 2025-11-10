package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import service.SubmissionsService;
import service.McqUserAnswerService;
import util.StaticResourceUtil;

@WebServlet(name = "ResetQuizServlet", urlPatterns = {"/"})
public class ResetQuizServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SubmissionsService submissionsService = new SubmissionsService();
    private final McqUserAnswerService mcqUserAnswerService = new McqUserAnswerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Bỏ qua static resources (CSS, JS, images, etc.)
        String path = req.getRequestURI();
        if (StaticResourceUtil.isStaticResource(path)) {
            // Chuyển tiếp request cho default servlet để phục vụ static resources
            req.getRequestDispatcher(path).include(req, resp);
            return;
        }

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String assignmentIDStr = req.getParameter("assignmentID");
        String userIDStr       = req.getParameter("userID");
        String sectionIDStr    = req.getParameter("sectionID");
        String courseIDStr     = req.getParameter("courseID");

        if (assignmentIDStr == null || userIDStr == null ||
            sectionIDStr == null || courseIDStr == null ||
            assignmentIDStr.isBlank() || userIDStr.isBlank() ||
            sectionIDStr.isBlank() || courseIDStr.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=Thiếu tham số bắt buộc");
            return;
        }

        try {
            UUID assignmentID = UUID.fromString(assignmentIDStr.trim());
            UUID userID       = UUID.fromString(userIDStr.trim());
            UUID sectionID    = UUID.fromString(sectionIDStr.trim());
            UUID courseID     = UUID.fromString(courseIDStr.trim());

            // === Xóa submission cũ của learner ===
            UUID existingSubmissionID = submissionsService.findByUserAndAssignment(userID, assignmentID);
            if (existingSubmissionID != null) {
                mcqUserAnswerService.deleteBySubmissionID(existingSubmissionID);
                submissionsService.deleteById(existingSubmissionID);
            }

            // === Quay lại trang quiz để làm lại từ đầu ===
            resp.sendRedirect(req.getContextPath()
                + "/assignment?sectionID=" + sectionID
                + "&courseID=" + courseID);

        } catch (IllegalArgumentException e) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=UUID không hợp lệ");
        } catch (Exception e) {
            throw new ServletException("Không thể reset bài kiểm tra", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Bỏ qua static resources (CSS, JS, images, etc.)
        String path = req.getRequestURI();
        if (StaticResourceUtil.isStaticResource(path)) {
            // Chuyển tiếp request cho default servlet để phục vụ static resources
            req.getRequestDispatcher(path).include(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=Phương thức GET không được hỗ trợ");
    }
}