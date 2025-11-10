package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import model.Assignment;
import model.McqQuestions;
import model.McqChoices;
import service.McqQuestionsService;
import service.AssignmentService;

/**
 * Servlet for learner to take tests
 */
@WebServlet(name = "LearnerTestServlet", urlPatterns = {"/learner/test"})
public class LearnerTestServlet extends HttpServlet {

    private McqQuestionsService questionService = new McqQuestionsService();
    private AssignmentService assignmentService = new AssignmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String assignmentIdParam = request.getParameter("assignment");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số assignment");
                request.getRequestDispatcher("/learner/jsp/Test/test.jsp").forward(request, response);
                return;
            }

            UUID assignmentId = UUID.fromString(assignmentIdParam);
            
            // Lấy assignment
            Assignment assignment = assignmentService.findById(assignmentId);
            if (assignment == null) {
                request.setAttribute("error", "Không tìm thấy bài test");
                request.getRequestDispatcher("/learner/jsp/Test/test.jsp").forward(request, response);
                return;
            }

            // Lấy danh sách questions
            List<McqQuestions> questions = questionService.getQuestionsByAssignment(assignmentId);
            
            // Lấy choices cho mỗi question
            for (McqQuestions q : questions) {
                List<McqChoices> choices = questionService.getChoicesByQuestion(q.getId());
                q.setMcqChoicesCollection(choices);
            }

            request.setAttribute("assignment", assignment);
            request.setAttribute("questions", questions);
            request.setAttribute("fromServlet", "true");
            request.getRequestDispatcher("/learner/jsp/Test/test.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải bài test: " + e.getMessage());
            request.getRequestDispatcher("/learner/jsp/Test/test.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle test submission
        try {
            String assignmentIdParam = request.getParameter("assignment");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số assignment");
                doGet(request, response);
                return;
            }

            UUID assignmentId = UUID.fromString(assignmentIdParam);
            Assignment assignment = assignmentService.findById(assignmentId);
            if (assignment == null) {
                request.setAttribute("error", "Không tìm thấy bài test");
                doGet(request, response);
                return;
            }

            // Get all questions
            List<McqQuestions> questions = questionService.getQuestionsByAssignment(assignmentId);
            
            int correctCount = 0;
            int totalQuestions = questions.size();
            
            // Check answers
            for (McqQuestions question : questions) {
                String userAnswerParam = request.getParameter("question_" + question.getId());
                if (userAnswerParam != null) {
                    UUID userChoiceId = UUID.fromString(userAnswerParam);
                    List<McqChoices> choices = questionService.getChoicesByQuestion(question.getId());
                    for (McqChoices choice : choices) {
                        if (choice.getId().equals(userChoiceId) && choice.isIsCorrect()) {
                            correctCount++;
                            break;
                        }
                    }
                }
            }
            
            double score = totalQuestions > 0 ? (double) correctCount / totalQuestions * 100 : 0;

            request.setAttribute("assignment", assignment);
            request.setAttribute("questions", questions);
            request.setAttribute("correctCount", correctCount);
            request.setAttribute("totalQuestions", totalQuestions);
            request.setAttribute("score", score);
            request.setAttribute("showResults", true);
            request.setAttribute("fromServlet", "true");
            
            // Get user answers for display
            java.util.Map<UUID, UUID> userAnswers = new java.util.HashMap<>();
            for (McqQuestions question : questions) {
                String userAnswerParam = request.getParameter("question_" + question.getId());
                if (userAnswerParam != null) {
                    userAnswers.put(question.getId(), UUID.fromString(userAnswerParam));
                }
            }
            request.setAttribute("userAnswers", userAnswers);
            
            request.getRequestDispatcher("/learner/jsp/Test/test.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi chấm bài: " + e.getMessage());
            doGet(request, response);
        }
    }
}

