/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sePay;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author trank
 */
public class TransactionChecker {

    public static boolean isTransaction(String keyword) {
        boolean isFine = false;

        SepayAPI api = new SepayAPI();

   

       

        
            try {
                JsonArray transactions = api.getTransactions();
                if (transactions != null) {
                    for (JsonElement el : transactions) {
                        JsonObject tran = el.getAsJsonObject();
                        String content = tran.get("transaction_content").getAsString();
                        if (content.contains(keyword)) {
                            isFine = true;
                            return isFine;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        

        return isFine;
    }
}