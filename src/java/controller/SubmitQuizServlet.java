package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import model.Assignment;
import model.McqQuestions;
import model.McqChoices;
import service.AssignmentService;
import service.SubmissionsService;
import service.McqUserAnswerService;

@WebServlet(name = "SubmitQuizServlet", urlPatterns = {"/submitQuiz"})
public class SubmitQuizServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AssignmentService assignmentService = new AssignmentService();
    private final SubmissionsService submissionsService = new SubmissionsService();
    private final McqUserAnswerService mcqUserAnswerService = new McqUserAnswerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // === 1. Nhận tham số cơ bản ===
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

        UUID assignmentID, userID, sectionID, courseID;
        try {
            assignmentID = UUID.fromString(assignmentIDStr.trim());
            userID       = UUID.fromString(userIDStr.trim());
            sectionID    = UUID.fromString(sectionIDStr.trim());
            courseID     = UUID.fromString(courseIDStr.trim());
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=UUID không hợp lệ");
            return;
        }

        // === 2. Lấy danh sách đáp án người học chọn ===
        List<UUID> selectedChoiceIds = new ArrayList<>();
        Enumeration<String> params = req.getParameterNames();

        while (params.hasMoreElements()) {
            String pname = params.nextElement();
            if (pname.startsWith("q_")) {
                String choiceStr = req.getParameter(pname);
                if (choiceStr != null && !choiceStr.isBlank()) {
                    try {
                        selectedChoiceIds.add(UUID.fromString(choiceStr.trim()));
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        }

        if (selectedChoiceIds.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=Bạn chưa chọn đáp án nào");
            return;
        }

        try {
            // === 3. Kiểm tra submission cũ ===
            UUID existingSubmissionID = submissionsService.findByUserAndAssignment(userID, assignmentID);
            UUID submissionID = (existingSubmissionID != null) ? existingSubmissionID : UUID.randomUUID();

            if (existingSubmissionID != null) {
                mcqUserAnswerService.deleteBySubmissionID(existingSubmissionID);
                submissionsService.deleteById(existingSubmissionID);
            }

            // === 4. Lưu submission mới và các lựa chọn ===
            submissionsService.saveSubmission(submissionID, userID, assignmentID);
            mcqUserAnswerService.saveUserAnswers(submissionID, selectedChoiceIds);

            // === 5. Chấm điểm ===
            Assignment quizAssignment = assignmentService.getByAssignmentId(assignmentID);
            int correctCount = 0;
            int totalQuestions = quizAssignment.getQuestions().size();

            // Duyệt từng câu hỏi
            for (McqQuestions q : quizAssignment.getQuestions()) {
                for (McqChoices c : q.getMcqChoicesCollection()) {
                    if (selectedChoiceIds.contains(c.getId()) && c.isIsCorrect()) {
                        correctCount++;
                        break; // chỉ tính 1 đáp án đúng / câu
                    }
                }
            }

            double percentScore = totalQuestions > 0 ? (correctCount * 100.0 / totalQuestions) : 0;

            // === 6. Gửi kết quả về quiz.jsp ===
            req.setAttribute("quizAssignment", quizAssignment);
            req.setAttribute("submissionID", submissionID);
            req.setAttribute("selectedChoiceIds", selectedChoiceIds);
            req.setAttribute("courseID", courseID);
            req.setAttribute("correctCount", correctCount);
            req.setAttribute("incorrectCount", totalQuestions - correctCount);
            req.setAttribute("totalQuestions", totalQuestions);
            req.setAttribute("percentScore", String.format("%.1f", percentScore));
            req.setAttribute("isReview", true);

            RequestDispatcher rd = req.getRequestDispatcher("/learner/jsp/Course/quiz.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Lỗi khi xử lý nộp bài kiểm tra", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=Phương thức GET không được hỗ trợ");
    }
}
