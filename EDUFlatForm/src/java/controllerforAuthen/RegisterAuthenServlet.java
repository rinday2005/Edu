package controllerforAuthen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import model.User;
import service.UserServiceImpl;

@WebServlet(name = "RegisterAuthenServlet", urlPatterns = {"/register"})
public class RegisterAuthenServlet extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ✅ chỉ mở form đăng ký
        request.getRequestDispatcher("/login/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = getParam(request, "username");
        String email = getParam(request, "email");
        String password = getParam(request, "password");
        String fullName = getParam(request, "fullName");

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin!");
            request.getRequestDispatcher("/login/jsp/register.jsp").forward(request, response);
            return;
        }

        User u = new User();
        u.setUserID(UUID.randomUUID().toString());
        u.setUserName(username);
        u.setEmail(email);
        u.setFullName(fullName);
        u.setPassword(password);
        u.setRole("Learner");
        u.setStatus(true);
        u.setEnrollmentCount(0);

        boolean ok = userService.createUser(u);
        if (!ok) {
            request.setAttribute("errorMessage", "Đăng ký thất bại! Email hoặc tài khoản đã tồn tại.");
            request.getRequestDispatcher("/login/jsp/register.jsp").forward(request, response);
            return;
        }

        // ✅ Sau khi đăng ký thành công, chuyển về trang đăng nhập
        response.sendRedirect(request.getContextPath() + "/login");
    }

    private String getParam(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }
}
