package CommentsDAO;

import DAO.DBConnection;
import java.sql.*;
import java.util.*;
import model.Comments;

public class CommentsDAO implements ICommentsDAO {

    @Override
    public List<Comments> findByLessionId(UUID lessionId) {
        List<Comments> list = new ArrayList<>();
        String sql = "SELECT CommentID, UserID, CreateAt, ParentID, Content, ArticleID, LessionID FROM Comments WHERE LessionID=? ORDER BY CreateAt ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, lessionId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comments c = new Comments();
                    c.setCommentID(UUID.fromString(rs.getString("CommentID")));
                    c.setUserID(UUID.fromString(rs.getString("UserID")));
                    c.setCreateAt(rs.getTimestamp("CreateAt"));
                    String parent = rs.getString("ParentID");
                    c.setParentID(parent != null ? UUID.fromString(parent) : null);
                    c.setContent(rs.getString("Content"));
                    String article = rs.getString("ArticleID");
                    c.setArticleID(article != null ? UUID.fromString(article) : null);
                    c.setLessionID(UUID.fromString(rs.getString("LessionID")));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Comments> findByCourseId(UUID courseId) {
        List<Comments> list = new ArrayList<>();
        String sql = "SELECT c.CommentID, c.UserID, c.CreateAt, c.ParentID, c.Content, c.ArticleID, c.LessionID "
                + "FROM Comments c "
                + "JOIN Lession l ON c.LessionID = l.lessionID "
                + "JOIN Sections s ON l.sectionID = s.sectionID "
                + "WHERE s.courseID = ? "
                + "ORDER BY c.CreateAt ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, courseId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comments c = new Comments();
                    c.setCommentID(UUID.fromString(rs.getString("CommentID")));
                    c.setUserID(UUID.fromString(rs.getString("UserID")));
                    c.setCreateAt(rs.getTimestamp("CreateAt"));
                    String parent = rs.getString("ParentID");
                    c.setParentID(parent != null ? UUID.fromString(parent) : null);
                    c.setContent(rs.getString("Content"));
                    String article = rs.getString("ArticleID");
                    c.setArticleID(article != null ? UUID.fromString(article) : null);
                    c.setLessionID(UUID.fromString(rs.getString("LessionID")));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(Comments c) {
        String sql = "INSERT INTO Comments (CommentID, UserID, CreateAt, ParentID, Content, ArticleID, LessionID) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            UUID id = c.getCommentID() != null ? c.getCommentID() : UUID.randomUUID();
            ps.setString(1, id.toString());
            ps.setString(2, c.getUserID().toString());
            ps.setTimestamp(3, new Timestamp(c.getCreateAt() != null ? c.getCreateAt().getTime() : System.currentTimeMillis()));
            if (c.getParentID() != null) ps.setString(4, c.getParentID().toString()); else ps.setNull(4, Types.VARCHAR);
            ps.setString(5, c.getContent());
            if (c.getArticleID() != null) ps.setString(6, c.getArticleID().toString()); else ps.setNull(6, Types.VARCHAR);
            ps.setString(7, c.getLessionID().toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateContent(UUID commentId, UUID userId, String content, boolean isAdmin) {
        String sql = isAdmin
                ? "UPDATE Comments SET Content=? WHERE CommentID=?"
                : "UPDATE Comments SET Content=? WHERE CommentID=? AND UserID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, content);
            ps.setString(2, commentId.toString());
            if (!isAdmin) ps.setString(3, userId.toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UUID commentId, UUID userId, boolean isAdmin) {
        String sql = isAdmin
                ? "DELETE FROM Comments WHERE CommentID=?"
                : "DELETE FROM Comments WHERE CommentID=? AND UserID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, commentId.toString());
            if (!isAdmin) ps.setString(2, userId.toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


