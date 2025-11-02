/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerforInstructor;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;
import model.*;
import service.*;

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
            case "createlession": {
                try {
                    createcourse(request, response);
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

    private void createcourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLServerException {
        try {

            HttpSession session = request.getSession(false);
            if (session == null) {
                // Chưa đăng nhập hoặc mất cookie session
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            // Lấy user từ session (hỗ trợ cả "user" và "authUser" phòng nơi khác dùng tên khác)
            UUID userID = null;
            Object u = session.getAttribute("user");
            if (u == null) {
                u = session.getAttribute("authUser");
            }

            if (u instanceof User userObj) {
                // userObj.getUserID() có thể là UUID hoặc String -> ép kiểu an toàn
                Object idObj = userObj.getUserID();
                if (idObj instanceof UUID) {
                    userID = (UUID) idObj;
                } else if (idObj != null) {
                    userID = UUID.fromString(String.valueOf(idObj));
                }
            }
            // Dự phòng: nếu bạn còn set "userID" (String) riêng trong Login
            if (userID == null) {
                Object uidAttr = session.getAttribute("userID");
                if (uidAttr != null) {
                    userID = UUID.fromString(String.valueOf(uidAttr));
                }
            }

            if (userID == null) {
                // Có session nhưng thiếu thông tin người dùng -> coi như chưa đăng nhập
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }
            String name = request.getParameter("namecourse");
            String description = request.getParameter("descriptioncourse");
            int price = Integer.parseInt(request.getParameter("pricecourse"));
            String level = request.getParameter("levelcourse");
            String status = request.getParameter("status"); // Draft/Published/...

            Part img = request.getPart("picturecourse");
            String imgURL = null;
            if (img != null && img.getSize() > 0) {
                String fileName = Paths.get(img.getSubmittedFileName()).getFileName().toString();
                File uploadDir = new File(getServletContext().getRealPath("/uploads/course"));
                uploadDir.mkdirs();
                File saved = new File(uploadDir, fileName);
                img.write(saved.getAbsolutePath());
                imgURL = request.getContextPath() + "/uploads/course/" + fileName;
            }

            UUID courseID = UUID.randomUUID();
            Courses course = new Courses(courseID, userID, name, description, imgURL, 0, price, level, false);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
