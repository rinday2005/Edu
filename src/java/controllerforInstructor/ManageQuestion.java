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
        System.out.println("[ManageQuestion] doPost - action: " + action);
        System.out.println("[ManageQuestion] doPost - request parameters: " + java.util.Collections.list(request.getParameterNames()));
        
        if (action == null || action.isEmpty()) {
            System.out.println("[ManageQuestion] doPost - action is null or empty, redirecting to list");
            listQuestions(request, response);
            return;
        }

        switch (action) {
            case "create":
                System.out.println("[ManageQuestion] doPost - calling createQuestion");
                createQuestion(request, response);
                break;
            case "update":
                System.out.println("[ManageQuestion] doPost - calling updateQuestion");
                updateQuestion(request, response);
                break;
            default:
                System.out.println("[ManageQuestion] doPost - unknown action: " + action + ", redirecting to list");
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
            System.out.println("[ManageQuestion] listQuestions - Loading questions for assignment: " + assignmentId);
            List<McqQuestions> questions = questionService.getQuestionsByAssignment(assignmentId);
            System.out.println("[ManageQuestion] listQuestions - Found " + (questions != null ? questions.size() : 0) + " questions");
            
            // Lấy choices cho mỗi question
            if (questions != null) {
                for (McqQuestions q : questions) {
                    List<McqChoices> choices = questionService.getChoicesByQuestion(q.getId());
                    System.out.println("[ManageQuestion] listQuestions - Question " + q.getId() + " has " + (choices != null ? choices.size() : 0) + " choices");
                    q.setMcqChoicesCollection(choices);
                }
            }

            // Get message and error from URL parameters if present
            String messageParam = request.getParameter("message");
            String errorParam = request.getParameter("error");
            if (messageParam != null && !messageParam.isEmpty()) {
                request.setAttribute("message", messageParam);
            }
            if (errorParam != null && !errorParam.isEmpty()) {
                request.setAttribute("error", errorParam);
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
            System.out.println("===========================================");
            System.out.println("[ManageQuestion] createQuestion - Starting");
            System.out.println("[ManageQuestion] createQuestion - Request Method: " + request.getMethod());
            System.out.println("[ManageQuestion] createQuestion - Request URI: " + request.getRequestURI());
            System.out.println("[ManageQuestion] createQuestion - X-Requested-With: " + request.getHeader("X-Requested-With"));
            System.out.println("[ManageQuestion] createQuestion - Accept: " + request.getHeader("Accept"));
            System.out.println("[ManageQuestion] createQuestion - ajax parameter: " + request.getParameter("ajax"));
            
            String assignmentIdParam = request.getParameter("assignmentId");
            System.out.println("[ManageQuestion] createQuestion - assignmentId: " + assignmentIdParam);
            String questionContent = request.getParameter("questionContent");
            System.out.println("[ManageQuestion] createQuestion - questionContent: " + questionContent);
            
            // Print all parameters
            System.out.println("[ManageQuestion] createQuestion - All parameters:");
            request.getParameterMap().forEach((key, values) -> {
                System.out.println("  " + key + " = " + java.util.Arrays.toString(values));
            });
            
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                System.out.println("[ManageQuestion] createQuestion - Missing assignmentId");
                // Try to get from assignment parameter
                assignmentIdParam = request.getParameter("assignment");
                if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                    request.setAttribute("error", "Thiếu tham số assignment");
                    response.sendRedirect(request.getContextPath() + "/ManageAssignment?action=list");
                    return;
                }
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
            String content = questionContent; // Use the one we already got
            
            if (content == null || content.trim().isEmpty()) {
                System.out.println("[ManageQuestion] createQuestion - Question content is empty");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam + "&error=" + java.net.URLEncoder.encode("Nội dung câu hỏi không được để trống!", "UTF-8"));
                return;
            }
            
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
                
                System.out.println("[ManageQuestion] createQuestion - Choice " + choiceIndex + ": " + choiceContent + " (isCorrect: " + isCorrect + ")");
                
                McqChoices choice = new McqChoices();
                choice.setId(UUID.randomUUID());
                choice.setContent(choiceContent);
                choice.setIsCorrect(isCorrect);
                choices.add(choice);
                
                choiceIndex++;
            }

            System.out.println("[ManageQuestion] createQuestion - Total choices: " + choices.size());

            if (choices.isEmpty() || choices.size() < 2) {
                System.out.println("[ManageQuestion] createQuestion - Not enough choices: " + choices.size());
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam + "&error=" + java.net.URLEncoder.encode("Cần ít nhất 2 lựa chọn!", "UTF-8"));
                return;
            }
            
            // Kiểm tra có ít nhất một đáp án đúng
            boolean hasCorrectAnswer = false;
            for (McqChoices choice : choices) {
                if (choice.isIsCorrect()) {
                    hasCorrectAnswer = true;
                    break;
                }
            }
            if (!hasCorrectAnswer) {
                request.setAttribute("error", "Cần ít nhất một đáp án đúng!");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam);
                return;
            }

            // Tạo question mới
            System.out.println("[ManageQuestion] createQuestion - Creating question object...");
            System.out.println("[ManageQuestion] createQuestion - assignmentId UUID: " + assignmentId);
            
            McqQuestions question = new McqQuestions();
            UUID questionId = UUID.randomUUID();
            question.setId(questionId);
            question.setContent(content);
            question.setAssignmentId(assignmentId);
            
            // Verify values are set correctly
            System.out.println("[ManageQuestion] createQuestion - Question object created:");
            System.out.println("  - Question ID: " + question.getId());
            System.out.println("  - Question Content: " + question.getContent());
            System.out.println("  - Assignment ID: " + question.getAssignmentId());
            System.out.println("  - Choices count: " + choices.size());
            
            // Verify assignmentId is not null before calling service
            if (question.getAssignmentId() == null) {
                System.err.println("[ManageQuestion] createQuestion - CRITICAL ERROR: AssignmentId is NULL after setting!");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam + "&error=" + java.net.URLEncoder.encode("Lỗi: AssignmentId không hợp lệ!", "UTF-8"));
                return;
            }
            
            // Verify choices have valid IDs
            for (int i = 0; i < choices.size(); i++) {
                McqChoices c = choices.get(i);
                System.out.println("[ManageQuestion] createQuestion - Choice " + (i+1) + " ID: " + c.getId() + ", Content: " + c.getContent());
            }

            System.out.println("[ManageQuestion] createQuestion - Calling questionService.createQuestion");
            boolean created = questionService.createQuestion(question, choices);
            System.out.println("[ManageQuestion] createQuestion - Result: " + created);

            // Check if this is an AJAX request
            String requestedWith = request.getHeader("X-Requested-With");
            String acceptHeader = request.getHeader("Accept");
            String ajaxParam = request.getParameter("ajax");
            
            System.out.println("[ManageQuestion] createQuestion - Checking AJAX request:");
            System.out.println("  X-Requested-With: " + requestedWith);
            System.out.println("  Accept: " + acceptHeader);
            System.out.println("  ajax parameter: " + ajaxParam);
            
            boolean isAjax = "XMLHttpRequest".equals(requestedWith) || 
                            (acceptHeader != null && acceptHeader.contains("application/json")) ||
                            "true".equals(ajaxParam);
            
            System.out.println("[ManageQuestion] createQuestion - isAjax: " + isAjax);
            
            if (isAjax) {
                // Return JSON response for AJAX requests
                System.out.println("[ManageQuestion] createQuestion - Returning JSON response");
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Cache-Control", "no-cache");
                
                String jsonResponse;
                if (created) {
                    jsonResponse = "{\"success\":true,\"message\":\"Tạo câu hỏi thành công!\"}";
                } else {
                    jsonResponse = "{\"success\":false,\"message\":\"Tạo câu hỏi thất bại! Vui lòng thử lại.\"}";
                }
                
                System.out.println("[ManageQuestion] createQuestion - JSON response: " + jsonResponse);
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();
                System.out.println("[ManageQuestion] createQuestion - Response sent");
                System.out.println("===========================================");
                return;
            }
            
            // Regular form submission - redirect
            if (created) {
                // Success - redirect with message parameter
                System.out.println("[ManageQuestion] createQuestion - Success, redirecting");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam + "&message=" + java.net.URLEncoder.encode("Tạo câu hỏi thành công!", "UTF-8"));
            } else {
                // Failure - redirect with error parameter
                System.out.println("[ManageQuestion] createQuestion - Failed, redirecting with error");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam + "&error=" + java.net.URLEncoder.encode("Tạo câu hỏi thất bại! Vui lòng thử lại.", "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            String assignmentIdParam = request.getParameter("assignmentId");
            if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                assignmentIdParam = request.getParameter("assignment");
                if (assignmentIdParam == null || assignmentIdParam.isEmpty()) {
                    assignmentIdParam = "";
                }
            }
            
            // Check if this is an AJAX request
            String requestedWith = request.getHeader("X-Requested-With");
            boolean isAjax = "XMLHttpRequest".equals(requestedWith) || 
                            request.getHeader("Accept") != null && 
                            request.getHeader("Accept").contains("application/json");
            
            if (isAjax || request.getParameter("ajax") != null) {
                // Return JSON response for AJAX requests
                try {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    String errorMsg = "Lỗi khi tạo câu hỏi: " + (e.getMessage() != null ? e.getMessage() : "Unknown error");
                    response.getWriter().write("{\"success\":false,\"message\":\"" + 
                        errorMsg.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r") + "\"}");
                    response.getWriter().flush();
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            // Regular form submission - redirect
            try {
                String errorMsg = "Lỗi khi tạo câu hỏi: " + (e.getMessage() != null ? e.getMessage() : "Unknown error");
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam + "&error=" + java.net.URLEncoder.encode(errorMsg, "UTF-8"));
            } catch (Exception ex) {
                ex.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/ManageQuestion?action=list&assignment=" + assignmentIdParam);
            }
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

