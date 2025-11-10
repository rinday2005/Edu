package util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter để đảm bảo static resources (CSS, JS, images) được phục vụ đúng cách
 * và không bị các servlet khác chặn
 * 
 * Filter được cấu hình trong web.xml để đảm bảo thứ tự thực thi đúng
 */
public class StaticResourceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần khởi tạo gì
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        
        // Nếu là static resource, chuyển tiếp cho default servlet để container phục vụ
        // Không tiếp tục qua filter chain để tránh bị servlet khác chặn
        if (isStaticResource(path)) {
            // Sử dụng RequestDispatcher để chuyển tiếp cho default servlet
            // Default servlet sẽ phục vụ static resources từ webapp root
            try {
                RequestDispatcher dispatcher = httpRequest.getServletContext()
                        .getNamedDispatcher("default");
                if (dispatcher != null) {
                    dispatcher.forward(request, response);
                } else {
                    // Fallback: nếu không có default servlet, sử dụng path-based dispatcher
                    dispatcher = httpRequest.getRequestDispatcher(path);
                    if (dispatcher != null) {
                        dispatcher.forward(request, response);
                    } else {
                        // Cuối cùng, vẫn tiếp tục chain nhưng có thể bị servlet chặn
                        chain.doFilter(request, response);
                    }
                }
            } catch (Exception e) {
                // Nếu có lỗi, vẫn tiếp tục chain
                chain.doFilter(request, response);
            }
            return;
        }
        
        // Các request khác tiếp tục qua filter chain đến servlet
        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String path) {
        return StaticResourceUtil.isStaticResource(path);
    }

    @Override
    public void destroy() {
        // Không cần cleanup
    }
}

