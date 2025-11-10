package controllerforAdmin;

import DAO.DBConnection;
import CommentReportDAO.CommentReportDAO;
import CommentReportDAO.ICommentReportDAO;
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
    private final CommentReportDAO reportDAO = new CommentReportDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        
        String tab = request.getParameter("tab");
        if (tab == null) tab = "all";
        
        List<Map<String, Object>> rows = new ArrayList<>();
        String sql = "SELECT commentID, userID, createAt, content, articleID, lessionID FROM dbo.Comments ORDER BY createAt DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                String commentID = String.valueOf(rs.getObject("commentID"));
                m.put("commentID", commentID);
                m.put("userID", String.valueOf(rs.getObject("userID")));
                m.put("createAt", rs.getDate("createAt"));
                m.put("content", rs.getString("content"));
                m.put("articleID", String.valueOf(rs.getObject("articleID")));
                m.put("lessionID", String.valueOf(rs.getObject("lessionID")));
                
                // Get report count for this comment
                try {
                    java.util.UUID commentUUID = java.util.UUID.fromString(commentID);
                    List<model.CommentReport> reports = reportDAO.findByCommentId(commentUUID);
                    long pendingReports = reports.stream().filter(r -> "pending".equals(r.getStatus())).count();
                    m.put("reportCount", reports.size());
                    m.put("pendingReports", pendingReports);
                } catch (Exception e) {
                    m.put("reportCount", 0);
                    m.put("pendingReports", 0);
                }
                
                rows.add(m);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        
        // Filter by tab
        if ("reported".equals(tab)) {
            rows.removeIf(m -> ((Number)m.get("pendingReports")).intValue() == 0);
        }
        
        request.setAttribute("rows", rows);
        request.setAttribute("currentTab", tab);
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
        String reportIdStr = request.getParameter("reportID");
        String status = request.getParameter("status");
        
        try (Connection con = DBConnection.getConnection()) {
            if ("delete".equals(action)) {
                try (PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.Comments WHERE commentID=?")) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                    // Also delete related reports
                    if (id != null) {
                        try {
                            java.util.UUID commentUUID = java.util.UUID.fromString(id);
                            List<model.CommentReport> reports = reportDAO.findByCommentId(commentUUID);
                            for (model.CommentReport r : reports) {
                                reportDAO.delete(r.getReportID());
                            }
                        } catch (Exception e) {
                            // Ignore
                        }
                    }
                    request.getSession().setAttribute("success", "Đã xóa bình luận thành công!");
                }
            } else if ("updateReportStatus".equals(action) && reportIdStr != null && status != null) {
                // Update report status (resolve or dismiss)
                try {
                    java.util.UUID reportID = java.util.UUID.fromString(reportIdStr);
                    boolean ok = reportDAO.updateStatus(reportID, status);
                    if (ok) {
                        request.getSession().setAttribute("success", "Đã cập nhật trạng thái báo cáo!");
                    } else {
                        request.getSession().setAttribute("error", "Không thể cập nhật trạng thái báo cáo!");
                    }
                } catch (Exception e) {
                    request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/comments");
    }
}


