/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package McqChoiceDAO;

import DAO.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.McqChoices;

/**
 *
 * @author ADMIN
 */
public class McqChoiceDAO implements IMcqChoiceDAO {
    
    private static final String TABLE = "dbo.McqChoices";
    
    private static final String INSERT_SQL = "INSERT INTO " + TABLE + 
        " (id, content, isCorrect, mcqQuestionId) " +
        "VALUES (?, ?, ?, ?)";
    
    private static final String SELECT_BY_ID_SQL = "SELECT id, content, isCorrect, mcqQuestionId " +
        "FROM " + TABLE + " WHERE id = ?";
    
    private static final String SELECT_ALL_SQL = "SELECT id, content, isCorrect, mcqQuestionId " +
        "FROM " + TABLE + " ORDER BY content ASC";
    
    private static final String SELECT_BY_QUESTION_SQL = "SELECT id, content, isCorrect, mcqQuestionId " +
        "FROM " + TABLE + " WHERE mcqQuestionId = ? ORDER BY content ASC";
    
    private static final String UPDATE_SQL = "UPDATE " + TABLE + 
        " SET content=?, isCorrect=?, mcqQuestionId=? " +
        "WHERE id=?";
    
    private static final String DELETE_SQL = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    private static final String DELETE_BY_QUESTION_SQL = "DELETE FROM " + TABLE + " WHERE mcqQuestionId = ?";
    
    private void setNullableUuid(PreparedStatement ps, int idx, UUID value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.VARCHAR);
        } else {
            ps.setString(idx, value.toString());
        }
    }
    
    private UUID getUuid(ResultSet rs, String col) throws SQLException {
        String s = rs.getString(col);
        return (s == null) ? null : UUID.fromString(s);
    }
    
    private McqChoices mapRow(ResultSet rs) throws SQLException {
        McqChoices c = new McqChoices();
        c.setId(getUuid(rs, "id"));
        c.setContent(rs.getString("content"));
        c.setIsCorrect(rs.getBoolean("isCorrect"));
        c.setMcqQuestionId(getUuid(rs, "mcqQuestionId"));
        return c;
    }

    @Override
    public boolean insert(McqChoices choice) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            
            ps.setString(1, choice.getId().toString());
            ps.setString(2, choice.getContent());
            ps.setBoolean(3, choice.isIsCorrect());
            setNullableUuid(ps, 4, choice.getMcqQuestionId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public McqChoices findById(UUID choiceId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {
            
            ps.setString(1, choiceId.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<McqChoices> findAll() {
        List<McqChoices> list = new ArrayList<>();
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
    public List<McqChoices> findByQuestionId(UUID questionId) {
        List<McqChoices> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_QUESTION_SQL)) {
            
            setNullableUuid(ps, 1, questionId);
            
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
    public boolean update(McqChoices choice) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
            
            ps.setString(1, choice.getContent());
            ps.setBoolean(2, choice.isIsCorrect());
            setNullableUuid(ps, 3, choice.getMcqQuestionId());
            ps.setString(4, choice.getId().toString());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(UUID choiceId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            
            ps.setString(1, choiceId.toString());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByQuestionId(UUID questionId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_BY_QUESTION_SQL)) {
            
            ps.setString(1, questionId.toString());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

