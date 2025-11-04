
package controllerQR;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import sePay.TransactionChecker;
import service.CartService;

/**
 *
 * @author trank
 */
@WebServlet("/checkTransaction")
public class TransactionCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartDAO = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        String keyword = (String) session.getAttribute("transactionKeyword");
        
        if (keyword == null) {
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Không tìm thấy mã giao dịch\"}");
            return;
        }
        
        try {
            boolean isTransactionFound = TransactionChecker.isTransaction(keyword);
            
            if (isTransactionFound) {
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Giao dịch được tìm thấy\", \"keyword\": \"" + keyword + "\"}");

                
            } else {
                response.getWriter().write("{\"status\": \"pending\", \"message\": \"Chưa tìm thấy giao dịch\", \"keyword\": \"" + keyword + "\"}");
            }
            
        } catch (Exception e) {
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Lỗi khi kiểm tra giao dịch: " + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
