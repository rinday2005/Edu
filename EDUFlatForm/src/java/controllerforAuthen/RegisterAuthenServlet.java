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
import model.User;
import service.UserServiceImpl;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterAuthenServlet extends HttpServlet {
    private UserServiceImpl userService = new UserServiceImpl();


    /** GET: nếu đã đăng nhập -> /course; chưa -> mở form register */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(response.encodeRedirectURL(
                request.getContextPath() + "/course"));
            return;
        }

        String redirect = request.getParameter("redirect");
        if (redirect != null) {
            request.setAttribute("redirect", redirect);
        }

        request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
    }

    /** POST: xử lý đăng ký */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = getParam(request, "username");
        String email    = getParam(request, "email");
        String password = getParam(request, "password");
        String fullName = getParam(request, "fullName");
        String redirect = request.getParameter("redirect");

        // Validate tối thiểu
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin!");
            request.setAttribute("redirect", redirect);
            request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
            return;
        }

        // Tạo user
        User u = new User();
        u.setUserID(java.util.UUID.randomUUID().toString());
        u.setUserName(username);
        u.setEmail(email);
        u.setFullName(fullName);
        // u.setPassword(hashPassWord.hash(password)); // nếu dùng hash
        u.setPassword(password); // nếu đang test plain-text
        u.setRole("Learner");
        u.setStatus(true);
        u.setEnrollmentCount(0);

        boolean ok = userService.createUser(u);
        if (!ok) {
            request.setAttribute("errorMessage", "Đăng ký thất bại! Vui lòng thử lại.");
            request.setAttribute("redirect", redirect);
            request.getRequestDispatcher("/authen/registerAuthen.jsp").forward(request, response);
            return;
        }

        // Auto-login
        HttpSession session = request.getSession(true);
        session.setAttribute("user", u);

        // Về trang trước / mặc định /course
        String target = (redirect != null && !redirect.isBlank())
                ? redirect
                : (request.getContextPath() + "/course");

        response.sendRedirect(response.encodeRedirectURL(target));
    }

    private String getParam(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }
}



