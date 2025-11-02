/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.UUID;


/**
 *
 * @author ADMIN
 */

public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private UUID walletID;
 
    private String bankName;
    
    private String bankAccount;
    
    private Integer balance;
    
    private UUID userID;

    public Wallet() {
    }

    public Wallet(UUID walletID, String bankName, String bankAccount, Integer balance, UUID userID) {
        this.walletID = walletID;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.balance = balance;
        this.userID = userID;
    }

    public UUID getWalletID() {
        return walletID;
    }

    public void setWalletID(UUID walletID) {
        this.walletID = walletID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    
}
