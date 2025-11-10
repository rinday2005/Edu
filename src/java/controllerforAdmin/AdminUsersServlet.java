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
        String action = trim(request.getParameter("action"));
        String userId = trim(request.getParameter("userID"));
        boolean shouldRedirect = true;
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
            } else if ("create".equals(action)) {
                shouldRedirect = handleCreateUser(request, response);
                if (!shouldRedirect) return;
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        if (shouldRedirect) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }

    private boolean handleCreateUser(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String fullName = trim(request.getParameter("fullName"));
        String email = trim(request.getParameter("email"));
        String phone = trim(request.getParameter("phone"));
        String role = trim(request.getParameter("role"));
        String password = trim(request.getParameter("password"));
        String confirmPassword = trim(request.getParameter("confirmPassword"));
        String statusRaw = trim(request.getParameter("status"));

        if (role.isEmpty()) {
            role = "Learner";
        }
        if (statusRaw.isEmpty()) {
            statusRaw = "Active";
        }

        java.util.Map<String, String> formData = new java.util.HashMap<>();
        formData.put("fullName", fullName);
        formData.put("email", email);
        formData.put("phone", phone);
        formData.put("role", role);
        formData.put("status", statusRaw);

        java.util.List<String> errors = new java.util.ArrayList<>();
        if (fullName.isEmpty()) errors.add("Họ và tên không được để trống.");
        if (email.isEmpty()) {
            errors.add("Email không được để trống.");
        } else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            errors.add("Email không hợp lệ.");
        }
        if (password.isEmpty()) errors.add("Mật khẩu không được để trống.");
        if (!password.equals(confirmPassword)) errors.add("Mật khẩu xác nhận không khớp.");

        model.User existed = userDAO.getUserByEmail(email);
        if (existed != null) {
            errors.add("Email đã tồn tại trong hệ thống.");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("formErrors", errors);
            request.setAttribute("formData", formData);
            doGet(request, response);
            return false;
        }

        model.User newUser = new model.User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setUserName(email);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phone);
        newUser.setRole(role);
        boolean active = !"Locked".equalsIgnoreCase(statusRaw);
        newUser.setStatus(active);
        Object adminId = request.getSession().getAttribute("userID");
        if (adminId != null) {
            newUser.setLastModifiedID(String.valueOf(adminId));
        }

        boolean ok = userDAO.createUser(newUser);
        if (ok) {
            request.getSession().setAttribute("flashSuccess", "Tạo tài khoản mới thành công.");
        } else {
            request.getSession().setAttribute("flashError", "Không thể tạo tài khoản. Vui lòng thử lại.");
        }
        return true;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}


