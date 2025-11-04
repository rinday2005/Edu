/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WalletDAO;

/**
 *
 * @author ADMIN
 */
import java.util.*;
import model.Wallet;

public interface IWalletDAO {

    void create(Wallet w);                 // C

    int update(Wallet w);                 // U

    int deleteById(UUID walletId);        // D

    Wallet findById(UUID walletId);        // R1

    Wallet findByUserId(UUID userId);      // tiện lợi

    List<Wallet> findAll();                // R2

    int updateBalance(UUID walletId, int delta); // cộng/trừ số dư
}
