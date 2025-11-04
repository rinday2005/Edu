package controllerforAdmin;

import DAO.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(name = "AdminTransactionsServlet", urlPatterns = {"/admin/transactions"})
public class AdminTransactionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        List<Map<String, Object>> rows = new ArrayList<>();
        String sql = "SELECT uc.userCourseID, uc.userID, uc.courseID FROM dbo.UserCourse uc ORDER BY uc.userCourseID";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                m.put("userCourseID", String.valueOf(rs.getObject("userCourseID")));
                m.put("userID", String.valueOf(rs.getObject("userID")));
                m.put("courseID", String.valueOf(rs.getObject("courseID")));
                rows.add(m);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("rows", rows);
        request.getRequestDispatcher("/admin/jsp/transactions.jsp").forward(request, response);
    }
}


