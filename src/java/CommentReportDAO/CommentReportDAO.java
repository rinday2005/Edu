package CommentReportDAO;

import DAO.DBConnection;
import model.CommentReport;
import java.sql.*;
import java.util.*;

public class CommentReportDAO implements ICommentReportDAO {
    
    private static final String TABLE = "dbo.CommentReport";
    
    private static final String INSERT_SQL = 
        "INSERT INTO " + TABLE + " (reportID, commentID, reporterID, reason, createAt, status) " +
        "VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_BY_COMMENT_SQL = 
        "SELECT reportID, commentID, reporterID, reason, createAt, status " +
        "FROM " + TABLE + " WHERE commentID = ? ORDER BY createAt DESC";
    
    private static final String SELECT_PENDING_SQL = 
        "SELECT reportID, commentID, reporterID, reason, createAt, status " +
        "FROM " + TABLE + " WHERE status = 'pending' ORDER BY createAt DESC";
    
    private static final String SELECT_ALL_SQL = 
        "SELECT reportID, commentID, reporterID, reason, createAt, status " +
        "FROM " + TABLE + " ORDER BY createAt DESC";
    
    private static final String UPDATE_STATUS_SQL = 
        "UPDATE " + TABLE + " SET status = ? WHERE reportID = ?";
    
    private static final String DELETE_SQL = 
        "DELETE FROM " + TABLE + " WHERE reportID = ?";
    
    private static final String EXISTS_SQL = 
        "SELECT 1 FROM " + TABLE + " WHERE commentID = ? AND reporterID = ?";
    
    private UUID getUuid(ResultSet rs, String col) throws SQLException {
        String s = rs.getString(col);
        return (s == null) ? null : UUID.fromString(s);
    }
    
    private CommentReport mapRow(ResultSet rs) throws SQLException {
        CommentReport r = new CommentReport();
        r.setReportID(getUuid(rs, "reportID"));
        r.setCommentID(getUuid(rs, "commentID"));
        r.setReporterID(getUuid(rs, "reporterID"));
        r.setReason(rs.getString("reason"));
        r.setCreateAt(rs.getTimestamp("createAt"));
        r.setStatus(rs.getString("status"));
        return r;
    }
    
    @Override
    public boolean insert(CommentReport report) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            
            UUID reportID = report.getReportID() != null ? report.getReportID() : UUID.randomUUID();
            ps.setString(1, reportID.toString());
            ps.setString(2, report.getCommentID().toString());
            ps.setString(3, report.getReporterID().toString());
            ps.setString(4, report.getReason() != null ? report.getReason() : "spam");
            ps.setTimestamp(5, new Timestamp(report.getCreateAt() != null ? report.getCreateAt().getTime() : System.currentTimeMillis()));
            ps.setString(6, report.getStatus() != null ? report.getStatus() : "pending");
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<CommentReport> findByCommentId(UUID commentID) {
        List<CommentReport> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_COMMENT_SQL)) {
            
            ps.setString(1, commentID.toString());
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
    public List<CommentReport> findAllPending() {
        List<CommentReport> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PENDING_SQL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<CommentReport> findAll() {
        List<CommentReport> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public boolean updateStatus(UUID reportID, String status) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_STATUS_SQL)) {
            
            ps.setString(1, status);
            ps.setString(2, reportID.toString());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(UUID reportID) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            
            ps.setString(1, reportID.toString());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean exists(UUID commentID, UUID reporterID) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {
            
            ps.setString(1, commentID.toString());
            ps.setString(2, reporterID.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

