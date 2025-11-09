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
import service.WalletService;

/**
 *
 * @author trank
 */
@WebServlet(name = "tranferServlet", urlPatterns = {"/tranferServlet"})
public class tranferServlet extends HttpServlet {
    private WalletService WS = new  WalletService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        UUID userID = UUID.fromString(user.getUserID().toString());
        int price = Integer.parseInt(request.getParameter("withdrawAmount"));
        
        WS.deductBalance(userID, price);
        
        request.getRequestDispatcher("FinanceReportServlet").forward(request, response);
        
    }
}