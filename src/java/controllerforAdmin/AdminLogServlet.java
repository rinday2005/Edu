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

@WebServlet(name = "AdminLogServlet", urlPatterns = {"/admin/logs"})
public class AdminLogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        String sql =
            "SELECT 'User' AS tableName, userID AS entityID, lastModifiedAt AS ts, lastModifiedID AS byId FROM dbo.[User] WHERE lastModifiedAt IS NOT NULL "+
            "UNION ALL "+
            "SELECT 'Courses', courseID, NULL, NULL FROM dbo.Courses "+
            "UNION ALL "+
            "SELECT 'Sections', sectionID, NULL, NULL FROM dbo.Sections "+
            "UNION ALL "+
            "SELECT 'Lession', lessionID, NULL, NULL FROM dbo.Lession "+
            "UNION ALL "+
            "SELECT 'Assignment', assignmentID, NULL, NULL FROM dbo.Assignment "+
            "UNION ALL "+
            "SELECT 'Article', articleID, createAt, userID FROM dbo.Article "+
            "UNION ALL "+
            "SELECT 'Comments', commentID, createAt, userID FROM dbo.Comments "+
            "ORDER BY ts DESC";
        java.util.List<java.util.Map<String,Object>> logs = new java.util.ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                java.util.Map<String,Object> m = new java.util.HashMap<>();
                m.put("tableName", rs.getString("tableName"));
                m.put("entityID", String.valueOf(rs.getObject("entityID")));
                m.put("ts", rs.getObject("ts"));
                m.put("byId", String.valueOf(rs.getObject("byId")));
                logs.add(m);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/admin/jsp/logs.jsp").forward(request, response);
    }
}


