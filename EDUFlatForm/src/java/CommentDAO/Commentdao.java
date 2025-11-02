package CommentDAO;

import DAO.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.UUID;
import model.Comments;
import model.CommentMedia;

public class Commentdao implements ICommentDAO {

    // ====== Tên bảng ======
    private static final String TBL_COMMENT = "[dbo].[Comments]";
    private static final String TBL_MEDIA   = "[dbo].[CommentMedia]";
    private static final String TBL_ARTICLE = "[dbo].[Article]";
    private static final String TBL_USER    = "[dbo].[User]";

    // ====== COMMENTS: SQL (đặt cố định ở đây) ======
    private static final String SQL_COMMENT_INSERT =
        "INSERT INTO " + TBL_COMMENT + " " +
        "(commentID, userID, createAt, parentID, content, articleID, lessionID, status) " +
        "VALUES (?,?,?,?,?,?,?,?)";

    private static final String SQL_COMMENT_FIND_BY_ID =
        "SELECT commentID, userID, createAt, parentID, content, articleID, lessionID, status " +
        "FROM " + TBL_COMMENT + " WHERE commentID=?";

    private static final String SQL_COMMENT_UPDATE =
        "UPDATE " + TBL_COMMENT + " SET userID=?, createAt=?, parentID=?, content=?, " +
        "articleID=?, lessionID=?, status=? WHERE commentID=?";

    private static final String SQL_COMMENT_DELETE_HARD =
        "DELETE FROM " + TBL_COMMENT + " WHERE commentID=?";

    private static final String SQL_COMMENT_DELETE_SOFT =
        "UPDATE " + TBL_COMMENT + " SET status='Deleted' WHERE commentID=?";

    private static final String SQL_COMMENT_UPDATE_STATUS =
        "UPDATE " + TBL_COMMENT + " SET status=? WHERE commentID=?";

    private static final String SQL_COMMENT_LIST_BY_ARTICLE =
        "SELECT commentID, userID, createAt, parentID, content, articleID, lessionID, status " +
        "FROM " + TBL_COMMENT + " " +
        "WHERE articleID=? AND (status IS NULL OR status <> 'Deleted') " +
        "ORDER BY createAt DESC " +
        "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    // ====== INSTRUCTOR VIEW (yêu cầu: join User, Article) – trả CommentMedia ======
    private static final String SQL_MEDIA_LIST_BY_INSTRUCTOR =
        "SELECT m.commentMediaID, m.userID, m.createAt, m.contentUrl, m.commentID " +
        "FROM " + TBL_ARTICLE + " a " +
        "JOIN " + TBL_COMMENT + " c ON c.articleID = a.articleID " +
        "JOIN " + TBL_MEDIA   + " m ON m.commentID = c.commentID " +
        "WHERE a.userID=? AND (c.status IS NULL OR c.status <> 'Deleted') " +
        "ORDER BY m.createAt DESC " +
        "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String SQL_MEDIA_COUNT_BY_INSTRUCTOR =
        "SELECT COUNT(*) " +
        "FROM " + TBL_ARTICLE + " a " +
        "JOIN " + TBL_COMMENT + " c ON c.articleID = a.articleID " +
        "JOIN " + TBL_MEDIA   + " m ON m.commentID = c.commentID " +
        "WHERE a.userID=? AND (c.status IS NULL OR c.status <> 'Deleted')";

    // ====== COMMENT MEDIA: SQL ======
    private static final String SQL_MEDIA_INSERT =
        "INSERT INTO " + TBL_MEDIA + " (commentMediaID, userID, createAt, contentUrl, commentID) " +
        "VALUES (?,?,?,?,?)";

    private static final String SQL_MEDIA_FIND_BY_ID =
        "SELECT commentMediaID, userID, createAt, contentUrl, commentID " +
        "FROM " + TBL_MEDIA + " WHERE commentMediaID=?";

    private static final String SQL_MEDIA_UPDATE =
        "UPDATE " + TBL_MEDIA + " SET userID=?, createAt=?, contentUrl=?, commentID=? " +
        "WHERE commentMediaID=?";

    private static final String SQL_MEDIA_DELETE =
        "DELETE FROM " + TBL_MEDIA + " WHERE commentMediaID=?";

    // ========================= IMPLEMENTATION =========================

