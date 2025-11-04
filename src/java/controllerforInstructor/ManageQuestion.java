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
import model.Assignment;
import model.McqQuestions;
import model.McqChoices;
import model.User;
import service.McqQuestionsService;
import AssignmentDAO.AssignmentDAO;
import AssignmentDAO.IAssignment;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageQuestion", urlPatterns = {"/ManageQuestion"})
public class ManageQuestion extends HttpServlet {

    private McqQuestionsService questionService = new McqQuestionsService();
    private IAssignment assignmentDAO = new AssignmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            listQuestions(request, response);
            return;
        }

        switch (action) {
            case "list":
                listQuestions(request, response);
                break;
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteQuestion(request, response);
                break;
            default:
                listQuestions(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            listQuestions(request, response);
            return;
        }

        switch (action) {
            case "create":
                createQuestion(request, response);
                break;
            case "update":
                updateQuestion(request, response);
                break;
            default:
                listQuestions(request, response);
                break;
        }
    }

    private void listQuestions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String assignmentIdParam = request.getParameter("assignment");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số assignment");
                request.getRequestDispatcher("/instructor/jsp/QuestionManagement.jsp").forward(request, response);
                return;
            }

            UUID assignmentId = UUID.fromString(assignmentIdParam);
            
            // Kiểm tra quyền sở hữu assignment
            Assignment assignment = assignmentDAO.findById(assignmentId);
            if (assignment == null) {
                request.setAttribute("error", "Không tìm thấy bài tập");
                request.getRequestDispatcher("/instructor/jsp/QuestionManagement.jsp").forward(request, response);
                return;
            }

            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || !assignment.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền xem câu hỏi này");
                request.getRequestDispatcher("/instructor/jsp/QuestionManagement.jsp").forward(request, response);
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
            request.getRequestDispatcher("/instructor/jsp/QuestionManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách câu hỏi: " + e.getMessage());
            request.getRequestDispatcher("/instructor/jsp/QuestionManagement.jsp").forward(request, response);
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String assignmentIdParam = request.getParameter("assignment");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số assignment");
                listQuestions(request, response);
                return;
            }

            UUID assignmentId = UUID.fromString(assignmentIdParam);
            Assignment assignment = assignmentDAO.findById(assignmentId);
            
            if (assignment == null) {
                request.setAttribute("error", "Không tìm thấy bài tập");
                listQuestions(request, response);
                return;
            }

            request.setAttribute("assignment", assignment);
            request.setAttribute("isCreate", true);
            listQuestions(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            listQuestions(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String questionIdParam = request.getParameter("id");
            if (questionIdParam == null || questionIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu ID câu hỏi");
                listQuestions(request, response);
                return;
            }

            UUID questionId = UUID.fromString(questionIdParam);
            McqQuestions question = questionService.getQuestionById(questionId);

            if (question == null) {
                request.setAttribute("error", "Không tìm thấy câu hỏi");
                listQuestions(request, response);
                return;
            }

            // Kiểm tra quyền
            Assignment assignment = assignmentDAO.findById(question.getAssignmentId());
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || assignment == null || !assignment.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền chỉnh sửa câu hỏi này");
                listQuestions(request, response);
                return;
            }

            // Lấy choices
            List<McqChoices> choices = questionService.getChoicesByQuestion(questionId);
            question.setMcqChoicesCollection(choices);

            request.setAttribute("question", question);
            request.setAttribute("assignment", assignment);
            request.setAttribute("isEdit", true);
            request.setAttribute("questions", questionService.getQuestionsByAssignment(question.getAssignmentId()));
            request.setAttribute("fromServlet", "true");
            request.getRequestDispatcher("/instructor/jsp/QuestionManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            listQuestions(request, response);
        }
    }

    private void createQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String assignmentIdParam = request.getParameter("assignmentId");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu tham số assignment");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam);
                return;
            }

            UUID assignmentId = UUID.fromString(assignmentIdParam);
            Assignment assignment = assignmentDAO.findById(assignmentId);

            if (assignment == null) {
                request.setAttribute("error", "Không tìm thấy bài tập");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam);
                return;
            }

            // Kiểm tra quyền
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || !assignment.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền tạo câu hỏi");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam);
                return;
            }

            // Lấy dữ liệu từ form
            String content = request.getParameter("questionContent");
            
            // Lấy choices từ form (choice1, choice2, choice3, choice4, etc.)
            List<McqChoices> choices = new ArrayList<>();
            int choiceIndex = 1;
            while (true) {
                String choiceContent = request.getParameter("choice" + choiceIndex);
                if (choiceContent == null || choiceContent.trim().isEmpty()) {
                    break;
                }
                
                String isCorrectParam = request.getParameter("isCorrect" + choiceIndex);
                boolean isCorrect = isCorrectParam != null && "true".equals(isCorrectParam);
                
                McqChoices choice = new McqChoices();
                choice.setId(UUID.randomUUID());
                choice.setContent(choiceContent);
                choice.setIsCorrect(isCorrect);
                choices.add(choice);
                
                choiceIndex++;
            }

            if (choices.isEmpty()) {
                request.setAttribute("error", "Cần ít nhất 1 lựa chọn!");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam);
                return;
            }

            // Tạo question mới
            McqQuestions question = new McqQuestions();
            question.setId(UUID.randomUUID());
            question.setContent(content);
            question.setAssignmentId(assignmentId);

            boolean created = questionService.createQuestion(question, choices);

            if (created) {
                request.setAttribute("message", "Tạo câu hỏi thành công!");
            } else {
                request.setAttribute("error", "Tạo câu hỏi thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tạo câu hỏi: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + request.getParameter("assignmentId"));
        }
    }

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String questionIdParam = request.getParameter("questionId");
            if (questionIdParam == null || questionIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu ID câu hỏi");
                listQuestions(request, response);
                return;
            }

            UUID questionId = UUID.fromString(questionIdParam);
            McqQuestions existingQuestion = questionService.getQuestionById(questionId);

            if (existingQuestion == null) {
                request.setAttribute("error", "Không tìm thấy câu hỏi");
                listQuestions(request, response);
                return;
            }

            // Kiểm tra quyền
            Assignment assignment = assignmentDAO.findById(existingQuestion.getAssignmentId());
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || assignment == null || !assignment.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền chỉnh sửa câu hỏi này");
                listQuestions(request, response);
                return;
            }

            // Lấy dữ liệu từ form
            String content = request.getParameter("questionContent");
            
            // Lấy choices từ form
            List<McqChoices> choices = new ArrayList<>();
            int choiceIndex = 1;
            while (true) {
                String choiceContent = request.getParameter("choice" + choiceIndex);
                if (choiceContent == null || choiceContent.trim().isEmpty()) {
                    break;
                }
                
                String isCorrectParam = request.getParameter("isCorrect" + choiceIndex);
                boolean isCorrect = isCorrectParam != null && "true".equals(isCorrectParam);
                
                McqChoices choice = new McqChoices();
                choice.setId(UUID.randomUUID());
                choice.setContent(choiceContent);
                choice.setIsCorrect(isCorrect);
                choices.add(choice);
                
                choiceIndex++;
            }

            // Cập nhật question
            existingQuestion.setContent(content);

            boolean updated = questionService.updateQuestion(existingQuestion, choices);

            if (updated) {
                request.setAttribute("message", "Cập nhật câu hỏi thành công!");
            } else {
                request.setAttribute("error", "Cập nhật câu hỏi thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + existingQuestion.getAssignmentId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi cập nhật câu hỏi: " + e.getMessage());
            listQuestions(request, response);
        }
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String questionIdParam = request.getParameter("id");
            if (questionIdParam == null || questionIdParam.isEmpty()) {
                request.setAttribute("error", "Thiếu ID câu hỏi");
                listQuestions(request, response);
                return;
            }

            UUID questionId = UUID.fromString(questionIdParam);
            McqQuestions question = questionService.getQuestionById(questionId);

            if (question == null) {
                request.setAttribute("error", "Không tìm thấy câu hỏi");
                listQuestions(request, response);
                return;
            }

            // Kiểm tra quyền
            Assignment assignment = assignmentDAO.findById(question.getAssignmentId());
            UUID userID = getUserIdFromSession(request.getSession(false));
            if (userID == null || assignment == null || !assignment.getUserID().equals(userID)) {
                request.setAttribute("error", "Bạn không có quyền xóa câu hỏi này");
                listQuestions(request, response);
                return;
            }

            UUID assignmentId = question.getAssignmentId();
            boolean deleted = questionService.deleteQuestion(questionId);

            if (deleted) {
                request.setAttribute("message", "Xóa câu hỏi thành công!");
            } else {
                request.setAttribute("error", "Xóa câu hỏi thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentId.toString());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi xóa câu hỏi: " + e.getMessage());
            listQuestions(request, response);
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

