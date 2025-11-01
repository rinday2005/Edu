/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerforArtical;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import model.*;
import service.*;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageArtical", urlPatterns = {"/ManageArtical"})
public class ManageArtical extends HttpServlet {

    ArticleService articleservice = new ArticleService();

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
            out.println("<title>Servlet ManageArtical</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageArtical at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            
        }
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
            case "createArtical":
                createartical(request, response);
                break;
        }
    }

    private void createartical(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            // Ưu tiên lấy từ object user; fallback từ chuỗi userID
            UUID userID = null;
            Object u = session.getAttribute("user");
            if (u instanceof User) {
                userID = UUID.fromString(((User) u).getUserID());
            }
            if (userID == null) {
                String uidStr = (String) session.getAttribute("userID");
                if (uidStr != null) {
                    userID = UUID.fromString(uidStr);
                }
            }
            if (userID == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            String title = request.getParameter("titleartical");
            String content = request.getParameter("contentArtical");
            String status = request.getParameter("statusArtical"); // đã thêm name trong form

            UUID articleID = UUID.randomUUID();                 // PK NOT NULL
            java.util.Date now = new java.util.Date();

            Article article = new Article(articleID, userID, now, status, title, content);
            articleservice.create(article);

            response.sendRedirect(request.getContextPath() + "/instructor/jsp/ArticleManage.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Tạo bài viết thất bại: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
