/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import model.Assignment;
import model.McqChoices;
import model.McqQuestions;
import service.AssignmentService;
import service.SubmissionsService;
import service.McqUserAnswerService;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AssignmentServlet", urlPatterns = {"/assignment"})
public class AssignmentServlet extends HttpServlet {
    private final AssignmentService assignmentService = new AssignmentService();
    private final SubmissionsService submissionsService = new SubmissionsService();
    private final McqUserAnswerService mcqUserAnswerService = new McqUserAnswerService();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AssignmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AssignmentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String courseIDStr = req.getParameter("courseID");
        String sectionIDStr = req.getParameter("sectionID");
        String userIDStr = (String) req.getSession().getAttribute("userID"); // ✅ fix cast

        if (sectionIDStr == null || sectionIDStr.isEmpty() || courseIDStr == null || courseIDStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=Thiếu tham số khóa học");
            return;
        }

        try {
            UUID sectionID = UUID.fromString(sectionIDStr);
            UUID courseID = UUID.fromString(courseIDStr);
            UUID userID = null;
            if (userIDStr != null && !userIDStr.isBlank()) {
                userID = UUID.fromString(userIDStr);
            }

            System.out.println("===========================================");
            System.out.println("[AssignmentServlet] Checking sectionID: " + sectionID);
            System.out.println("[AssignmentServlet] CourseID: " + courseID);
            
            // Kiểm tra xem section này có assignment không
            List<Assignment> assignmentsInSection = assignmentService.findBySectionID(sectionID);
            System.out.println("[AssignmentServlet] Found " + (assignmentsInSection != null ? assignmentsInSection.size() : 0) + " assignments in section");
            
            // Chẩn đoán: Kiểm tra tất cả assignments trong database để xem có sectionID khớp không
            try {
                List<Assignment> allAssignments = assignmentService.findAll();
                System.out.println("[AssignmentServlet] Total assignments in database: " + (allAssignments != null ? allAssignments.size() : 0));
                if (allAssignments != null) {
                    System.out.println("[AssignmentServlet] Checking all assignments for sectionID match...");
                    for (Assignment a : allAssignments) {
                        System.out.println("[AssignmentServlet] Assignment: " + a.getAssignmentID() + 
                                ", SectionID: " + a.getSectionID() + 
                                ", Match: " + (a.getSectionID() != null && a.getSectionID().equals(sectionID)));
                    }
                }
            } catch (Exception e) {
                System.err.println("[AssignmentServlet] Error checking all assignments: " + e.getMessage());
            }
            
            if (assignmentsInSection != null && !assignmentsInSection.isEmpty()) {
                for (Assignment a : assignmentsInSection) {
                    System.out.println("[AssignmentServlet] Assignment found: " + a.getAssignmentID() + ", Name: " + a.getName() + ", SectionID: " + a.getSectionID());
                }
            }
            
            if (assignmentsInSection == null || assignmentsInSection.isEmpty()) {
                System.err.println("[AssignmentServlet] ❌ No assignment found for sectionID: " + sectionID);
                System.err.println("[AssignmentServlet] This section does not have any test/assignment created yet.");
                System.err.println("[AssignmentServlet] 💡 Solution: Instructor needs to create an assignment for this section.");
                req.setAttribute("error", "Section này chưa có bài kiểm tra. Vui lòng liên hệ instructor để tạo bài kiểm tra cho section này. (SectionID: " + sectionID + ")");
                req.setAttribute("courseID", courseID);
                req.setAttribute("sectionID", sectionID);
                RequestDispatcher rd = req.getRequestDispatcher("/learner/jsp/Course/quiz.jsp");
                rd.forward(req, resp);
                return;
            }
            
            // Sử dụng assignment đầu tiên (thường một section chỉ có một assignment)
            System.out.println("[AssignmentServlet] Loading assignment with questions...");
            Assignment quizAssignment = assignmentService.getAssignmentWithQuestions(sectionID);
            
            // Kiểm tra xem assignment có tồn tại không
            if (quizAssignment == null) {
                System.err.println("[AssignmentServlet] No assignment found for sectionID: " + sectionID + " (but found " + assignmentsInSection.size() + " in list)");
                // Thử sử dụng assignment đầu tiên trong danh sách
                if (!assignmentsInSection.isEmpty()) {
                    quizAssignment = assignmentsInSection.get(0);
                    System.out.println("[AssignmentServlet] Using first assignment from list: " + quizAssignment.getAssignmentID());
                    // Tải thủ công các câu hỏi và lựa chọn
                    try {
                        quizAssignment = assignmentService.getAssignmentWithQuestions(sectionID);
                    } catch (Exception e) {
                        System.err.println("[AssignmentServlet] Error loading questions for assignment: " + e.getMessage());
                    }
                }
                
                if (quizAssignment == null) {
                    req.setAttribute("error", "Không thể tải bài kiểm tra. Vui lòng liên hệ instructor.");
                    req.setAttribute("courseID", courseID);
                    req.setAttribute("sectionID", sectionID);
                    RequestDispatcher rd = req.getRequestDispatcher("/learner/jsp/Course/quiz.jsp");
                    rd.forward(req, resp);
                    return;
                }
            }
            
            System.out.println("[AssignmentServlet] Found assignment: " + quizAssignment.getAssignmentID() + 
                    ", Questions: " + (quizAssignment.getQuestions() != null ? quizAssignment.getQuestions().size() : 0));
            
            // Kiểm tra xem có câu hỏi không
            if (quizAssignment.getQuestions() == null || quizAssignment.getQuestions().isEmpty()) {
                System.err.println("[AssignmentServlet] Assignment found but no questions: " + quizAssignment.getAssignmentID());
                req.setAttribute("error", "Bài kiểm tra này chưa có câu hỏi. Vui lòng liên hệ instructor.");
                req.setAttribute("quizAssignment", quizAssignment);
                req.setAttribute("courseID", courseID);
                RequestDispatcher rd = req.getRequestDispatcher("/learner/jsp/Course/quiz.jsp");
                rd.forward(req, resp);
                return;
            }
            
            req.setAttribute("quizAssignment", quizAssignment);
            req.setAttribute("courseID", courseID);

            // ✅ Nếu user đã đăng nhập thì kiểm tra submission
            if (userID != null) {
                UUID submissionID = submissionsService.findByUserAndAssignment(userID, quizAssignment.getAssignmentID());

                if (submissionID != null) {
                    // Lấy danh sách đáp án người dùng đã chọn
                    List<UUID> selectedChoiceIds = mcqUserAnswerService.findChoicesBySubmissionID(submissionID);

                    int correctCount = 0;
                    int totalQuestions = quizAssignment.getQuestions().size();

                    for (McqQuestions q : quizAssignment.getQuestions()) {
                        for (McqChoices c : q.getMcqChoicesCollection()) {
                            if (selectedChoiceIds.contains(c.getId()) && c.isIsCorrect()) {
                                correctCount++;
                                break;
                            }
                        }
                    }

                    double percentScore = totalQuestions > 0 ? (correctCount * 100.0 / totalQuestions) : 0;

                    req.setAttribute("isReview", true);
                    req.setAttribute("submissionID", submissionID);
                    req.setAttribute("selectedChoiceIds", selectedChoiceIds);
                    req.setAttribute("correctCount", correctCount);
                    req.setAttribute("totalQuestions", totalQuestions);
                    req.setAttribute("percentScore", String.format("%.1f", percentScore));

                } else {
                    req.setAttribute("isReview", false);
                }
            } else {
                req.setAttribute("isReview", false);
            }

            RequestDispatcher rd = req.getRequestDispatcher("/learner/jsp/Course/quiz.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Lỗi khi hiển thị bài kiểm tra", e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
