/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerforInstructor;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import model.*;
import service.*;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageSections", urlPatterns = {"/ManageSections"})
public class ManageContent extends HttpServlet {
private SectionsService sectionService = new SectionsService();
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
            out.println("<title>Servlet ManageSections</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageSections at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        String courseId = request.getParameter("courseId");
        List<Sections> sections = sectionService.findAll();
        request.setAttribute("sections", sections);
        request.getRequestDispatcher("/instructor/ManageContent.jsp").forward(request, response);
    
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "addsection":
                addsection(request,response);
        }
    }

    private void addsection(HttpServletRequest request, HttpServletResponse response) {
        

            // 1) Lấy dữ liệu từ form
            String name = request.getParameter("namesections");
            String description = request.getParameter("descriptsection");

            // 2) courseID từ hidden input (có thể null)
            UUID courseID = null;
            String courseIDStr = request.getParameter("courseID");
            if (courseIDStr != null && !courseIDStr.isBlank()) {
                try { courseID = UUID.fromString(courseIDStr); } catch (IllegalArgumentException ignore) {}
            
            // 3) userID từ session (nếu có đăng nhập)
            UUID userID = null;
            User auth = (session != null) ? (User) session.getAttribute("authUser") : null;
            if (auth != null) userID = auth.getUserID();

            // 4) status mặc định (nếu không có checkbox)
            boolean status = true;
            // Nếu có checkbox ở form thì:
            // status = req.getParameter("status") != null;

            // 5) Validate tối thiểu
            if (name == null || name.isBlank()) {
                req.setAttribute("error", "Tên section là bắt buộc");
                req.getRequestDispatcher("/instructor/section-list.jsp").forward(req, resp);
                return;
            }

            // 6) Tạo model và insert
            Sections s = new Sections(
                    UUID.randomUUID(),   // sectionID tự tạo
                    userID,              // có thể null
                    status,              // mặc định
                    name.trim(),
                    (description == null ? null : description.trim()),
                    courseID             // có thể null
            );

            boolean ok = sectionDAO.insert(s);

            // 7) Điều hướng
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/instructor/sections?courseID=" +
                                  (courseID != null ? courseID.toString() : ""));
            } else {
                req.setAttribute("error", "Không tạo được Section");
                req.getRequestDispatcher("/instructor/section-list.jsp").forward(req, resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
        }
        
    }

    

}
