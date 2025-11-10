package util;

/**
 * Utility class để kiểm tra xem một request có phải là static resource không
 */
public class StaticResourceUtil {
    
    /**
     * Kiểm tra xem request path có phải là static resource không
     * @param path Request URI path
     * @return true nếu là static resource (CSS, JS, images, fonts, etc.)
     */
    public static boolean isStaticResource(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }
        
        String lowerPath = path.toLowerCase();
        
        // Kiểm tra extension
        if (lowerPath.endsWith(".css") || lowerPath.endsWith(".js") || 
            lowerPath.endsWith(".jpg") || lowerPath.endsWith(".jpeg") || 
            lowerPath.endsWith(".png") || lowerPath.endsWith(".gif") || 
            lowerPath.endsWith(".svg") || lowerPath.endsWith(".ico") ||
            lowerPath.endsWith(".woff") || lowerPath.endsWith(".woff2") || 
            lowerPath.endsWith(".ttf") || lowerPath.endsWith(".eot") ||
            lowerPath.endsWith(".webp") || lowerPath.endsWith(".mp4") ||
            lowerPath.endsWith(".pdf") || lowerPath.endsWith(".zip") ||
            lowerPath.endsWith(".json") || lowerPath.endsWith(".xml")) {
            return true;
        }
        
        // Kiểm tra đường dẫn chứa thư mục static
        if (lowerPath.contains("/css/") || lowerPath.contains("/js/") || 
            lowerPath.contains("/images/") || lowerPath.contains("/fonts/") ||
            lowerPath.contains("/assets/") || lowerPath.contains("/uploads/") ||
            lowerPath.contains("/static/") || lowerPath.contains("/resources/")) {
            return true;
        }
        
        return false;
    }
}

