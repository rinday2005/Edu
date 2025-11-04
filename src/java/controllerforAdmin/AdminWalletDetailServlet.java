package controllerforAdmin;

import WalletDAO.WalletDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "AdminWalletDetailServlet", urlPatterns = {"/admin/wallet/detail"})
public class AdminWalletDetailServlet extends HttpServlet {
    private final WalletDAO dao = new WalletDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath()+"/login/jsp/login.jsp");
            return;
        }
        try {
            UUID id = UUID.fromString(request.getParameter("id"));
            request.setAttribute("wallet", dao.findById(id));
        } catch (Exception e) { request.setAttribute("error", e.getMessage()); }
        request.getRequestDispatcher("/admin/jsp/wallet-detail.jsp").forward(request,response);
    }
}


