package CourseDAO;

import DAO.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.UUID;
import model.Courses;

public class CourseDAO implements ICourseDAO {

    private static final String TABLE = "dbo.Courses";

    private static final String INSERT_SQL =
        "INSERT INTO " + TABLE + " (courseID, userID, name, description, imgURL, rating, price, level, isApproved) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
        "SELECT courseID, userID, name, description, imgURL, rating, price, level, isApproved " +
        "FROM " + TABLE + " WHERE courseID = ?";

    private static final String SELECT_ALL_SQL =
        "SELECT courseID, userID, name, description, imgURL, rating, price, level, isApproved " +
        "FROM " + TABLE + " ORDER BY name";

    private static final String SELECT_BY_USER_SQL =
        "SELECT courseID, userID, name, description, imgURL, rating, price, level, isApproved " +
        "FROM " + TABLE + " WHERE userID = ? ORDER BY name";

    private static final String UPDATE_SQL =
        "UPDATE " + TABLE + " SET userID=?, name=?, description=?, imgURL=?, rating=?, price=?, level=?, isApproved=? " +
        "WHERE courseID=?";

    private static final String UPDATE_ISAPPROVED_SQL =
        "UPDATE " + TABLE + " SET isApproved=? WHERE courseID=?";

    private static final String DELETE_SQL =
        "DELETE FROM " + TABLE + " WHERE courseID=?";

    private static final String EXISTS_SQL =
        "SELECT 1 FROM " + TABLE + " WHERE courseID=?";

    // ===== Helpers UUID (ghi/đọc dạng String) =====
    private void setNullableUuid(PreparedStatement ps, int idx, UUID v) throws SQLException {
        if (v == null) ps.setNull(idx, Types.VARCHAR);
        else ps.setString(idx, v.toString());
    }

    private UUID getUuid(ResultSet rs, String col) throws SQLException {
        String s = rs.getString(col);
        return (s == null) ? null : UUID.fromString(s);
    }

    private Courses mapRow(ResultSet rs) throws SQLException {
        Courses c = new Courses();
        c.setCourseID(getUuid(rs, "courseID"));
        c.setUserID(getUuid(rs, "userID"));
        c.setName(rs.getString("name"));
        c.setDescription(rs.getString("description"));
        c.setImgURL(rs.getString("imgURL"));
        c.setRating(rs.getInt("rating"));
        if (rs.wasNull()) c.setRating(0); // nếu để null trong DB
        c.setPrice(rs.getInt("price"));
        c.setLevel(rs.getString("level"));
        c.setApproved(rs.getBoolean("isApproved"));
        return c;
    }

    // ===== CRUD =====

    @Override
    public boolean insert(Courses c) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setString(1, c.getCourseID().toString());
            setNullableUuid(ps, 2, c.getUserID());
            ps.setString(3, c.getName());
            ps.setString(4, c.getDescription());
            ps.setString(5, c.getImgURL());
            ps.setInt(6, c.getRating());
            ps.setInt(7, c.getPrice());
            ps.setString(8, c.getLevel());
            ps.setBoolean(9, c.isApproved());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Courses findById(UUID courseId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setString(1, courseId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Courses> findAll() {
        List<Courses> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public List<Courses> findByUserId(UUID userId) {
        List<Courses> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_USER_SQL)) {

            setNullableUuid(ps, 1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean update(Courses c) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {

            setNullableUuid(ps, 1, c.getUserID());
            ps.setString(2, c.getName());
            ps.setString(3, c.getDescription());
            ps.setString(4, c.getImgURL());
            ps.setInt(5, c.getRating());
            ps.setInt(6, c.getPrice());
            ps.setString(7, c.getLevel());
            ps.setBoolean(8, c.isApproved());

            ps.setString(9, c.getCourseID().toString());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIsApproved(UUID courseId, boolean approved) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ISAPPROVED_SQL)) {

            ps.setBoolean(1, approved);
            ps.setString(2, courseId.toString());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    @Override
    public boolean delete(UUID courseId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {

            ps.setString(1, courseId.toString());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    @Override
    public boolean existsById(UUID courseId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {

            ps.setString(1, courseId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
