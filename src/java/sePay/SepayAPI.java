/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sePay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author trank
 */
public class SepayAPI {

    private static final String API_URL = "https://my.sepay.vn/userapi/transactions/list?account_number=0911245428&limit=20";
    private static final String AUTH_TOKEN = "YEKVHDWRV1IYBT4CGUSRLKSGXFHAQJXHIOG5JLIMKAJVO0NWNM2CTLQPWX3UPFQW";

    public JsonArray getTransactions() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);

        int responseCode = conn.getResponseCode();
        if (responseCode == 429) {
            System.out.println("⚠️ API bị giới hạn tần suất, đợi một chút rồi thử lại...");
            return null;
        }

        if (responseCode != 200) {
            System.out.println("⚠️ Lỗi khi gọi API: " + responseCode);
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        return jsonResponse.getAsJsonArray("transactions");
    }
}
