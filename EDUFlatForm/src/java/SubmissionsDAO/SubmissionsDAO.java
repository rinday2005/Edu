package SubmissionsDAO;


import DAO.DBConnection;
import model.Submissions;

import java.sql.*;
import java.util.*;

public class SubmissionsDAO implements ISubmissionsDAO {

    @Override
    public void create(Submissions s) {
        String sql = "INSERT INTO Submissions (userID, createAt, status, assignmentID) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, s.getUserID());
            ps.setDate(2, new java.sql.Date(s.getCreateAt().getTime()));
            ps.setString(3, s.getStatus());
            ps.setObject(4, s.getAssignmentID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int update(Submissions s) {
        String sql = "UPDATE Submissions SET userID=?, createAt=?, status=?, assignmentID=? WHERE submissionID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, s.getUserID());
            ps.setDate(2, new java.sql.Date(s.getCreateAt().getTime()));
            ps.setString(3, s.getStatus());
            ps.setObject(4, s.getAssignmentID());
            ps.setObject(5, s.getSubmissionID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(UUID submissionId) {
        String sql = "DELETE FROM Submissions WHERE submissionID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, submissionId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Submissions findById(UUID submissionId) {
        String sql = "SELECT * FROM Submissions WHERE submissionID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, submissionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractSubmission(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateStatus(UUID userId, boolean status) {
        String sql = "UPDATE Submissions SET status=? WHERE userID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status ? "Completed" : "Pending");
            ps.setObject(2, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Submissions> findByAssignment(UUID assignmentId) {
        List<Submissions> list = new ArrayList<>();
        String sql = "SELECT * FROM Submissions WHERE assignmentID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, assignmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractSubmission(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Submissions> findByUser(UUID userId) {
        List<Submissions> list = new ArrayList<>();
        String sql = "SELECT * FROM Submissions WHERE userID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractSubmission(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Submissions> findAll() {
        List<Submissions> list = new ArrayList<>();
        String sql = "SELECT * FROM Submissions";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractSubmission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Submissions extractSubmission(ResultSet rs) throws SQLException {
        Submissions s = new Submissions();
        s.setSubmissionID((UUID) rs.getObject("submissionID"));
        s.setUserID((UUID) rs.getObject("userID"));
        s.setCreateAt(rs.getDate("createAt"));
        s.setStatus(rs.getString("status"));
        s.setAssignmentID((UUID) rs.getObject("assignmentID"));
        return s;
    }
}

