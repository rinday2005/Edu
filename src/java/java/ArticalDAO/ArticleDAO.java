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
            + "       0 AS viewCount, "
            + "       (SELECT COUNT(*) FROM dbo.Comments c "
            + "         WHERE c.articleID = a.articleID AND c.userID <> a.userID) AS commentCount "
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
    public int update(Article a) {
        String sql = UPDATE_ARTICAL;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, a.getUserID());
            ps.setDate(2, new java.sql.Date(a.getCreateAt().getTime()));
            ps.setString(3, a.getStatus());
            ps.setString(4, a.getTitle());
            ps.setString(5, a.getContent());
            ps.setObject(6, a.getArticleID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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

        // articleID
        Object artObj = rs.getObject("articleID");
        if (artObj instanceof java.util.UUID) {
            a.setArticleID((java.util.UUID) artObj);
        } else if (artObj instanceof String) {
            a.setArticleID(java.util.UUID.fromString((String) artObj));
        } else if (artObj != null) {
            a.setArticleID(java.util.UUID.fromString(String.valueOf(artObj)));
        }

        // userID
        Object userObj = rs.getObject("userID");
        if (userObj instanceof java.util.UUID) {
            a.setUserID((java.util.UUID) userObj);
        } else if (userObj instanceof String) {
            a.setUserID(java.util.UUID.fromString((String) userObj));
        } else if (userObj != null) {
            a.setUserID(java.util.UUID.fromString(String.valueOf(userObj)));
        }

        // createAt – DB dùng DATE (không có time), nhưng vẫn dùng getTimestamp để an toàn
        try {
            java.sql.Timestamp ts = rs.getTimestamp("createAt");
            if (ts != null) {
                a.setCreateAt(new java.util.Date(ts.getTime()));
            } else {
                // Nếu getTimestamp null, thử getDate
                java.sql.Date date = rs.getDate("createAt");
                a.setCreateAt(date != null ? new java.util.Date(date.getTime()) : null);
            }
        } catch (SQLException e) {
            System.out.println("Error getting createAt: " + e.getMessage());
            a.setCreateAt(null);
        }

        a.setStatus(rs.getString("status"));
        a.setTitle(rs.getString("title"));
        a.setContent(rs.getString("content"));
        return a;
    }

    @Override
    public List<Article> findAllWithStats() {
        List<Article> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(FIND_ALL_WITH_STATS); 
             ResultSet rs = ps.executeQuery()) {
            
            System.out.println("=== DEBUG: findAllWithStats ===");
            int count = 0;
            while (rs.next()) {
                count++;
                Article a = extractArticle(rs);
                
                // viewCount được set từ query (0 AS viewCount)
                try {
                    int viewCount = rs.getInt("viewCount");
                    a.setViewCount(viewCount);
                } catch (SQLException e) {
                    System.out.println("Error getting viewCount: " + e.getMessage());
                    a.setViewCount(0);
                }
                
                // commentCount từ subquery
                try {
                    int commentCount = rs.getInt("commentCount");
                    a.setCommentCount(commentCount);
                } catch (SQLException e) {
                    System.out.println("Error getting commentCount: " + e.getMessage());
                    a.setCommentCount(0);
                }
                
                System.out.println("Loaded article " + count + ": " + a.getTitle() + " (ID: " + a.getArticleID() + ", userID: " + a.getUserID() + ")");
                list.add(a);
            }
            System.out.println("Total articles loaded: " + count);
            System.out.println("=== END DEBUG ===");
            
        } catch (SQLException e) {
            System.err.println("SQL Error in findAllWithStats: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error in findAllWithStats: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    UUID authorId = article.getUserID();
    @Override
    public int increaseViewCount(UUID articleId, UUID viewerId, UUID authorId) {
    if (viewerId != null && viewerId.equals(authorId)) return 0; // không tính lượt xem của chính tác giả
    String sql = INCREASE_VIEW;
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setObject(1, articleId);
        return ps.executeUpdate();
    } catch (SQLException e) { e.printStackTrace(); return 0; }
}
}
