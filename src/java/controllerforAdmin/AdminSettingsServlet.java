package controllerforAdmin;

import UserDAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminSettingsServlet", urlPatterns = {"/admin/settings"})
public class AdminSettingsServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        request.getRequestDispatcher("/admin/jsp/settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        try {
            model.User u = (model.User) request.getSession().getAttribute("user");
            if (u == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phoneNumber");
            String avatar = request.getParameter("avatarUrl");
            u.setFullName(fullName);
            u.setEmail(email);
            u.setPhoneNumber(phone);
            u.setAvatarUrl(avatar);
            userDAO.updateProfile(u);
            request.getSession().setAttribute("user", u);
            request.setAttribute("message", "Cập nhật thành công");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/settings.jsp").forward(request, response);
    }

    private boolean isAdmin(HttpSession s) {
        Object role = s.getAttribute("role");
        return role != null && "Admin".equals(String.valueOf(role));
    }
}


