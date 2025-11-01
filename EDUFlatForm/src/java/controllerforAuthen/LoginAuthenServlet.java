/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerforAuthen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import model.User;
import service.UserServiceImpl;
import service.UserServiceImpl;

/**
 *
 * @author Hieu
 */
@WebServlet(name = "LoginAuthenServlet", urlPatterns = {"/login"})
public class LoginAuthenServlet extends HttpServlet {

    private UserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    // Gợi ý: đừng invalidate tại /login GET (sẽ coi như logout). 
    // Nếu muốn logout, tạo servlet /logout riêng để tránh xóa session khi người dùng chỉ mở trang login.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("input") != null ? request.getParameter("input").trim() : "";
        String password = request.getParameter("password") != null ? request.getParameter("password").trim() : "";
        String rememberMe = request.getParameter("rememberMe");

        User user = userService.checkLoginUser(username, password);

        if (user != null) {
            // ✅ Tạo/lấy session
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            // ✅ Lưu thêm userID (UUID -> String) và role để các servlet khác dùng nhanh
            if (user.getUserID() != null) {
                session.setAttribute("userID", user.getUserID().toString());
            }
            session.setAttribute("role", user.getRole());

            // (khuyến nghị) thời gian sống session, ví dụ 60 phút
            session.setMaxInactiveInterval(60 * 60);

            // ✅ Cookie Remember Me (tùy chọn)
            if ("on".equals(rememberMe)) {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                usernameCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                usernameCookie.setHttpOnly(true);
                // SameSite có thể cần tùy context (LAX/STRICT)
                response.addCookie(usernameCookie);
            }

            // ✅ Điều hướng theo role
            String ctx = request.getContextPath();
            switch (String.valueOf(user.getRole())) {
                case "Admin":
                    response.sendRedirect(ctx + "/admin/jsp/admindashboard.jsp");
                    break;
                case "Instructor":
                    response.sendRedirect(ctx + "/instructor/jsp/InstructorDashboard.jsp");
                    break;
                default:
                    response.sendRedirect(ctx + "/learner/jsp/Home/home.jsp");
                    break;
            }
            return;
        }

        // ❌ Sai thông tin đăng nhập
        request.setAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu!");
        request.getRequestDispatcher("/login/jsp/login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login Authentication Servlet";
    }
}
