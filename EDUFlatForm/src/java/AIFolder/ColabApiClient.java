package AIFolder;

import java.net.http.*;
import java.net.URI;
import java.time.Duration;
import com.google.gson.*;

public class ColabApiClient {
    private final HttpClient client;
    private final String baseUrl;
    private final Gson gson = new Gson();

    public ColabApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))   // ❸ tăng timeout
                .build();
    }

    public String predict(String text) throws Exception {
        JsonObject payload = new JsonObject();
        payload.addProperty("text", text);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/predict"))
                .header("Content-Type", "application/json")
                .header("User-Agent", "EDUChat/1.0")      // ❹ UA an toàn
                .timeout(Duration.ofSeconds(120))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(payload)))
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) {
            throw new RuntimeException("Colab API lỗi: HTTP " + res.statusCode() + " – " + res.body());
        }
        JsonObject obj = JsonParser.parseString(res.body()).getAsJsonObject();
        return obj.get("answer").getAsString();
    }
}
