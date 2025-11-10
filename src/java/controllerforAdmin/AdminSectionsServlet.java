package controllerforAdmin;

import SectionsDAO.SectionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminSectionsServlet", urlPatterns = {"/admin/sections"})
public class AdminSectionsServlet extends HttpServlet {
    private final SectionDAO sectionDAO = new SectionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        try {
            request.setAttribute("sections", sectionDAO.findAll());
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/sections.jsp").forward(request, response);
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
        String sectionId = request.getParameter("sectionID");
        try {
            java.util.UUID id = java.util.UUID.fromString(sectionId);
            if ("delete".equals(action)) {
                sectionDAO.delete(id);
            } else if ("toggle".equals(action)) {
                // Đọc trạng thái hiện tại không tiện, ở đây trực tiếp đặt active = 1 (có thể mở rộng)
                sectionDAO.updateStatus(id, true);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/sections");
    }
}


