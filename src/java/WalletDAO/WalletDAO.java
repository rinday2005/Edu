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

    private Wallet mapResultSet(ResultSet rs) throws SQLException {
        Wallet w = new Wallet();
        w.setWalletID((UUID) rs.getObject("walletID"));
        w.setUserID((UUID) rs.getObject("userID"));
        w.setBankName(rs.getString("BankName"));
        w.setBankAccount(rs.getString("BankAccount"));
        w.setBalance(rs.getInt("Balance"));
        return w;
    }
}
