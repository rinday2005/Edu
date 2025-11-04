package controllerforAdmin;

import AssignmentDAO.AssignmentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminAssignmentDetailServlet", urlPatterns = {"/admin/assignment/detail"})
public class AdminAssignmentDetailServlet extends HttpServlet {
    private final AssignmentDAO dao = new AssignmentDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath()+"/login/jsp/login.jsp");
            return;
        }
        try {
            java.util.UUID id = java.util.UUID.fromString(request.getParameter("id"));
            request.setAttribute("assignment", dao.findById(id));
        } catch (Exception e) { request.setAttribute("error", e.getMessage()); }
        request.getRequestDispatcher("/admin/jsp/assignment-detail.jsp").forward(request,response);
    }
}


