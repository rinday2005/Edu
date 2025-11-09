// WalletService.java
package service;
import model.Wallet;
import WalletDAO.WalletDAO;
import java.util.UUID;
public class WalletService{
    WalletDAO WD = new WalletDAO();
    public void addBalanceForCourseOwners(UUID userID){
        WD.addBalanceForCourseOwners(userID);
    }
    public int getBalanceByUserID(UUID userID){
        return WD.getBalanceByUserID(userID);
    }
    public Wallet findById(UUID walletId){
        return WD.findByUserId(walletId);
    }
    public boolean withdrawByUserId(UUID userId, int price){
        return WD.withdrawByUserId(userId, price);
    }
    public void deductBalance(UUID userId, int price){
        WD.deductBalance(userId, price);
    } 
    
    public boolean existsByUserId(UUID userId){
        return WD.existsByUserId(userId);
    }
    public void create(UUID userID, String bankName, String bankAccount){
        WD.create(userID, bankName, bankAccount);
    }
    
        public static void main(String[] args) {
        WalletDAO dao = new WalletDAO();
        UUID testUserId = UUID.fromString("33C57260-0D72-4EB5-B1E3-4FF6C7EE4016");
        Wallet wallet = dao.findByUserId(testUserId);

        if (wallet != null) {
            System.out.println("Found wallet for user: " + testUserId);
            System.out.println("Bank Name: " + wallet.getBankName());
            System.out.println("Bank Account: " + wallet.getBankAccount());
            System.out.println("Balance: " + wallet.getBalance());
        } else {
            System.out.println("No wallet found for user: " + testUserId);
        }
    }
    
    
    
}
