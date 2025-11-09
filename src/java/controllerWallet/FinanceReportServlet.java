/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerWallet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import model.User;
import model.Wallet;
import service.WalletService;

/**
 *
 * @author trank
 */
@WebServlet(name = "FinanceReportServlet", urlPatterns = {"/FinanceReportServlet"})
public class FinanceReportServlet extends HttpServlet {
    private WalletService WS = new  WalletService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        UUID userID = UUID.fromString(user.getUserID().toString());
        
        Wallet wallet = WS.findById(userID);
        Boolean check = WS.existsByUserId(userID);
        request.setAttribute("price",wallet );
        request.setAttribute("check",check );
        
        request.getRequestDispatcher("/instructor/jsp/FinanceReport.jsp").forward(request, response);
    }
}
