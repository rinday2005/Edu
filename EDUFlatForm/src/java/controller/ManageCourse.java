/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageLession", urlPatterns = {"/ManageLession"})
public class ManageCourse extends HttpServlet {

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
            out.println("<title>Servlet ManageLession</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageLession at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "createlession":
            {
                try {
                    createLession(request,response);
                } catch (SQLServerException ex) {
                    System.getLogger(ManageCourse.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }

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

    private void createLession(HttpServletRequest request, HttpServletResponse response) 
    throws SQLServerException{
        try {

            String name = request.getParameter("name");
            long price = Long.parseLong(request.getParameter("price"));
            String description = request.getParameter("description");
            int stock = Integer.parseInt(request.getParameter("stock"));
            LocalDate import_date = LocalDate.parse(request.getParameter("import_date")); 
            java.sql.Date day = java.sql.Date.valueOf(import_date);
//            Product pro = new Product(name, price, description, stock, import_date);
//            productservice.insertProduct(pro);
            response.sendRedirect(request.getContextPath() + "/ProductServlet?action=listforadmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
           
    }

}
