package WalletDAO;

import DAO.DBConnection;
import model.Wallet;
import java.sql.*;
import java.util.*;

public class WalletDAO implements IWalletDAO {

        @Override
        public void create(UUID userID, String bankName, String bankAccount) {
        String sql = "INSERT INTO Wallet (userID, BankName, BankAccount, Balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userID.toString());
            ps.setString(2, bankName);
            ps.setString(3, bankAccount);
            ps.setInt(4, 0);  // Balance luôn = 0
            
            ps.executeUpdate();
            
    }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int update(Wallet w) {
        String sql = "UPDATE Wallet SET userID = ?, BankName = ?, BankAccount = ?, Balance = ? WHERE walletID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, w.getUserID(), java.sql.Types.OTHER);
            ps.setString(2, w.getBankName());
            ps.setString(3, w.getBankAccount());
            ps.setInt(4, w.getBalance());
            ps.setObject(5, w.getWalletID(), java.sql.Types.OTHER);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(UUID walletId) {
        String sql = "DELETE FROM Wallet WHERE walletID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, walletId, java.sql.Types.OTHER);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Wallet findById(UUID walletId) {
        String sql = "SELECT * FROM Wallet WHERE walletID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, walletId, java.sql.Types.OTHER);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

@Override
public Wallet findByUserId(UUID userId) {
    String sql = "SELECT * FROM Wallet WHERE userID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, userId.toString());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Wallet wallet = new Wallet();
            wallet.setBankName(rs.getString("bankName"));
            wallet.setBankAccount(rs.getString("bankAccount"));
            wallet.setBalance(rs.getInt("balance"));
            return wallet; // trả về Wallet chứa dữ liệu
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // nếu không tìm thấy wallet
}



    @Override
    public List<Wallet> findAll() {
        List<Wallet> list = new ArrayList<>();
        String sql = "SELECT * FROM Wallet";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapResultSet(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int updateBalance(UUID walletId, int delta) {
        String sql = "UPDATE Wallet SET Balance = Balance + ? WHERE walletID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, delta);
            ps.setObject(2, walletId, java.sql.Types.OTHER);
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public void addBalanceForCourseOwners(UUID userID) {
    String selectQuery = "SELECT c.courseID, c.userID AS ownerID, c.price " +
                         "FROM Cart ca " +
                         "JOIN Courses c ON ca.courseID = c.courseID " +
                         "WHERE ca.userID = ?";

    String checkWalletQuery = "SELECT COUNT(*) FROM Wallet WHERE userID = ?";
    String insertWalletQuery = "INSERT INTO Wallet (walletID, userID, BankName, BankAccount, Balance) " +
                               "VALUES (NEWID(), ?, NULL, NULL, 0)";
    String updateWalletQuery = "UPDATE Wallet SET Balance = Balance + ? WHERE userID = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement psSelect = con.prepareStatement(selectQuery);
         PreparedStatement psCheck = con.prepareStatement(checkWalletQuery);
         PreparedStatement psInsert = con.prepareStatement(insertWalletQuery);
         PreparedStatement psUpdate = con.prepareStatement(updateWalletQuery)) {

        psSelect.setString(1, userID.toString());
        ResultSet rs = psSelect.executeQuery();

        while (rs.next()) {
            String ownerID = rs.getString("ownerID");
            int price = rs.getInt("price");
            int amountToTransfer = price; // 75% cho chủ course

            // Kiểm tra nếu chủ course chưa có Wallet -> tạo mới
            psCheck.setString(1, ownerID);
            ResultSet rsCheck = psCheck.executeQuery();
            boolean hasWallet = false;
            if (rsCheck.next()) {
                hasWallet = rsCheck.getInt(1) > 0;
            }

            if (!hasWallet) {
                psInsert.setString(1, ownerID);
                psInsert.executeUpdate();
            }

            psUpdate.setInt(1, amountToTransfer);
            psUpdate.setString(2, ownerID);
            psUpdate.executeUpdate();
        }
        rs.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    @Override
    public int getBalanceByUserID(UUID userID) {
        String sql = "SELECT Balance FROM Wallet WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Balance");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // trả về 0 nếu không tìm thấy
    }
    

    @Override
    public boolean withdrawByUserId(UUID userId, int price) {
    if (price < 0) return false; // số tiền không hợp lệ

    String sql = "UPDATE Wallet SET Balance = Balance - ? " +
                 "WHERE userID = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, price);
        ps.setString(1, userId.toString());

        int updated = ps.executeUpdate();
        return updated == 1; // nếu 1 row được update => trừ thành công

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
        public void deductBalance(UUID userId, int price) {
        if (price <= 0) {
            System.out.println("❌ Số tiền không hợp lệ!");
            return;
        }

        String checkBalanceSql = "SELECT Balance FROM Wallet WHERE userID = ?";
        String updateBalanceSql = "UPDATE Wallet SET Balance = Balance - ? WHERE userID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkBalanceSql);
             PreparedStatement updatePs = conn.prepareStatement(updateBalanceSql)) {

            checkPs.setString(1, userId.toString());

            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                int balance = rs.getInt("Balance");

                if (price > balance) {
                    System.out.println("❌ Không đủ số dư!");
                    return;
                }

                updatePs.setInt(1, price);
                updatePs.setString(2, userId.toString());
                updatePs.executeUpdate();

                System.out.println("✅ Đã trừ " + price + " khỏi ví!");
            } else {
                System.out.println("❌ Không tìm thấy userID này!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public boolean existsByUserId(UUID userId) {
    String sql = "SELECT COUNT(*) FROM Wallet WHERE userID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, userId.toString());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



    private Wallet mapResultSet(ResultSet rs) throws SQLException {
        Wallet w = new Wallet();
        w.setWalletID((UUID) rs.getObject("walletID"));
        w.setUserID((UUID) rs.getObject("userID"));
        w.setBankName(rs.getString("BankName"));
        w.setBankAccount(rs.getString("BankAccount"));
        w.setBalance(rs.getInt("Balance"));
        return w;
    }
    
    public static void main(String[] args) {
        WalletDAO dao = new WalletDAO();

        // Tạo một userID mẫu (giả sử đã có user này trong bảng User)
        UUID userID = UUID.fromString("FA7E4C8F-117E-4999-994C-FD82C9BDA333");

        // Gọi hàm create để thêm ví mới cho user
        dao.create(userID, "Vietcombank", "0123456789");

        System.out.println("Đã thêm ví mới cho user " + userID);
    }
}
