/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArticalDAO;

import model.Article;
import DAO.DBConnection;

import java.sql.*;
import java.util.*;

public class ArticleDAO implements IArticle {

    final String INSERT_ARTICAL = "INSERT INTO Article (articleID, userID, createAt, status, title, content) VALUES (?,?,?,?,?,?)";
    final String UPDATE_ARTICAL = "UPDATE Article SET userID = ?, createAt = ?, status = ?, title = ?, content = ? WHERE articleID = ?";
    final String UPDATE_STATUS = "UPDATE Article SET status = ? WHERE articleID = ?";
    final String DELETE_ARTICAL_BY_ID = "DELETE FROM Article WHERE articleID = ?";
    final String FIND_ARTICAL_BY_ID = "SELECT * FROM Article WHERE articleID = ?";
    final String FIND_ALL_ARTICAL = "SELECT * FROM Article ORDER BY createAt DESC";
    final String FIND_ALL_WITH_STATS
            = "SELECT a.articleID, a.userID, a.createAt, a.status, a.title, a.content, "
            + "       CAST(0 AS int) AS viewCount, "
            + // <-- không cần cột
            "       (SELECT COUNT(*) FROM dbo.Comment c "
            + "         WHERE c.articleID = a.articleID AND (c.userID IS NULL OR c.userID <> a.userID)) AS commentCount "
            + "FROM dbo.Article a "
            + "ORDER BY a.createAt DESC";
    final String INCREASE_VIEW = "UPDATE dbo.Article SET viewCount = viewCount + 1 WHERE articleID = ?";
    Article article = new Article();

    @Override
    public void create(Article a) {
        String sql = INSERT_ARTICAL;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, a.getArticleID()); // UUID
            ps.setObject(2, a.getUserID());    // UUID
            ps.setTimestamp(3, new java.sql.Timestamp(a.getCreateAt().getTime()));
            ps.setString(4, a.getStatus());
            ps.setString(5, a.getTitle());
            ps.setString(6, a.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UUID update(Article a) {
        final String sql = UPDATE_ARTICAL; // "UPDATE Article SET userID = ?, createAt = ?, status = ?, title = ?, content = ? WHERE articleID = ?"
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            // SQL Server (uniqueidentifier) chấp nhận setObject(UUID) với driver mới
            ps.setObject(1, a.getUserID());
            ps.setTimestamp(2, new java.sql.Timestamp(a.getCreateAt().getTime())); // giữ giờ/phút/giây
            ps.setString(3, a.getStatus());
            ps.setString(4, a.getTitle());
            ps.setString(5, a.getContent());
            ps.setObject(6, a.getArticleID());

            int rows = ps.executeUpdate();
            return rows > 0 ? a.getArticleID() : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateStatus(UUID articleId, String status) {
        String sql = UPDATE_STATUS;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setObject(2, articleId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(UUID articleId) {
        String sql = DELETE_ARTICAL_BY_ID;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, articleId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Article findById(UUID articleId) {
        String sql = FIND_ARTICAL_BY_ID;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, articleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractArticle(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Article> findAll() {
        List<Article> list = new ArrayList<>();
        String sql = FIND_ALL_ARTICAL;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractArticle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Article extractArticle(ResultSet rs) throws SQLException {
        Article a = new Article();

        Object artObj = rs.getObject("articleID");
        if (artObj instanceof java.util.UUID) {
            a.setArticleID((java.util.UUID) artObj);
        } else if (artObj != null) {
            a.setArticleID(java.util.UUID.fromString(String.valueOf(artObj)));
        }

        Object userObj = rs.getObject("userID");
        if (userObj instanceof java.util.UUID) {
            a.setUserID((java.util.UUID) userObj);
        } else if (userObj != null) {
            a.setUserID(java.util.UUID.fromString(String.valueOf(userObj)));
        }

        // createAt là DATE -> lấy bằng getDate; nếu null thì để null
        java.sql.Date d = rs.getDate("createAt");
        a.setCreateAt(d != null ? new java.util.Date(d.getTime()) : null);

        a.setStatus(rs.getString("status"));
        a.setTitle(rs.getString("title"));
        a.setContent(rs.getString("content"));

        // 2 dòng dưới đây chỉ chạy nếu SELECT có alias tương ứng
        try {
            a.setViewCount(rs.getInt("viewCount"));
        } catch (SQLException ignore) {
        }
        try {
            a.setCommentCount(rs.getInt("commentCount"));
        } catch (SQLException ignore) {
        }

        return a;
    }

    @Override
    public List<Article> findAllWithStats() {
        List<Article> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(FIND_ALL_WITH_STATS); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Article a = extractArticle(rs);           // dùng bản extract an toàn UUID như mình gửi trước
                a.setViewCount(rs.getInt("viewCount"));   // có cột => set
                a.setCommentCount(rs.getInt("commentCount"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    UUID authorId = article.getUserID();

    @Override
    public int increaseViewCount(UUID articleId, UUID viewerId, UUID authorId) {
        if (viewerId != null && viewerId.equals(authorId)) {
            return 0; // không tính lượt xem của chính tác giả
        }
        String sql = INCREASE_VIEW;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, articleId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
