/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerCart;

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
import service.WalletService;

@WebServlet(name = "AfterPayServlet", urlPatterns = {"/AfterPayServlet"})
public class AfterPayServlet extends HttpServlet {
    private CartService cartService = new CartService();
    private WalletService walletServicce = new WalletService();
    
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
        
        if (user != null) {
            UUID userID = UUID.fromString(user.getUserID());
            
            // Lấy danh sách khóa học trong giỏ hàng trước khi xóa
            ArrayList<Courses> coursesInCart = cartService.findCourseInCart(userID);
            
            // Xử lý thanh toán: thêm balance và chuyển khóa học từ cart sang UserCourse
            walletServicce.addBalanceForCourseOwners(userID);
            cartService.moveCoursesFromCartToUserCourse(userID);
            
            // Xác định URL để redirect sau khi đăng ký thành công
            String redirectUrl;
            
            if (coursesInCart != null && !coursesInCart.isEmpty()) {
                // Nếu chỉ có 1 khóa học, redirect về trang chi tiết khóa học đó
                if (coursesInCart.size() == 1) {
                    UUID courseID = coursesInCart.get(0).getCourseID();
                    redirectUrl = request.getContextPath() + "/CourseServletController?action=detail&id=" + courseID;
                } else {
                    // Nếu có nhiều khóa học, redirect về trang danh sách khóa học
                    redirectUrl = request.getContextPath() + "/CourseServletController";
                }
            } else {
                // Nếu không có khóa học nào (trường hợp hiếm), redirect về trang chủ
                redirectUrl = request.getContextPath() + "/CourseServletController";
            }
            
            // Redirect về trang phù hợp
            response.sendRedirect(redirectUrl);
        } else {
            // Nếu chưa đăng nhập, redirect về trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }  
}
