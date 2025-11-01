/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

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
import service.CartService;

/**
 *
 * @author trank
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {
    private CartService cartService = new CartService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        User user = (User) session.getAttribute("user"); 
        if (user != null) {
        UUID userID = UUID.fromString(user.getUserID()); 
        
        ArrayList<Courses> ca = cartService.findCourseInCart(userID);
        int cart = cartService.countItemCart(userID);
        int cartPrice = cartService.getTotalAmount(userID);
        
        request.setAttribute("CourseInCart", ca);
        request.setAttribute( "NumberInCart", cart);
        request.setAttribute("cartPrice", cartPrice);
        
        request.getRequestDispatcher("/cart/card.jsp").forward(request, response);
        
        }
        request.setAttribute("errorMessage", "kotimthayUserID!");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

    }    
}