package controllerforAdmin;

import DAO.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "AdminCommentDetailServlet", urlPatterns = {"/admin/comment/detail"})
public class AdminCommentDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath()+"/login/jsp/login.jsp");
            return;
        }
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM dbo.Comments WHERE commentID=?")) {
            ps.setString(1, request.getParameter("id"));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.util.Map<String,Object> m = new java.util.HashMap<>();
                    m.put("commentID", String.valueOf(rs.getObject("commentID")));
                    m.put("userID", String.valueOf(rs.getObject("userID")));
                    m.put("createAt", rs.getObject("createAt"));
                    m.put("content", rs.getString("content"));
                    m.put("articleID", String.valueOf(rs.getObject("articleID")));
                    m.put("lessionID", String.valueOf(rs.getObject("lessionID")));
                    request.setAttribute("comment", m);
                }
            }
        } catch (Exception e) { request.setAttribute("error", e.getMessage()); }
        request.getRequestDispatcher("/admin/jsp/comment-detail.jsp").forward(request,response);
    }
}


