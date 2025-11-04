package controllerforAdmin;

import UserDAO.UserDAO;
import CourseDAO.CourseDAO;
import WalletDAO.WalletDAO;
import ArticalDAO.ArticleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "AdminUserDetailServlet", urlPatterns = {"/admin/user/detail"})
public class AdminUserDetailServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final WalletDAO walletDAO = new WalletDAO();
    private final ArticleDAO articleDAO = new ArticleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }
        try {
            model.User user = userDAO.getUserById(id);
            request.setAttribute("user", user);

            // Courses của user (nếu là Instructor: tạo; nếu là Learner: đã đăng ký → TODO nếu có DAO riêng)
            java.util.List<model.Courses> all = courseDAO.findAll();
            java.util.List<model.Courses> created = new ArrayList<>();
            for (model.Courses c : all) {
                if (c.getUserID() != null && c.getUserID().toString().equals(id)) created.add(c);
            }
            request.setAttribute("createdCourses", created);

            // Wallet
            try {
                java.util.UUID uid = java.util.UUID.fromString(id);
                request.setAttribute("wallet", walletDAO.findByUserId(uid));
            } catch (Exception ignore) {}

            // Articles
            java.util.List<model.Article> arts = articleDAO.findAll();
            java.util.List<model.Article> mine = new ArrayList<>();
            for (model.Article a : arts) {
                if (a.getUserID() != null && a.getUserID().toString().equals(id)) mine.add(a);
            }
            request.setAttribute("articles", mine);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/user-detail.jsp").forward(request, response);
    }
}




