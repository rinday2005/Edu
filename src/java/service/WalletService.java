// WalletService.java
package service;
import model.Wallet;
import DAO.*;
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
    
    public static void main(String[] args) {
        try {
            WalletService walletService = new WalletService();

            // UUID của người mua (thay bằng ID có thật trong DB)
            UUID buyerID = UUID.fromString("EAB8A44A-FBB9-43BE-9C43-B202F9FDE5B0");

            // Gọi xử lý chuyển tiền cho chủ khóa học
            walletService.addBalanceForCourseOwners(buyerID);

            System.out.println("✅ Đã xử lý chuyển tiền cho các chủ khóa học của user: " + buyerID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}
