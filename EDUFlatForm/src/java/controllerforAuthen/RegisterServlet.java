/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerforAuthen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import model.User;
import service.UserServiceImpl;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private UserServiceImpl userService = new UserServiceImpl();


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(response.encodeRedirectURL(
                    request.getContextPath() + "/course"));
            return;
        }

        // Hiển thị form đăng ký
        request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username") == null ? "" : request.getParameter("username").trim();
        String email = request.getParameter("email") == null ? "" : request.getParameter("email").trim();
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone").trim();
        String password = request.getParameter("password") == null ? "" : request.getParameter("password").trim();
        String confirmPassword = request.getParameter("confirmPassword") == null ? "" : request.getParameter("confirmPassword").trim();

        // Validation
        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin!");
            request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
            return;
        }

        if (password.length() < 6) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự!");
            request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email đã tồn tại chưa
        User existingUser = userService.getUserByEmail(email);
        if (existingUser != null) {
            request.setAttribute("errorMessage", "Email này đã được sử dụng!");
            request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
            return;
        }

        // Tạo user mới
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phone);
        newUser.setPassword(password);
        newUser.setLoginProvider("Local");
        newUser.setStatus(true);

        boolean success = userService.createUser(newUser);
        
        if (success) {
            // Đăng ký thành công: chuyển tới trang đăng nhập, không tự đăng nhập
            String redirectAfterLogin = request.getContextPath() + "/course"; // trang chủ
            String loginUrl = request.getContextPath() + "/login?registered=1&redirect=" +
                    URLEncoder.encode(redirectAfterLogin, StandardCharsets.UTF_8);
            response.sendRedirect(response.encodeRedirectURL(loginUrl));
            return;
        }

        // Lỗi khi tạo user
        request.setAttribute("errorMessage", "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại!");
        request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Register Servlet";
    }
}

