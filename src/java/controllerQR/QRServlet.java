package controllerQR;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import model.Courses;
import model.User;
import sePay.randomKey;
import service.CartService;

/**
 *
 * @author trank
 */
@WebServlet(name="QRServlet", urlPatterns = {"/QRServlet"})
public class QRServlet extends HttpServlet {
    randomKey nD = new randomKey();
    private CartService cartService = new CartService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); 
        if (user != null) {
            String keyword = nD.noiDung();
            UUID userID = UUID.fromString(user.getUserID());
            ArrayList<Courses> ca = cartService.findCourseInCart(userID);
            int cartPrice = cartService.getTotalAmount(userID);
            
            session.setAttribute("transactionKeyword", keyword);
            request.setAttribute("userID", userID);
            request.setAttribute("noiDung", keyword);
            request.setAttribute("check", false);
            request.setAttribute("CourseInCart", ca);
            request.setAttribute("price", cartPrice);
            System.out.println("User ID: " + userID);
            System.out.println("Courses in cart: " + ca);
            System.out.println("Cart price: " + cartPrice);

            
            
        request.getRequestDispatcher("QR/jsp/QR.jsp").forward(request, response);    
        }   
        
    }
}