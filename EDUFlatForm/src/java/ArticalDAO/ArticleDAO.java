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

    @Override
    public void create(Article a) {
        String sql = "INSERT INTO Article (userID, createAt, status, title, content) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, a.getUserID());
            ps.setDate(2, new java.sql.Date(a.getCreateAt().getTime()));
            ps.setString(3, a.getStatus());
            ps.setString(4, a.getTitle());
            ps.setString(5, a.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int update(Article a) {
        String sql = "UPDATE Article SET userID = ?, createAt = ?, status = ?, title = ?, content = ? WHERE articleID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        String sql = "UPDATE Article SET status = ? WHERE articleID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        String sql = "DELETE FROM Article WHERE articleID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, articleId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Article findById(UUID articleId) {
        String sql = "SELECT * FROM Article WHERE articleID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM Article ORDER BY createAt DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
        a.setArticleID((UUID) rs.getObject("articleID"));
        a.setUserID((UUID) rs.getObject("userID"));
        a.setCreateAt(rs.getDate("createAt"));
        a.setStatus(rs.getString("status"));
        a.setTitle(rs.getString("title"));
        a.setContent(rs.getString("content"));
        return a;
    }
}
