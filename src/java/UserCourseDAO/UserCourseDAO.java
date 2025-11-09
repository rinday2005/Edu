package UserCourseDAO;


import DAO.DBConnection;
import model.UserCourse;

import java.sql.*;
import java.sql.Types;
import java.util.*;

public class UserCourseDAO implements IUserCourseDAO {

    @Override
    public void create(UserCourse uc) {
        String sql = "INSERT INTO UserCourse (userID, courseID) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, uc.getUserID());
            ps.setObject(2, uc.getCourseID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int deleteById(UUID userCourseId) {
        String sql = "DELETE FROM UserCourse WHERE userCourseID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, userCourseId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(UserCourse uc) {
        String sql = "UPDATE UserCourse SET userID=?, courseID=? WHERE userCourseID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, uc.getUserID());
            ps.setObject(2, uc.getCourseID());
            ps.setObject(3, uc.getUserCourseID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public UserCourse findById(UUID userCourseId) {
        String sql = "SELECT * FROM UserCourse WHERE userCourseID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, userCourseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserCourse(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserCourse> findByUser(UUID userId) {
        List<UserCourse> list = new ArrayList<>();
        String sql = "SELECT * FROM UserCourse WHERE userID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Set userID parameter - try setString first for better compatibility
            if (userId != null) {
                ps.setString(1, userId.toString());
            } else {
                ps.setNull(1, Types.VARCHAR);
            }
            
            System.out.println("[UserCourseDAO] Executing query: " + sql + " with userID: " + userId);
            
            try (ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
                    UserCourse uc = extractUserCourse(rs);
                    if (uc != null) {
                        list.add(uc);
                    }
                }
            }
            System.out.println("[UserCourseDAO] findByUser found " + list.size() + " entries for userID: " + userId);
        } catch (SQLException e) {
            System.err.println("[UserCourseDAO] Error in findByUser for userID: " + userId);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<UserCourse> findByCourse(UUID courseId) {
        List<UserCourse> list = new ArrayList<>();
        String sql = "SELECT * FROM UserCourse WHERE courseID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractUserCourse(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<UserCourse> findAll() {
        List<UserCourse> list = new ArrayList<>();
        String sql = "SELECT * FROM UserCourse";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractUserCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public boolean isCourseExist(UUID userID, UUID courseID) {
    String query = "SELECT * FROM UserCourse WHERE userID = ? AND courseID = ?";
    try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, userID.toString());
        ps.setString(2, courseID.toString());
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    private UserCourse extractUserCourse(ResultSet rs) throws SQLException {
        UserCourse uc = new UserCourse();
        try {
            // Try to get UUID from Object first, if fails try String
            Object userCourseIDObj = rs.getObject("userCourseID");
            if (userCourseIDObj instanceof UUID) {
                uc.setUserCourseID((UUID) userCourseIDObj);
            } else if (userCourseIDObj instanceof String) {
                uc.setUserCourseID(UUID.fromString((String) userCourseIDObj));
            } else {
                String userCourseIDStr = rs.getString("userCourseID");
                uc.setUserCourseID(userCourseIDStr != null ? UUID.fromString(userCourseIDStr) : null);
            }
Object userIDObj = rs.getObject("userID");
            if (userIDObj instanceof UUID) {
                uc.setUserID((UUID) userIDObj);
            } else if (userIDObj instanceof String) {
                uc.setUserID(UUID.fromString((String) userIDObj));
            } else {
                String userIDStr = rs.getString("userID");
                uc.setUserID(userIDStr != null ? UUID.fromString(userIDStr) : null);
            }
            
            Object courseIDObj = rs.getObject("courseID");
            if (courseIDObj instanceof UUID) {
                uc.setCourseID((UUID) courseIDObj);
            } else if (courseIDObj instanceof String) {
                uc.setCourseID(UUID.fromString((String) courseIDObj));
            } else {
                String courseIDStr = rs.getString("courseID");
                uc.setCourseID(courseIDStr != null ? UUID.fromString(courseIDStr) : null);
            }
        } catch (Exception e) {
            System.err.println("[UserCourseDAO] Error extracting UserCourse from ResultSet: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return uc;
    }
}
