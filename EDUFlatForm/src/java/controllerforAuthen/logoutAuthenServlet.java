/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerforAuthen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "logoutAuthen", urlPatterns = {"/logout"})
public class logoutAuthenServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet logoutAuthen</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet logoutAuthen at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔹 Xóa session đăng nhập hiện tại
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            System.out.println("✅ Session invalidated successfully.");
        }

        // 🔹 Xóa cookie "username" (nếu có)
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    System.out.println("✅ Cookie 'username' deleted.");
                }
            }
        }

        // 🔹 Xóa luôn JSESSIONID để tránh session cũ tái sử dụng
        Cookie jsession = new Cookie("JSESSIONID", "");
        jsession.setMaxAge(0);
        jsession.setPath(request.getContextPath());
        response.addCookie(jsession);

        // 🔹 Chuyển hướng về trang đăng nhập
        response.sendRedirect(request.getContextPath() + "/course");
    }

    @Override
    public String getServletInfo() {
        return "Servlet dùng để đăng xuất và xóa session, cookie.";
    }

}
