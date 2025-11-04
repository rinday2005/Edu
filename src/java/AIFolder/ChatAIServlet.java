package AIFolder;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import service.SimpleApiClientService;

@WebServlet(name="PredictServlet", urlPatterns={"/predict"})
public class ChatAIServlet extends HttpServlet {
    private SimpleApiClientService api;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        String base = getServletContext().getInitParameter("CLOUDFLARE_API_BASE");
        api = new SimpleApiClientService(base);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String q = req.getParameter("q");

        // API FastAPI của bạn nhận {"text": "..."}
        String body = "{\"text\":" + json(q) + "}";

        try {
            String resultJson = api.postJson("/predict", body);
            // Gán nguyên JSON trả về cho JSP (ví dụ: {"answer":"..."} )
            req.setAttribute("ok", true);
            req.setAttribute("rawJson", resultJson);
            req.setAttribute("answer", extractAnswer(resultJson)); // cố gắng tách "answer"
        } catch (Exception e) {
            req.setAttribute("ok", false);
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/learner/jsp/Home/home.jsp").forward(req, resp);
    }

    // escape JSON đơn giản
    private static String json(String s){
        if (s == null) return "null";
        return "\"" + s.replace("\\","\\\\").replace("\"","\\\"")
                       .replace("\n","\\n").replace("\r","") + "\"";
    }

    // Tách trường "answer" (đủ dùng nếu JSON đúng dạng {"answer":"..."}).
    private static String extractAnswer(String json){
        if (json == null) return null;
        int i = json.indexOf("\"answer\"");
        if (i < 0) return null;
        int colon = json.indexOf(':', i);
        if (colon < 0) return null;
        // cắt phần sau dấu :
        String tail = json.substring(colon + 1).trim();
        // nếu là chuỗi bắt đầu bằng "
        if (tail.startsWith("\"")) {
            StringBuilder sb = new StringBuilder();
            boolean esc = false;
            for (int k = 1; k < tail.length(); k++){
                char ch = tail.charAt(k);
                if (esc) { sb.append(ch); esc = false; continue; }
                if (ch == '\\') { esc = true; continue; }
                if (ch == '"') break;
                sb.append(ch);
            }
            return sb.toString();
        }
        // fallback: trả raw
        return json;
    }
}