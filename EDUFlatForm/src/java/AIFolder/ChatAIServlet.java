package AIFolder;

import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet(name="ChatAIServlet", urlPatterns={"/api/chat-ai"})
public class ChatAIServlet extends HttpServlet {

    // ❶ Cập nhật URL Cloudflare hiện tại ở đây mỗi khi Colab đổi link
    private static final String COLAB_PUBLIC_URL =
        "https://fighter-comparing-technical-furthermore.trycloudflare.com";

    private final ColabApiClient api = new ColabApiClient(COLAB_PUBLIC_URL);
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = req.getReader()) {
            JsonObject body = gson.fromJson(reader, JsonObject.class);
            String message = body != null && body.has("message")
                    ? body.get("message").getAsString() : "";

            String answer = api.predict(message);

            JsonObject out = new JsonObject();
            out.addProperty("answer", answer);
            resp.getWriter().write(gson.toJson(out));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(502);
            JsonObject err = new JsonObject();
            err.addProperty("error", "Proxy lỗi: " + e.getClass().getSimpleName()
                    + " – " + e.getMessage());
            resp.getWriter().write(gson.toJson(err));
        }
    }

    // ❷ (Tuỳ chọn) Thêm healthcheck GET để test nhanh
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write("{\"ok\":true}");
    }
}
