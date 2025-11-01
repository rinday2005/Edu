/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import service.UserServiceImpl;
import util.AvatarUploadUtil;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
@WebServlet(name = "LearnerControllerServlet", urlPatterns = {"/learner"})
public class LearnerControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private UserServiceImpl userService = new UserServiceImpl();


    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LearnerControllerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LearnerControllerServlet at " + request.getContextPath() + "</h1>");
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
        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }
        switch(action.toLowerCase()){
            case "setting":
                showSettingPage(request,response);
                break;
            default:
                showLearnerHome(request,response);
                break;
        }
                
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }

        switch (action.toLowerCase()) {
            case "create":
//                handleCreateLearner(request, response);
                break;
            case "editinfo":
            {
                try {
                    handleUpdateLearnerPro(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(LearnerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

            case "changepassword":
                try {
                    handleChangePassword(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(LearnerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "delete":
//                handleDeleteLearner(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported action: " + action);
                break;
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

    private void handleUpdateLearnerPro(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login/loginRole.jsp");
            return;
        }      
            String fullName = param(request, "fullName");
            String email = param(request, "email");
            String phone = param(request, "phone");
            String bio = param(request, "bio");
            String birthday = param(request, "birthday");

            sessionUser.setFullName(fullName);
            sessionUser.setEmail(email);
            sessionUser.setPhoneNumber(phone);
            sessionUser.setBio(bio);
        
        if (birthday != null && !birthday.isEmpty()) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
            Date parsedDate = sdf.parse(birthday);
            sessionUser.setDateofbirth(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
}

        // --- Upload Avatar ---
        Part avatarPart = request.getPart("avatar");
        String avatarUrl = AvatarUploadUtil.uploadFile(
            avatarPart,
            request.getServletContext(),
            request.getContextPath(),
            "uploads/avatar/learner" // bạn có thể đổi thư mục tùy context
        );
        if (avatarUrl != null) {
            sessionUser.setAvatarUrl(avatarUrl);
        }
        // --- Update DB ---
        boolean updated = false;
        try {
            updated = userService.updateProfile(sessionUser);
        } catch (Exception e) {       
            e.printStackTrace();
        }

        if (updated) {
            session.setAttribute("user", sessionUser);
            request.setAttribute("message", "Cập nhật thông tin thành công!");
        } else {
            request.setAttribute("error", "Cập nhật thất bại, vui lòng thử lại!");
        }        
        request.getRequestDispatcher("/learner/setting.jsp").forward(request, response);
        
    }
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String currentPassword = param(request, "currentPassword");
        String newPassword = param(request, "newPassword");
        String confirmPassword = param(request, "confirmPassword");

        // Kiểm tra các trường bắt buộc
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            request.getRequestDispatcher("/learner/setting.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận có khớp không
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận không khớp!");
            request.getRequestDispatcher("/learner/setting.jsp").forward(request, response);
            return;
        }

        // Kiểm tra độ dài mật khẩu mới
        if (newPassword.length() < 6) {
            request.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự!");
            request.getRequestDispatcher("/learner/setting.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!currentPassword.equals(sessionUser.getPassword())) {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng!");
            request.getRequestDispatcher("/learner/setting.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu trong database
        boolean updated = userService.updatePassword(sessionUser.getEmail(), newPassword);

        if (updated) {
            // Cập nhật session user với mật khẩu mới
            sessionUser.setPassword(newPassword);
            session.setAttribute("user", sessionUser);
            request.setAttribute("message", "Đổi mật khẩu thành công!");
        } else {
            request.setAttribute("error", "Đổi mật khẩu thất bại, vui lòng thử lại!");
        }

        request.getRequestDispatcher("/learner/setting.jsp").forward(request, response);
    }

    private String param(HttpServletRequest request, String name) {
        String v = request.getParameter(name);
        return v != null ? v.trim() : "";
    }

    private void showSettingPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/learner/setting.jsp");
        rd.forward(request, response);
    }

    private void showLearnerHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/course");
        rd.forward(request, response);
    }
}
