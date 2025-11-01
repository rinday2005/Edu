/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerforAuthen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import service.UserServiceImpl;

@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/google-login"})
public class GoogleLoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GoogleLoginServlet.class.getName());
    private static final String GOOGLE_TOKEN_VERIFY_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";
    private static final String GOOGLE_ISSUER = "https://accounts.google.com";
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    private UserServiceImpl userService = new UserServiceImpl();

 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String credential = request.getParameter("credential");

        if (credential == null || credential.trim().isEmpty()) {
            LOGGER.warning("Credential is null or empty");
            sendErrorResponse(response, "Missing credential", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            String clientId = "241470665821-3vbsh1pbsp17rd1vb6dctv2qlh5c721g.apps.googleusercontent.com";

            JSONObject payload = verifyTokenWithGoogle(credential);
            if (payload == null || !isTokenValid(payload, clientId)) {
                sendErrorResponse(response, "Invalid or expired token", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 🔹 Lấy thông tin người dùng từ Google payload
            String email = payload.optString("email", null);
            String name = payload.optString("name", "Người dùng Google");
            String googleId = payload.optString("sub", UUID.randomUUID().toString());

            if (email == null || email.isEmpty()) {
                sendErrorResponse(response, "Google account missing email", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            LOGGER.log(Level.INFO, "✅ Google login success for user: {0}", email);

            // ===================== TÁC VỤ DATABASE =====================
            User user = userService.getUserByEmail(email);

            if (user == null) {
                // 🔸 Nếu user chưa tồn tại -> tạo mới
                user = new User();
                user.setUserID(UUID.randomUUID().toString());
                user.setFullName(name);
                user.setUserName(email.split("@")[0]); // Lấy phần trước @ làm username
                user.setEmail(email);

                user.setLoginProvider("Google");
                user.setProviderKey(googleId);
                user.setStatus(true);
                user.setRole("Learner");
                user.setEnrollmentCount(0);
                user.setPassword("GOOGLE_USER_" + UUID.randomUUID()); // 🔸 tránh lỗi NOT NULL

                boolean created = userService.createUser(user);
                if (created) {
                    LOGGER.log(Level.INFO, "🆕 Created new Google user in DB: {0}", email);
                } else {
                    LOGGER.log(Level.SEVERE, "❌ Failed to create Google user in DB: {0}", email);
                    sendErrorResponse(response, "Failed to save user to database", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }

            } else {
                // 🔸 Nếu user đã tồn tại -> cập nhật avatar, fullname, provider
                user.setFullName(name);
                user.setLoginProvider("Google");
                user.setProviderKey(googleId);
                userService.updateGoogleUser(user);
                LOGGER.log(Level.INFO, "Existing Google user logged in: {0}", email);
            }

            // ===================== TẠO SESSION =====================
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60); // 30 phút

            // Redirect: ưu tiên tham số redirect, mặc định về /course
            String redirect = request.getParameter("redirect");
            if (redirect == null || redirect.isBlank()) {
                redirect = request.getContextPath() + "/course";
            }
            response.sendRedirect(response.encodeRedirectURL(redirect));
        } catch (JSONException e) {
            LOGGER.log(Level.SEVERE, "JSON parsing error: " + e.getMessage());
            sendErrorResponse(response, "Invalid Google response format", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during Google login: " + e.getMessage(), e);
            sendErrorResponse(response, "Server error during login", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // ======================= VERIFY TOKEN =======================
    private JSONObject verifyTokenWithGoogle(String credential) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(GOOGLE_TOKEN_VERIFY_URL + credential);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);

            if (conn.getResponseCode() != 200) {
                LOGGER.warning("Google API returned status: " + conn.getResponseCode());
                return null;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                return new JSONObject(response.toString());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error verifying token: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    // ======================= VALIDATE TOKEN =======================
    private boolean isTokenValid(JSONObject payload, String clientId) {
        try {
            return payload.getString("aud").equals(clientId)
                    && payload.getString("iss").equals(GOOGLE_ISSUER)
                    && payload.getBoolean("email_verified")
                    && (System.currentTimeMillis() / 1000) <= payload.getLong("exp");
        } catch (JSONException e) {
            LOGGER.warning("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    // ======================= SEND ERROR =======================
    private void sendErrorResponse(HttpServletResponse response, String message, int statusCode)
            throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        JSONObject errorJson = new JSONObject();
        errorJson.put("error", message);
        response.getWriter().write(errorJson.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not supported");
    }

    @Override
    public String getServletInfo() {
        return "Google Login Servlet - Handles OAuth and saves user to DB";
    }
}
