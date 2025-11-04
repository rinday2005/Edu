package controllerforAdmin;

import CourseDAO.CourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminApprovalsServlet", urlPatterns = {"/admin/approvals"})
public class AdminApprovalsServlet extends HttpServlet {
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
            java.util.List<model.Courses> all = courseDAO.findAll();
            java.util.List<model.Courses> pending = new java.util.ArrayList<>();
            for (model.Courses c : all) {
                if (!c.isApproved()) pending.add(c);
            }
            request.setAttribute("pendingCourses", pending);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/approvals.jsp").forward(request, response);
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
            if ("approve".equals(action)) {
                courseDAO.updateIsApproved(id, true);
            } else if ("reject".equals(action)) {
                courseDAO.updateIsApproved(id, false);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/approvals");
    }
}


