package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import service.UserServiceImpl;
import util.AvatarUploadUtil;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
@WebServlet(name = "LearnerControllerServlet", urlPatterns = {"/learner"})
public class LearnerControllerServlet extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>LearnerControllerServlet active</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }

        switch (action.toLowerCase()) {
            case "setting":
                showSettingPage(request, response);
                break;
            default:
                showLearnerHome(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }

        switch (action.toLowerCase()) {
            case "editinfo":
                try {
                    handleUpdateLearnerPro(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(LearnerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "changepassword":
                try {
                    handleChangePassword(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(LearnerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            default:
response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported action: " + action);
                break;
        }
    }

    private void handleUpdateLearnerPro(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            // ➤ Nếu chưa đăng nhập → quay về trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }

        String fullName = param(request, "fullName");
        String email = param(request, "email");
        String phone = param(request, "phone");
        String bio = param(request, "bio");
        String birthday = param(request, "birthday");

        sessionUser.setFullName(fullName);
        sessionUser.setEmail(email);
        sessionUser.setPhoneNumber(phone);
        sessionUser.setBio(bio);

        if (birthday != null && !birthday.isEmpty()) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                Date parsedDate = sdf.parse(birthday);
                sessionUser.setDateofbirth(parsedDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // --- Upload avatar nếu có ---
        Part avatarPart = request.getPart("avatar");
        String avatarUrl = AvatarUploadUtil.uploadFile(
                avatarPart,
                request.getServletContext(),
                request.getContextPath(),
                "uploads/avatar/learner"
        );
        if (avatarUrl != null) {
            sessionUser.setAvatarUrl(avatarUrl);
        }

        // --- Cập nhật thông tin trong DB ---
        boolean updated = false;
        try {
            updated = userService.updateProfile(sessionUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (updated) {
            session.setAttribute("user", sessionUser);
            request.setAttribute("message", "✅ Cập nhật thông tin thành công!");
        } else {
            request.setAttribute("error", "❌ Cập nhật thất bại, vui lòng thử lại!");
        }

        // ➤ Quay lại trang setting.jsp - kiểm tra từ trang nào submit
        String referer = request.getHeader("Referer");
        String settingPage = "/learner/jsp/Setting/setting.jsp";
        
        if (referer != null && referer.contains("/instructor/jsp/Setting.jsp")) {
            settingPage = "/instructor/jsp/Setting.jsp";
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(settingPage);
        rd.forward(request, response);
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response)
throws IOException, ServletException, Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }

        String currentPassword = param(request, "currentPassword");
        String newPassword = param(request, "newPassword");
        String confirmPassword = param(request, "confirmPassword");

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            forwardSetting(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận không khớp!");
            forwardSetting(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự!");
            forwardSetting(request, response);
            return;
        }

        if (!currentPassword.equals(sessionUser.getPassword())) {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng!");
            forwardSetting(request, response);
            return;
        }

        boolean updated = userService.updatePassword(sessionUser.getEmail(), newPassword);

        if (updated) {
            sessionUser.setPassword(newPassword);
            session.setAttribute("user", sessionUser);
            request.setAttribute("message", "✅ Đổi mật khẩu thành công!");
        } else {
            request.setAttribute("error", "❌ Đổi mật khẩu thất bại, vui lòng thử lại!");
        }

        forwardSetting(request, response);
    }

    private void forwardSetting(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra từ trang nào submit để forward về đúng trang
        String referer = request.getHeader("Referer");
        String settingPage = "/learner/jsp/Setting/setting.jsp";
        
        if (referer != null && referer.contains("/instructor/jsp/Setting.jsp")) {
            settingPage = "/instructor/jsp/Setting.jsp";
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(settingPage);
        rd.forward(request, response);
    }

    private String param(HttpServletRequest request, String name) {
        String v = request.getParameter(name);
        return v != null ? v.trim() : "";
    }

    private void showSettingPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/learner/jsp/Setting/setting.jsp");
rd.forward(request, response);
    }

    private void showLearnerHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ➤ Nếu bạn có trang learner chính, đổi đường dẫn tại đây
        response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Learner Controller Servlet";
    }
}
