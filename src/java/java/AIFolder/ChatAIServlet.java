/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIFolder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ChatAIServlet", urlPatterns = {"/ai/chat"})
public class ChatAIServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Check if user is logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try (PrintWriter out = response.getWriter()) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("error", "Bạn cần đăng nhập để sử dụng tính năng này");
                out.print(jsonResponse.toString());
            }
            return;
        }
        
        try (PrintWriter out = response.getWriter()) {
            // Read request body
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                requestBody.append(line);
            }
            
            // Parse JSON request
            JSONObject jsonRequest = new JSONObject(requestBody.toString());
            String message = jsonRequest.optString("message", "");
            
            // Generate response (Replace with actual AI service call)
            String aiResponse = generateAIResponse(message);
            
            // Create JSON response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("response", aiResponse);
            jsonResponse.put("status", "success");
            
            out.print(jsonResponse.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("error", "Có lỗi xảy ra khi xử lý yêu cầu: " + e.getMessage());
                out.print(jsonResponse.toString());
            }
        }
    }

    /**
     * Generate AI response (Mock implementation - Replace with actual AI service)
     */
    private String generateAIResponse(String userMessage) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Xin chào! Bạn có thể hỏi tôi về khóa học, bài viết, hoặc các khái niệm lập trình.";
        }
        
        String lowerMessage = userMessage.toLowerCase();
        
        // Simple keyword matching for demo (Replace with actual AI service)
        if (lowerMessage.contains("khóa học") || lowerMessage.contains("course")) {
            return "Bạn có thể tìm kiếm khóa học trong mục 'Khóa học Pro' trên trang chủ. "
                    + "Hệ thống có nhiều khóa học từ cơ bản đến nâng cao về lập trình web, "
                    + "mobile, và các công nghệ khác. Bạn muốn tìm hiểu về khóa học nào cụ thể không?";
        } else if (lowerMessage.contains("bài viết") || lowerMessage.contains("article")) {
            return "Bạn có thể xem các bài viết nổi bật trong mục 'Bài viết nổi bật'. "
                    + "Các bài viết bao gồm hướng dẫn, kinh nghiệm học tập, và các kiến thức lập trình. "
                    + "Bạn có câu hỏi gì về bài viết không?";
        } else if (lowerMessage.contains("html") || lowerMessage.contains("css") || lowerMessage.contains("javascript")) {
            return "HTML, CSS và JavaScript là những công nghệ cơ bản cho lập trình web frontend. "
                    + "Bạn có thể học các khóa học về HTML CSS Pro hoặc JavaScript trên hệ thống. "
                    + "Bạn muốn tìm hiểu về phần nào cụ thể?";
        } else if (lowerMessage.contains("giúp") || lowerMessage.contains("help") || lowerMessage.contains("hỗ trợ")) {
            return "Tôi có thể giúp bạn:\n"
                    + "• Tìm hiểu về các khóa học có trên hệ thống\n"
                    + "• Giải thích các khái niệm lập trình\n"
                    + "• Hỗ trợ học tập và làm bài tập\n"
                    + "• Gợi ý tài liệu học tập\n\n"
                    + "Bạn muốn hỏi gì cụ thể?";
        } else if (lowerMessage.contains("cảm ơn") || lowerMessage.contains("thanks") || lowerMessage.contains("thank")) {
            return "Không có gì! Tôi rất vui được giúp đỡ bạn. Nếu có thêm câu hỏi nào, đừng ngần ngại hỏi nhé!";
        } else if (lowerMessage.contains("chào") || lowerMessage.contains("hello") || lowerMessage.contains("hi")) {
            return "Xin chào! Tôi là trợ lý AI của E-Learning System. "
                    + "Tôi có thể giúp bạn tìm hiểu về khóa học, bài viết, hoặc giải đáp các câu hỏi về lập trình. "
                    + "Bạn muốn hỏi gì hôm nay?";
        } else {
            // Default response
            return "Cảm ơn bạn đã hỏi! Hiện tại tôi đang học hỏi để trả lời tốt hơn. "
                    + "Bạn có thể thử hỏi về:\n"
                    + "• Khóa học trên hệ thống\n"
                    + "• Bài viết nổi bật\n"
                    + "• Các khái niệm lập trình (HTML, CSS, JavaScript, React, v.v.)\n"
                    + "• Hỗ trợ học tập\n\n"
                    + "Bạn muốn tìm hiểu về điều gì?";
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Chat AI Servlet";
    }// </editor-fold>

}
