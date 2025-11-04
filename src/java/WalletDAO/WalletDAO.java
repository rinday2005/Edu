package WalletDAO;

import DAO.DBConnection;
import model.Wallet;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class WalletDAO implements IWalletDAO {

    @Override
    public void create(Wallet w) {
        String sql = "INSERT INTO Wallet (walletID, userID, BankName, BankAccount, Balance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            UUID id = w.getWalletID() != null ? w.getWalletID() : UUID.randomUUID();
            ps.setObject(1, id, java.sql.Types.OTHER);
            ps.setObject(2, w.getUserID(), java.sql.Types.OTHER);
            ps.setString(3, w.getBankName());
            ps.setString(4, w.getBankAccount());
            ps.setInt(5, w.getBalance());

            ps.executeUpdate();
        } catch (SQLException e) {
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

            ps.setObject(1, userId, java.sql.Types.OTHER);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        try {
            // Tạo DAO
            WalletDAO walletDAO = new WalletDAO();

            // UUID của user (người mua khóa học) — đổi sang ID thật trong DB nha
            UUID buyerID = UUID.fromString("EAB8A44A-FBB9-43BE-9C43-B202F9FDE5B0");

            // Gọi hàm để chuyển tiền cho các chủ khóa học
            walletDAO.addBalanceForCourseOwners(buyerID);

            System.out.println("✅ Đã xử lý chuyển tiền cho các chủ khóa học của user: " + buyerID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
