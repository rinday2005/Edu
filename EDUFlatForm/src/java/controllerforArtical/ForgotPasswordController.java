/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerforArtical;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;
import model.User;
import service.UserServiceImpl;
import util.EmailUtil;

@WebServlet(name = "ForgotPasswordController", urlPatterns = {"/forgot"})
public class ForgotPasswordController extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "sendOtp";

        switch (action) {
            case "sendOtp":
                handleSendOtp(request, response);
                break;
            case "verifyOtp":
                handleVerifyOtp(request, response);
                break;
            case "resetPassword":
                handleResetPassword(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    // ====================== 🔹 1. Gửi OTP ======================
    private void handleSendOtp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email") != null ? request.getParameter("email").trim() : "";

        if (email == null || email.isBlank()) {
            request.setAttribute("error", "Email không được để trống.");
            request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            return;
        }

        User user = userService.getUserByEmail(email);
        if (user == null) {
            request.setAttribute("error", "Không tìm thấy tài khoản với email này.");
            request.getRequestDispatcher("/authen/forgotPassWord.jsp").forward(request, response);
            return;
        }

        // Tạo mã OTP 6 số
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Lưu OTP vào session (hết hạn sau 5 phút)
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);
        session.setMaxInactiveInterval(5 * 60);

        // Gửi OTP qua email
        String subject = "Mã OTP khôi phục mật khẩu";
        String content = "Xin chào " + user.getUserName() + ",\n\nMã OTP của bạn là: " + otp
                + "\nMã này có hiệu lực trong 5 phút.\n\nTrân trọng,\nĐội ngũ E-Learning System.";
        
        boolean sent = EmailUtil.sendEmail(email, subject, content);
        System.out.println(sent);
        if (sent) {
            request.setAttribute("message", "OTP đã được gửi đến email của bạn.");
            request.getRequestDispatcher("/authen/verifyOtp.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Không thể gửi email. Vui lòng thử lại.");
            request.getRequestDispatcher("/authen/forgotPassWord.jsp").forward(request, response);
        }
    }

    // ====================== 🔹 2. Xác minh OTP ======================
    private void handleVerifyOtp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("otp") == null) {
            request.setAttribute("error", "OTP đã hết hạn hoặc không tồn tại.");
            request.getRequestDispatcher("/forgotPassWord.jsp").forward(request, response);
            return;
        }

        String otpInput = request.getParameter("otp");
        String otpSaved = (String) session.getAttribute("otp");

        if (otpInput != null && otpInput.equals(otpSaved)) {
            request.setAttribute("message", "OTP hợp lệ, vui lòng nhập mật khẩu mới.");
            request.getRequestDispatcher("/authen/resetPassWord.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Mã OTP không đúng, vui lòng thử lại.");
            request.getRequestDispatcher("/authen/verifyOtp.jsp").forward(request, response);
        }
    }

    // ====================== 🔹 3. Đặt lại mật khẩu ======================
    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            request.setAttribute("error", "Phiên làm việc đã hết hạn.");
            request.getRequestDispatcher("/authen/forgotPassWord.jsp").forward(request, response);
            return;
        }

        String email = (String) session.getAttribute("email");
        String newPassword = request.getParameter("newPassword");

        if (newPassword == null || newPassword.length() < 6) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("/authen/resetPassword.jsp").forward(request, response);
            return;
        }

        boolean updated = userService.updatePassword(email, newPassword);
        if (updated) {
            session.invalidate();
            request.setAttribute("message", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập lại.");
            request.getRequestDispatcher("/authen/loginAuthen.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Cập nhật mật khẩu thất bại.");
            request.getRequestDispatcher("/authen/resetPassword.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Forgot Password Controller - Handles OTP and password reset actions.";
    }
}
 
