package controllerforAdmin;

import SectionsDAO.SectionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminSectionDetailServlet", urlPatterns = {"/admin/section/detail"})
public class AdminSectionDetailServlet extends HttpServlet {
    private final SectionDAO dao = new SectionDAO();
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
            request.setAttribute("section", dao.findById(id));
        } catch (Exception e) { request.setAttribute("error", e.getMessage()); }
        request.getRequestDispatcher("/admin/jsp/section-detail.jsp").forward(request,response);
    }
}


