package controllerforInstructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import model.User;
import model.Courses;
import service.WalletService;
import CourseDAO.CourseDAO;
import CourseDAO.ICourseDAO;
import DAO.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "InstructorDashboardServlet", urlPatterns = {"/instructor/dashboard"})
public class InstructorDashboardServlet extends HttpServlet {

    private WalletService walletService = new WalletService();
    private ICourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }

        Object userObj = session.getAttribute("user");
        if (userObj == null || !(userObj instanceof User)) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }

        User user = (User) userObj;
        
        // Check if user is Instructor or Admin (Admin toàn quyền đóng 2 vai trò)
        String role = user.getRole();
        if (role == null || (!role.equals("Instructor") && !role.equals("Admin"))) {
            response.sendRedirect(request.getContextPath() + "/CourseServletController");
            return;
        }

        try {
            UUID userID = UUID.fromString(user.getUserID());
            
            // Load total income (balance) for instructor
            int price = walletService.getBalanceByUserID(userID);
            request.setAttribute("price", price);
            
            // Load courses created by this instructor
            List<Courses> courses = courseDAO.findByUserId(userID);
            int totalCourses = courses != null ? courses.size() : 0;
            
            // Count draft and published courses
            int draftCount = 0;
            int publishedCount = 0;
            if (courses != null) {
                for (Courses course : courses) {
                    if (course.isApproved()) {
                        publishedCount++;
                    } else {
                        draftCount++;
                    }
                }
            }
            
            request.setAttribute("totalCourses", totalCourses);
            request.setAttribute("draftCount", draftCount);
            request.setAttribute("publishedCount", publishedCount);
// Count total students enrolled in instructor's courses
            int totalStudents = 0;
            if (courses != null && !courses.isEmpty()) {
                try (Connection con = DBConnection.getConnection()) {
                    if (con != null) {
                        // Count distinct students who enrolled in any of instructor's courses
                        String sql = "SELECT COUNT(DISTINCT uc.userID) as studentCount " +
                                     "FROM dbo.UserCourse uc " +
                                     "INNER JOIN dbo.Courses c ON uc.courseID = c.courseID " +
                                     "WHERE c.userID = ?";
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setString(1, userID.toString());
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    totalStudents = rs.getInt("studentCount");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("[InstructorDashboardServlet] Error counting students: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            request.setAttribute("totalStudents", totalStudents);
            
            // Forward to dashboard JSP
            request.getRequestDispatcher("/instructor/jsp/InstructorDashboard.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("[InstructorDashboardServlet] Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
            // Set default values if error
            request.setAttribute("price", 0);
            request.setAttribute("totalCourses", 0);
            request.setAttribute("draftCount", 0);
            request.setAttribute("publishedCount", 0);
            request.setAttribute("totalStudents", 0);
            request.getRequestDispatcher("/instructor/jsp/InstructorDashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