    // -------- COMMENTS CRUD --------
    @Override
    public void create(Comments c) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_COMMENT_INSERT)) {

            ps.setString(1, uuid(c.getCommentID()));
            ps.setString(2, uuid(c.getUserID()));
            ps.setTimestamp(3, ts(c.getCreateAt()));
            setNullableUUID(ps, 4, c.getParentID());
            ps.setNString(5, c.getContent());
            setNullableUUID(ps, 6, c.getArticleID());
            setNullableUUID(ps, 7, c.getLessionID());
            ps.setString(8, "Pending");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("create Comment failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Comments findById(UUID commentID) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_COMMENT_FIND_BY_ID)) {
            ps.setString(1, uuid(commentID));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapComment(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("findById failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Comments c) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_COMMENT_UPDATE)) {
            ps.setString(1, uuid(c.getUserID()));
            ps.setTimestamp(2, ts(c.getCreateAt()));
            setNullableUUID(ps, 3, c.getParentID());
            ps.setNString(4, c.getContent());
            setNullableUUID(ps, 5, c.getArticleID());
            setNullableUUID(ps, 6, c.getLessionID());
            ps.setString(7, "Pending"); // hoặc lấy từ c nếu bạn có field status
            ps.setString(8, uuid(c.getCommentID()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("update Comment failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(UUID commentID) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_COMMENT_DELETE_HARD)) {
            ps.setString(1, uuid(commentID));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("hard delete Comment failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void softDelete(UUID commentID) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_COMMENT_DELETE_SOFT)) {
            ps.setString(1, uuid(commentID));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("soft delete Comment failed: " + e.getMessage(), e);
        }
    }

    // -------- BỔ SUNG --------
    @Override
    public void updateStatus(UUID commentID, String newStatus) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_COMMENT_UPDATE_STATUS)) {
            ps.setString(1, newStatus);
            ps.setString(2, uuid(commentID));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("updateStatus failed: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Comments> listByArticle(UUID articleID, int offset, int limit) {
        List<Comments> list = new ArrayList<>();
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_COMMENT_LIST_BY_ARTICLE)) {
            ps.setString(1, uuid(articleID));
            ps.setInt(2, Math.max(0, offset));
            ps.setInt(3, Math.max(1, limit));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapComment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("listByArticle failed: " + e.getMessage(), e);
        }
        return list;
    }

    // -------- THEO INSTRUCTOR (yêu cầu) --------
    @Override
    public List<CommentMedia> listByInstructor(UUID instructorID, int offset, int limit) {
        List<CommentMedia> list = new ArrayList<>();
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_MEDIA_LIST_BY_INSTRUCTOR)) {
            ps.setString(1, uuid(instructorID));
            ps.setInt(2, Math.max(0, offset));
            ps.setInt(3, Math.max(1, limit));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapMedia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("listByInstructor failed: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public int countByInstructor(UUID instructorID) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_MEDIA_COUNT_BY_INSTRUCTOR)) {
            ps.setString(1, uuid(instructorID));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("countByInstructor failed: " + e.getMessage(), e);
        }
    }

    // -------- COMMENT MEDIA CRUD --------
    @Override
    public void createMedia(CommentMedia m) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_MEDIA_INSERT)) {
            ps.setString(1, uuid(m.getCommentMediaID()));
            ps.setString(2, uuid(m.getUserID()));
            ps.setTimestamp(3, ts(m.getCreateAt()));
            ps.setString(4, m.getContentUrl());
            ps.setString(5, uuid(m.getCommentID()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("createMedia failed: " + e.getMessage(), e);
        }
    }

    @Override
    public CommentMedia findMediaById(UUID commentMediaID) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_MEDIA_FIND_BY_ID)) {
            ps.setString(1, uuid(commentMediaID));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapMedia(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("findMediaById failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateMedia(CommentMedia m) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_MEDIA_UPDATE)) {
            ps.setString(1, uuid(m.getUserID()));
            ps.setTimestamp(2, ts(m.getCreateAt()));
            ps.setString(3, m.getContentUrl());
            ps.setString(4, uuid(m.getCommentID()));
            ps.setString(5, uuid(m.getCommentMediaID()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("updateMedia failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteMedia(UUID commentMediaID) {
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_MEDIA_DELETE)) {
            ps.setString(1, uuid(commentMediaID));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("deleteMedia failed: " + e.getMessage(), e);
        }
    }

    // ========================= Helpers =========================
    private static String uuid(UUID id) { return id == null ? null : id.toString(); }
    private static Timestamp ts(java.util.Date d) {
        return new Timestamp(d != null ? d.getTime() : System.currentTimeMillis());
    }
    private static void setNullableUUID(PreparedStatement ps, int idx, UUID id) throws SQLException {
        if (id == null) ps.setNull(idx, Types.VARCHAR); else ps.setString(idx, uuid(id));
    }

    private static Comments mapComment(ResultSet rs) throws SQLException {
        Comments c = new Comments();
        c.setCommentID(getUUID(rs, "commentID"));
        c.setUserID(getUUID(rs, "userID"));
        c.setCreateAt(rs.getTimestamp("createAt"));
        c.setParentID(getUUID(rs, "parentID"));
        c.setContent(rs.getNString("content"));
        c.setArticleID(getUUID(rs, "articleID"));
        c.setLessionID(getUUID(rs, "lessionID"));
        // Nếu Comments có field status -> thêm setter ở model để map
        return c;
    }

    private static CommentMedia mapMedia(ResultSet rs) throws SQLException {
        CommentMedia m = new CommentMedia();
        m.setCommentMediaID(getUUID(rs, "commentMediaID"));
        m.setUserID(getUUID(rs, "userID"));
        m.setCreateAt(rs.getTimestamp("createAt"));
        m.setContentUrl(rs.getString("contentUrl"));
        m.setCommentID(getUUID(rs, "commentID"));
        return m;
    }

    private static UUID getUUID(ResultSet rs, String col) throws SQLException {
        String s = rs.getString(col);
        return (s == null) ? null : UUID.fromString(s);
    }
}
