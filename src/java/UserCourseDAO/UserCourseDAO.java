package UserCourseDAO;


import DAO.DBConnection;
import model.UserCourse;

import java.sql.*;
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

            ps.setObject(1, userId);
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
        uc.setUserCourseID((UUID) rs.getObject("userCourseID"));
        uc.setUserID((UUID) rs.getObject("userID"));
        uc.setCourseID((UUID) rs.getObject("courseID"));
        return uc;
    }
}
