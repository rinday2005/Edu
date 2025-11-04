/* ===== UserDAO.java ===== */
package UserDAO;

import DAO.DBConnection;

// Chỉ import những gì cần, tránh java.sql.* gây ambiguous Date
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.User;

public class UserDAO implements IUserDAO {

    private static final String LOGIN_SQL =
        "SELECT * FROM [dbo].[User] WHERE (userName = ? OR email = ?) AND password = ? AND status = 1";

    private static final String GET_BY_ID_SQL =
        "SELECT * FROM [dbo].[User] WHERE userID = ?";

    private static final String INSERT_SQL =
        "INSERT INTO [dbo].[User] (userID, fullName, password, email, phoneNumber, avatarUrl, bio, dateofbirth, status, role, createAt, lastModifiedAt, lastModifiedID, token, RefreshToken, LoginProvider, ProviderKey, EnrollmentCount) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_PROFILE_SQL =
        "UPDATE [dbo].[User] "
      + "SET fullName=?, email=?, phoneNumber=?, avatarUrl=?, bio=?, dateofbirth=?, lastModifiedAt=?, lastModifiedID=? "
      + "WHERE userID=?";

    private static final String UPDATE_PASSWORD_SQL =
        "UPDATE [dbo].[User] "
      + "SET password = ?, lastModifiedAt = ?, lastModifiedID = ? "
      + "WHERE userID = ?";

    private static final String SOFT_DELETE_SQL =
        "UPDATE [dbo].[User] "
      + "SET status = 0, lastModifiedAt = ?, lastModifiedID = ? "
      + "WHERE userID = ?";

    private static final String GET_ALL_SQL =
        "SELECT * FROM [dbo].[User]";

    // ========================= MAP RESULT =========================
    private User mapResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUserID(rs.getString("userID"));
        user.setFullName(rs.getString("fullName"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phoneNumber"));
        user.setAvatarUrl(rs.getString("avatarUrl"));
        user.setBio(rs.getString("bio"));
        Timestamp dob = rs.getTimestamp("dateofbirth");
        if (dob != null) user.setDateofbirth(dob);

        user.setStatus(rs.getBoolean("status"));
        user.setRole(rs.getString("role"));
        user.setCreateAt(rs.getDate("createAt"));
        user.setLastModifiedAt(rs.getDate("lastModifiedAt"));
        user.setLastModifiedID(rs.getString("lastModifiedID"));
        user.setToken(rs.getString("token"));
        user.setRefreshToken(rs.getString("RefreshToken"));
        user.setLoginProvider(rs.getString("LoginProvider"));
        user.setProviderKey(rs.getString("ProviderKey"));
        user.setEnrollmentCount(rs.getInt("EnrollmentCount"));

        return user;
    }

    // ========================= LOGIN =========================
    @Override
    public User checkLogin(String usernameOrEmail, String password) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_SQL)) {

            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);
            ps.setString(3, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========================= GET BY ID =========================
    @Override
    public User getUserById(String userID) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID_SQL)) {
            ps.setString(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========================= CREATE USER =========================
    @Override
public boolean createUser(User user) {
    // Gán giá trị mặc định an toàn cho các trường có thể null
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
        // Nếu là user Google, có thể set password rỗng hoặc 1 chuỗi định danh
        user.setPassword("GOOGLE_USER_" + UUID.randomUUID());
    }
    if (user.getUserName() == null || user.getUserName().isEmpty()) {
        // Nếu userName trống thì đặt bằng email
        user.setUserName(user.getEmail() != null ? user.getEmail() : "google_user");
    }
    if (user.getRole() == null || user.getRole().isEmpty()) {
        user.setRole("Learner");
    }
    if (user.getCreateAt() == null) {
        user.setCreateAt(new java.util.Date());
    }
    if (user.getLastModifiedAt() == null) {
        user.setLastModifiedAt(new java.util.Date());
    }

    String sql =
        "INSERT INTO [dbo].[User] " +
        "(userID, userName, fullName, password, email, phoneNumber, avatarUrl, bio, dateofbirth, status, role, createAt, lastModifiedAt, lastModifiedID, token, RefreshToken, LoginProvider, ProviderKey, EnrollmentCount) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, user.getUserID());
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getFullName());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getPhoneNumber());
        ps.setString(7, user.getAvatarUrl());
        ps.setString(8, user.getBio());
        ps.setTimestamp(9, user.getDateofbirth() != null ? new Timestamp(user.getDateofbirth().getTime()) : null);
        ps.setBoolean(10, user.getStatus());
        ps.setString(11, user.getRole());
        ps.setDate(12, new Date(user.getCreateAt().getTime()));
        ps.setDate(13, new Date(user.getLastModifiedAt().getTime()));
        ps.setString(14, user.getLastModifiedID());
        ps.setString(15, user.getToken());
        ps.setString(16, user.getRefreshToken());
        ps.setString(17, user.getLoginProvider());
        ps.setString(18, user.getProviderKey());
        ps.setInt(19, user.getEnrollmentCount());

        int rows = ps.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        System.err.println("❌ SQL Error while creating user: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    // ========================= UPDATE =========================
    @Override
    public boolean updateProfile(User user) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_PROFILE_SQL)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getAvatarUrl());
            ps.setString(5, user.getBio());
            ps.setTimestamp(6, user.getDateofbirth() != null 
                ? new Timestamp(user.getDateofbirth().getTime()) 
                : null);
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis())); // lastModifiedAt
            ps.setString(8, user.getLastModifiedID() != null 
                ? user.getLastModifiedID() 
                : user.getUserID()); // fallback
            ps.setString(9, user.getUserID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean softDeleteUser(String userID, String lastModifiedID) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SOFT_DELETE_SQL)) {

            ps.setDate(1, new Date(System.currentTimeMillis()));
            ps.setString(2, lastModifiedID);
            ps.setString(3, userID);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM [dbo].[User] WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Cập nhật avatar hoặc provider cho user Google (nếu đã tồn tại)
    @Override
    public boolean updateGoogleUser(User user) {
        String sql = "UPDATE [dbo].[User] SET userName = ?, avatarUrl = ?, LoginProvider = ?, ProviderKey = ? WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getAvatarUrl());
            ps.setString(3, user.getLoginProvider());
            ps.setString(4, user.getProviderKey());
            ps.setString(5, user.getEmail());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE [User] SET [password] = ? WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}