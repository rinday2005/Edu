package controllerforAdmin;

import ArticalDAO.ArticleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminArticleDetailServlet", urlPatterns = {"/admin/article/detail"})
public class AdminArticleDetailServlet extends HttpServlet {
    private final ArticleDAO dao = new ArticleDAO();
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
            request.setAttribute("article", dao.findById(id));
        } catch (Exception e) { request.setAttribute("error", e.getMessage()); }
        request.getRequestDispatcher("/admin/jsp/article-detail.jsp").forward(request,response);
    }
}


