// src/CourseReviewDAO/CourseReviewDAO.java
package CourseReviewDAO;


import model.CourseReview;
import DAO.DBConnection;

import java.sql.*;
import java.util.*;

public class CourseReviewDAO implements ICourseReview {

    @Override
    public void insert(CourseReview r) throws SQLException {
        String sql = "INSERT INTO CourseReview (userID, createAt, comment, courseID) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, r.getUserID());
            ps.setDate(2, new java.sql.Date(r.getCreateAt().getTime()));
            ps.setString(3, r.getComment());
            ps.setObject(4, r.getCourseID());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(CourseReview r) throws SQLException {
        String sql = "UPDATE CourseReview SET userID = ?, createAt = ?, comment = ?, courseID = ? WHERE courseReviewID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, r.getUserID());
            ps.setDate(2, new java.sql.Date(r.getCreateAt().getTime()));
            ps.setString(3, r.getComment());
            ps.setObject(4, r.getCourseID());
            ps.setObject(5, r.getCourseReviewID());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(UUID courseReviewID) throws SQLException {
        String sql = "DELETE FROM CourseReview WHERE courseReviewID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, courseReviewID);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public CourseReview findById(UUID courseReviewID) throws SQLException {
        String sql = "SELECT * FROM CourseReview WHERE courseReviewID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, courseReviewID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCourseReview(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<CourseReview> findAll() throws SQLException {
        List<CourseReview> list = new ArrayList<>();
        String sql = "SELECT * FROM CourseReview";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractCourseReview(rs));
            }
        }
        return list;
    }

    @Override
    public List<CourseReview> findByCourse(UUID courseID) throws SQLException {
        List<CourseReview> list = new ArrayList<>();
        String sql = "SELECT * FROM CourseReview WHERE courseID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, courseID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCourseReview(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<CourseReview> findByUser(UUID userID) throws SQLException {
        List<CourseReview> list = new ArrayList<>();
        String sql = "SELECT * FROM CourseReview WHERE userID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCourseReview(rs));
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateComment(UUID courseReviewID, String newComment) throws SQLException {
        String sql = "UPDATE CourseReview SET comment = ? WHERE courseReviewID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newComment);
            ps.setObject(2, courseReviewID);
            return ps.executeUpdate() > 0;
        }
    }

    private CourseReview extractCourseReview(ResultSet rs) throws SQLException {
        CourseReview r = new CourseReview();
        r.setCourseReviewID((UUID) rs.getObject("courseReviewID"));
        r.setUserID((UUID) rs.getObject("userID"));
        r.setCreateAt(rs.getDate("createAt"));
        r.setComment(rs.getString("comment"));
        r.setCourseID((UUID) rs.getObject("courseID"));
        return r;
    }
}
