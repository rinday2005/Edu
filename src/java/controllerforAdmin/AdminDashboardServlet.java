package controllerforAdmin;

import DAO.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Basic role check: only Admin can access (header.jsp also guards)
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            request.setAttribute("totalUsers", scalar(con, "SELECT COUNT(*) FROM [dbo].[User]"));
            request.setAttribute("adminCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[User] WHERE role='Admin'"));
            request.setAttribute("instructorCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[User] WHERE role='Instructor'"));
            request.setAttribute("learnerCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[User] WHERE role='Learner'"));

            request.setAttribute("totalCourses", scalar(con, "SELECT COUNT(*) FROM [dbo].[Courses]"));
            request.setAttribute("approvedCourses", scalar(con, "SELECT COUNT(*) FROM [dbo].[Courses] WHERE isApproved=1"));
            request.setAttribute("pendingCourses", scalar(con, "SELECT COUNT(*) FROM [dbo].[Courses] WHERE isApproved=0"));

            request.setAttribute("sectionCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[Sections]"));
            request.setAttribute("lessonCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[Lession]"));
            request.setAttribute("assignmentCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[Assignment]"));

            request.setAttribute("articleCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[Article]"));
            request.setAttribute("commentCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[Comments]"));
            
            // Doanh thu: tạm tính số lượt mua (UserCourse) * giá course
            String revenueSql = "SELECT ISNULL(SUM(c.price),0) FROM dbo.UserCourse uc JOIN dbo.Courses c ON uc.courseID=c.courseID";
            request.setAttribute("totalRevenue", scalar(con, revenueSql));

            // Top 5 instructor theo doanh thu (dựa vào sum price các course được mua)
            String topInstructorSql = "SELECT TOP 5 c.userID, COUNT(*) as enrolls, ISNULL(SUM(c.price),0) as revenue " +
                    "FROM dbo.UserCourse uc JOIN dbo.Courses c ON uc.courseID=c.courseID " +
                    "GROUP BY c.userID ORDER BY revenue DESC";
            request.setAttribute("topInstructors", table(con, topInstructorSql));

            // Top 5 khóa học bán chạy
            String topCourseSql = "SELECT TOP 5 c.name, COUNT(*) as enrolls, ISNULL(SUM(c.price),0) as revenue " +
                    "FROM dbo.UserCourse uc JOIN dbo.Courses c ON uc.courseID=c.courseID " +
                    "GROUP BY c.name ORDER BY enrolls DESC";
            request.setAttribute("topCourses", table(con, topCourseSql));

            request.setAttribute("walletCount", scalar(con, "SELECT COUNT(*) FROM [dbo].[Wallet]"));
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }

        request.getRequestDispatcher("/admin/jsp/dashboard.jsp").forward(request, response);
    }

    private long scalar(Connection con, String sql) throws Exception {
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        }
        return 0L;
    }

    private java.util.List<java.util.Map<String,Object>> table(Connection con, String sql) throws Exception {
        java.util.List<java.util.Map<String,Object>> list = new java.util.ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                java.util.Map<String,Object> m = new java.util.HashMap<>();
                for (int i=1;i<=cols;i++) {
                    m.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
                }
                list.add(m);
            }
        }
        return list;
    }
}


