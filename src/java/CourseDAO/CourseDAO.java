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
        // Sử dụng cascade delete để xóa tất cả dữ liệu liên quan
        return deleteCascade(courseId);
    }
    
    /**
     * Xóa khóa học và tất cả dữ liệu liên quan (cascade delete)
     * Xóa theo thứ tự: UserCourse -> Cart -> Comments -> Assignments -> Lessons -> Sections -> Courses
     */
    public boolean deleteCascade(UUID courseId) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Bắt đầu transaction
            
            String courseIdStr = courseId.toString();
            
            // 1. Xóa UserCourse (khóa học đã đăng ký)
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM UserCourse WHERE courseID = ?")) {
                ps.setString(1, courseIdStr);
                ps.executeUpdate();
            }
            
            // 2. Xóa Cart (khóa học trong giỏ hàng)
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM cart WHERE courseID = ?")) {
                ps.setString(1, courseIdStr);
                ps.executeUpdate();
            }
            
            // 3. Xóa Comments (bình luận của khóa học)
            // Comments có thể liên kết qua lessonID, cần xóa qua sections và lessons
            // Hoặc nếu Comments có courseID trực tiếp thì xóa trực tiếp
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM Comments WHERE courseID = ?")) {
                ps.setString(1, courseIdStr);
                ps.executeUpdate();
            } catch (SQLException e) {
                // Nếu Comments không có courseID, bỏ qua
                System.out.println("Comments table may not have courseID column, skipping...");
            }
            
            // 4. Xóa Assignments (bài tập/kiểm tra) - thông qua sections
            // Lấy tất cả sectionID của khóa học này
            java.util.List<UUID> sectionIds = new java.util.ArrayList<>();
            try (PreparedStatement ps = con.prepareStatement("SELECT sectionID FROM Sections WHERE courseID = ?")) {
                ps.setString(1, courseIdStr);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        UUID sectionId = getUuid(rs, "sectionID");
                        if (sectionId != null) {
                            sectionIds.add(sectionId);
                        }
                    }
                }
            }
            
            // Xóa Assignments theo sectionID
            if (!sectionIds.isEmpty()) {
                // Xóa Submissions trước (có foreign key đến Assignments)
                for (UUID sectionId : sectionIds) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "DELETE s FROM Submissions s " +
                            "INNER JOIN Assignments a ON s.assignmentID = a.assignmentID " +
                            "WHERE a.sectionID = ?")) {
                        ps.setObject(1, sectionId);
                        ps.executeUpdate();
                    }
                    
                    // Xóa McqUserAnswer trước (có foreign key đến Submissions)
                    try (PreparedStatement ps = con.prepareStatement(
                            "DELETE mua FROM McqUserAnswer mua " +
                            "INNER JOIN Submissions s ON mua.submissionID = s.submissionID " +
                            "INNER JOIN Assignments a ON s.assignmentID = a.assignmentID " +
                            "WHERE a.sectionID = ?")) {
                        ps.setObject(1, sectionId);
                        ps.executeUpdate();
                    }
                    
                    // Xóa Assignments
                    try (PreparedStatement ps = con.prepareStatement("DELETE FROM Assignments WHERE sectionID = ?")) {
                        ps.setObject(1, sectionId);
                        ps.executeUpdate();
                    }
                }
            }
            
            // 5. Xóa Lessons (bài học) - thông qua sections
            if (!sectionIds.isEmpty()) {
                // Lấy tất cả lessonID
                java.util.List<UUID> lessonIds = new java.util.ArrayList<>();
                for (UUID sectionId : sectionIds) {
                    try (PreparedStatement ps = con.prepareStatement("SELECT lessionID FROM Lession WHERE sectionID = ?")) {
                        ps.setObject(1, sectionId);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                UUID lessonId = getUuid(rs, "lessionID");
                                if (lessonId != null) {
                                    lessonIds.add(lessonId);
                                }
                            }
                        }
                    }
                }
                
                // Xóa Comments liên kết với lessons
                if (!lessonIds.isEmpty()) {
                    for (UUID lessonId : lessonIds) {
                        try (PreparedStatement ps = con.prepareStatement("DELETE FROM Comments WHERE lessonID = ?")) {
                            ps.setObject(1, lessonId);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            // Nếu Comments không có lessonID, bỏ qua
                            System.out.println("Comments may not have lessonID column, skipping...");
                        }
                    }
                }
                
                // Xóa LessionMaterial
                if (!lessonIds.isEmpty()) {
                    for (UUID lessonId : lessonIds) {
                        try (PreparedStatement ps = con.prepareStatement("DELETE FROM LessionMaterial WHERE lessionID = ?")) {
                            ps.setObject(1, lessonId);
                            ps.executeUpdate();
                        }
                    }
                }
                
                // Xóa Lession
                for (UUID sectionId : sectionIds) {
                    try (PreparedStatement ps = con.prepareStatement("DELETE FROM Lession WHERE sectionID = ?")) {
                        ps.setObject(1, sectionId);
                        ps.executeUpdate();
                    }
                }
            }
            
            // 6. Xóa Sections (chương)
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM Sections WHERE courseID = ?")) {
                ps.setString(1, courseIdStr);
                ps.executeUpdate();
            }
            
            // 7. Cuối cùng xóa Courses
            try (PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
                ps.setString(1, courseIdStr);
                int result = ps.executeUpdate();
                
                con.commit(); // Commit transaction
                return result > 0;
            }
            
        } catch (SQLException e) { 
            if (con != null) {
                try {
                    con.rollback(); // Rollback nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace(); 
            return false; 
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Khôi phục auto-commit
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
    @Override
    public UUID getCourseIdBySectionId(UUID sectionID) throws SQLException {
        String sql = "SELECT courseID FROM Sections WHERE sectionID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, sectionID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UUID courseID = (UUID) rs.getObject("courseID");
                    return courseID;
                }
            }
        }
        return null;
    }
}
