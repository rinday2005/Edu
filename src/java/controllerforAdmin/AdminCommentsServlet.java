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

@WebServlet(name = "AdminCommentsServlet", urlPatterns = {"/admin/comments"})
public class AdminCommentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        List<Map<String, Object>> rows = new ArrayList<>();
        String sql = "SELECT commentID, userID, createAt, content, articleID, lessionID FROM dbo.Comments ORDER BY createAt DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                m.put("commentID", String.valueOf(rs.getObject("commentID")));
                m.put("userID", String.valueOf(rs.getObject("userID")));
                m.put("createAt", rs.getDate("createAt"));
                m.put("content", rs.getString("content"));
                m.put("articleID", String.valueOf(rs.getObject("articleID")));
                m.put("lessionID", String.valueOf(rs.getObject("lessionID")));
                rows.add(m);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("rows", rows);
        request.getRequestDispatcher("/admin/jsp/comments.jsp").forward(request, response);
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
        String id = request.getParameter("commentID");
        try (Connection con = DBConnection.getConnection()) {
            if ("delete".equals(action)) {
                try (PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.Comments WHERE commentID=?")) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/comments");
    }
}


