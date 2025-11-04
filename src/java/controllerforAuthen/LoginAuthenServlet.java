package controllerforAuthen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;
import service.UserServiceImpl;

@WebServlet(name = "LoginAuthenServlet", urlPatterns = {"/login"})
public class LoginAuthenServlet extends HttpServlet {

    private UserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ✅ hiển thị form login
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
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            if (user.getUserID() != null) {
                session.setAttribute("userID", user.getUserID().toString());
            }
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(60 * 60);

            if ("on".equals(rememberMe)) {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(7 * 24 * 60 * 60);
                usernameCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                usernameCookie.setHttpOnly(true);
                response.addCookie(usernameCookie);
            }

            String ctx = request.getContextPath();
            switch (String.valueOf(user.getRole())) {
                case "Admin":
                    response.sendRedirect(ctx + "/admin/dashboard");
                    break;
                case "Instructor":
                    response.sendRedirect(ctx + "/instructor/jsp/InstructorDashboard.jsp");
                    break;
                default:
                    response.sendRedirect(ctx + "/CourseServletController");
                    break;
            }
            return;
        }

        request.setAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu!");
        request.getRequestDispatcher("/login/jsp/login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login Authentication Servlet";
    }
}
