package controllerforAdmin;

import CourseDAO.CourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCoursesServlet", urlPatterns = {"/admin/courses"})
public class AdminCoursesServlet extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        try {
            // Chỉ hiển thị các khóa học đã được duyệt (isApproved=true)
            java.util.List<model.Courses> allCourses = courseDAO.findAll();
            java.util.List<model.Courses> approvedCourses = new java.util.ArrayList<>();
            for (model.Courses c : allCourses) {
                if (c.isApproved()) {
                    approvedCourses.add(c);
                }
            }
            request.setAttribute("courses", approvedCourses);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/courses.jsp").forward(request, response);
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
        String courseId = request.getParameter("courseID");
        try {
            java.util.UUID id = java.util.UUID.fromString(courseId);
            if ("remove".equals(action)) {
                // Gỡ duyệt: đặt isApproved thành false, khóa học sẽ chuyển sang trang duyệt
                courseDAO.updateIsApproved(id, false);
                request.getSession().setAttribute("success", "Đã gỡ khóa học khỏi danh sách đã duyệt!");
            } else if ("delete".equals(action)) {
                boolean deleted = courseDAO.delete(id);
                if (deleted) {
                    request.getSession().setAttribute("success", "Đã xóa khóa học thành công!");
                } else {
                    request.getSession().setAttribute("error", "Không thể xóa khóa học. Có thể do lỗi dữ liệu hoặc ràng buộc.");
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/courses");
    }
}


