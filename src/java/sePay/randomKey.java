/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sePay;

/**
 *
 * @author trank
 */
import java.util.Random;
public class randomKey {
    public String noiDung(){
        Random rand = new Random();
        int number = rand.nextInt(999999999 - 111111111 + 1) + 111111111;
        String keyword = Integer.toString(number);
        return keyword;
    }
}