// src/SectionsDAO/SectionDAO.java
package SectionsDAO;

import DAO.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.UUID;
import model.Sections;

public class SectionDAO implements ISectionDAO {

    private static final String TABLE = "dbo.Sections";

    private static final String INSERT_SQL
            = "INSERT INTO " + TABLE + " (sectionID, userID, status, name, description, courseID) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL
            = "SELECT sectionID, userID, status, name, description, courseID FROM " + TABLE + " WHERE sectionID = ?";

    private static final String SELECT_ALL_SQL
            = "SELECT sectionID, userID, status, name, description, courseID FROM " + TABLE;

    private static final String SELECT_BY_COURSE_SQL
            = "SELECT sectionID, userID, status, name, description, courseID FROM " + TABLE + " WHERE courseID = ?";

    private static final String UPDATE_SQL
            = "UPDATE " + TABLE + " SET userID=?, status=?, name=?, description=?, courseID=? WHERE sectionID=?";

    private static final String DELETE_SQL
            = "DELETE FROM " + TABLE + " WHERE sectionID = ?";

    private static final String UPDATE_STATUS_SQL
            = "UPDATE " + TABLE + " SET status=? WHERE sectionID=?";

    // --- Helpers ---
    /**
     * set UUID có thể null cho SQL Server UNIQUEIDENTIFIER
     */
    private void setNullableUuid(PreparedStatement ps, int idx, UUID value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.VARCHAR);          // <— đổi từ OTHER sang VARCHAR
        } else {
            ps.setString(idx, value.toString());     // <— ghi UUID dạng chuỗi
        }
    }

    /**
     * đọc UUID có thể null
     */
    private UUID getUuid(ResultSet rs, String col) throws SQLException {
        String s = rs.getString(col);                // <— lấy về dạng chuỗi
        return (s == null) ? null : UUID.fromString(s);
    }

    private Sections mapRow(ResultSet rs) throws SQLException {
        Sections s = new Sections();
        s.setSectionID(getUuid(rs, "sectionID"));
        s.setUserID(getUuid(rs, "userID"));
        s.setStatus(rs.getBoolean("status"));             // BIT NOT NULL
        s.setName(rs.getString("name"));
        s.setDescription(rs.getString("description"));
        s.setCourseID(getUuid(rs, "courseID"));
        return s;
    }

    // --- CRUD ---
    @Override
    public boolean insert(Sections s) {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_SQL)) {

            // sectionID bắt buộc (PK)
            ps.setString(1, s.getSectionID().toString());

            // userID & courseID có thể null
            setNullableUuid(ps, 2, s.getUserID());

            ps.setBoolean(3, s.isStatus());
            ps.setString(4, s.getName());
            ps.setString(5, s.getDescription());

            setNullableUuid(ps, 6, s.getCourseID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Sections findById(UUID sectionID) {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setString(1, sectionID.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Sections> findAll() {
        List<Sections> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_SQL); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Sections> findByCourseId(UUID courseID) {
        List<Sections> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_BY_COURSE_SQL)) {
            setNullableUuid(ps, 1, courseID); // nếu muốn tìm NULL courseID hãy viết query riêng
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean update(Sections s) {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_SQL)) {

            setNullableUuid(ps, 1, s.getUserID());
            ps.setBoolean(2, s.isStatus());
            ps.setString(3, s.getName());
            ps.setString(4, s.getDescription());
            setNullableUuid(ps, 5, s.getCourseID());

            ps.setString(6, s.getSectionID().toString());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UUID sectionID) {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(DELETE_SQL)) {

            ps.setString(1, sectionID.toString());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Update status riêng ---
    @Override
    public boolean updateStatus(UUID sectionID, boolean status) {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_STATUS_SQL)) {

            ps.setBoolean(1, status);
            ps.setString(2, sectionID.toString());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
