package controllerforAdmin;

import WalletDAO.WalletDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminWalletsServlet", urlPatterns = {"/admin/wallets"})
public class AdminWalletsServlet extends HttpServlet {
    private final WalletDAO walletDAO = new WalletDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        request.setAttribute("wallets", walletDAO.findAll());
        request.getRequestDispatcher("/admin/jsp/wallets.jsp").forward(request, response);
    }
}


