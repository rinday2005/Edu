package controllerforAdmin;

import UserDAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminUsersServlet", urlPatterns = {"/admin/users"})
public class AdminUsersServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        try {
            String roleFilter = request.getParameter("role");
            java.util.List<model.User> all = userDAO.getAllUsers();
            if (roleFilter != null && !roleFilter.isEmpty()) {
                java.util.List<model.User> filtered = new java.util.ArrayList<>();
                for (model.User u : all) {
                    if (roleFilter.equalsIgnoreCase(String.valueOf(u.getRole()))) filtered.add(u);
                }
                request.setAttribute("users", filtered);
            } else {
                request.setAttribute("users", all);
            }
            request.setAttribute("roleFilter", roleFilter);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        String action = request.getParameter("action");
        String userId = request.getParameter("userID");
        try {
            if ("lock".equals(action)) {
                // 置 status = 0
                try (java.sql.Connection con = DAO.DBConnection.getConnection();
                     java.sql.PreparedStatement ps = con.prepareStatement("UPDATE [dbo].[User] SET status=0, lastModifiedAt=GETDATE() WHERE userID=?")) {
                    ps.setString(1, userId);
                    ps.executeUpdate();
                }
            } else if ("unlock".equals(action)) {
                try (java.sql.Connection con = DAO.DBConnection.getConnection();
                     java.sql.PreparedStatement ps = con.prepareStatement("UPDATE [dbo].[User] SET status=1, lastModifiedAt=GETDATE() WHERE userID=?")) {
                    ps.setString(1, userId);
                    ps.executeUpdate();
                }
            } else if ("delete".equals(action)) {
                try (java.sql.Connection con = DAO.DBConnection.getConnection();
                     java.sql.PreparedStatement ps = con.prepareStatement("DELETE FROM [dbo].[User] WHERE userID=?")) {
                    ps.setString(1, userId);
                    ps.executeUpdate();
                }
            } else if ("updateRole".equals(action)) {
                String roleNew = request.getParameter("role");
                try (java.sql.Connection con = DAO.DBConnection.getConnection();
                     java.sql.PreparedStatement ps = con.prepareStatement("UPDATE [dbo].[User] SET role=?, lastModifiedAt=GETDATE() WHERE userID=?")) {
                    ps.setString(1, roleNew);
                    ps.setString(2, userId);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}


