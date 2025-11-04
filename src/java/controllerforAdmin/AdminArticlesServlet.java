package controllerforAdmin;

import ArticalDAO.ArticleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminArticlesServlet", urlPatterns = {"/admin/articles"})
public class AdminArticlesServlet extends HttpServlet {
    private final ArticleDAO articleDAO = new ArticleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        try {
            request.setAttribute("articles", articleDAO.findAll());
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/articles.jsp").forward(request, response);
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
        String articleId = request.getParameter("articleID");
        try {
            java.util.UUID id = java.util.UUID.fromString(articleId);
            ArticalDAO.ArticleDAO dao = this.articleDAO;
            if ("delete".equals(action)) {
                dao.deleteById(id);
            } else if ("hide".equals(action)) {
                dao.updateStatus(id, "hidden");
            } else if ("show".equals(action)) {
                dao.updateStatus(id, "public");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/articles");
    }
}


