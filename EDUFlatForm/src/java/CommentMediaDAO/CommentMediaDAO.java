package CommentMediaDAO;

import DAO.DBConnection;
import model.CommentMedia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentMediaDAO implements ICommentMedia {

    @Override
    public void insert(CommentMedia m) throws SQLException {
        String sql = "INSERT INTO CommentMedia (userID, createAt, contentUrl, commentID) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, m.getUserID());
            ps.setDate(2, new java.sql.Date(m.getCreateAt().getTime()));
            ps.setString(3, m.getContentUrl());
            ps.setObject(4, m.getCommentID());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(CommentMedia m) throws SQLException {
        String sql = "UPDATE CommentMedia SET userID=?, createAt=?, contentUrl=?, commentID=? WHERE commentMediaID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, m.getUserID());
            ps.setDate(2, new java.sql.Date(m.getCreateAt().getTime()));
            ps.setString(3, m.getContentUrl());
            ps.setObject(4, m.getCommentID());
            ps.setObject(5, m.getCommentMediaID());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(UUID commentMediaID) throws SQLException {
        String sql = "DELETE FROM CommentMedia WHERE commentMediaID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, commentMediaID);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public CommentMedia findById(UUID commentMediaID) throws SQLException {
        String sql = "SELECT * FROM CommentMedia WHERE commentMediaID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, commentMediaID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCommentMedia(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<CommentMedia> findAll() throws SQLException {
        String sql = "SELECT * FROM CommentMedia";
        List<CommentMedia> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractCommentMedia(rs));
            }
        }
        return list;
    }

    @Override
    public List<CommentMedia> findByUser(UUID userID) throws SQLException {
        String sql = "SELECT * FROM CommentMedia WHERE userID=?";
        List<CommentMedia> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCommentMedia(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<CommentMedia> findByComment(UUID commentID) throws SQLException {
        String sql = "SELECT * FROM CommentMedia WHERE commentID=?";
        List<CommentMedia> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, commentID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCommentMedia(rs));
                }
            }
        }
        return list;
    }

    private CommentMedia extractCommentMedia(ResultSet rs) throws SQLException {
        CommentMedia m = new CommentMedia();
        m.setCommentMediaID((UUID) rs.getObject("commentMediaID"));
        m.setUserID((UUID) rs.getObject("userID"));
        m.setCreateAt(rs.getDate("createAt"));
        m.setContentUrl(rs.getString("contentUrl"));
        m.setCommentID((UUID) rs.getObject("commentID"));
        return m;
    }
}
