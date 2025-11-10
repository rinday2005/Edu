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

            Assignment quizAssignment = assignmentService.getAssignmentWithQuestions(sectionID);
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
