/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ADMIN
 */

import CartDAO.CartDAO;
import java.util.ArrayList;
import java.util.UUID;
import model.Courses;

public class CartService {
        CartDAO CD = new CartDAO();
        
    public void addToCart(UUID userID, UUID courseID){
        CD.addToCart(userID, courseID);
    };
    public void deleteCourse(UUID userID){
        CD.deleteCourse(userID);
    };
    public void deleteCourseByID(UUID courseID){
        CD.deleteCourseByID(courseID);
    };
    public int countItemCart(UUID userID){
        return CD.countItemCart(userID);
    };
    public boolean isCourseExist(UUID userID, UUID courseID){
        return CD.isCourseExist(userID, courseID);
    };
    
    public ArrayList<Courses> findCourseInCart(UUID userID){
        return CD.findCourseInCart(userID);
    }
    public int getTotalAmount(UUID userID){
        return CD.getTotalAmount(userID);
    }
}