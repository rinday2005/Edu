/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package McqQuestionDAO;

import DAO.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.McqQuestions;

/**
 *
 * @author ADMIN
 */
public class McqQuestionDAO implements IMcqQuestionDAO {
    
    private static final String TABLE = "dbo.McqQuestions";
    
    private static final String INSERT_SQL = "INSERT INTO " + TABLE + 
        " (id, content, assignmentId) " +
        "VALUES (?, ?, ?)";
    
    private static final String SELECT_BY_ID_SQL = "SELECT id, content, assignmentId " +
        "FROM " + TABLE + " WHERE id = ?";
    
    private static final String SELECT_ALL_SQL = "SELECT id, content, assignmentId " +
        "FROM " + TABLE + " ORDER BY content ASC";
    
    private static final String SELECT_BY_ASSIGNMENT_SQL = "SELECT id, content, assignmentId " +
        "FROM " + TABLE + " WHERE assignmentId = ? ORDER BY content ASC";
    
    private static final String UPDATE_SQL = "UPDATE " + TABLE + 
        " SET content=?, assignmentId=? " +
        "WHERE id=?";
    
    private static final String DELETE_SQL = "DELETE FROM " + TABLE + " WHERE id = ?";
    
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
    
    private McqQuestions mapRow(ResultSet rs) throws SQLException {
        McqQuestions q = new McqQuestions();
        q.setId(getUuid(rs, "id"));
        q.setContent(rs.getString("content"));
        q.setAssignmentId(getUuid(rs, "assignmentId"));
        return q;
    }

    @Override
    public boolean insert(McqQuestions question) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            
            ps.setString(1, question.getId().toString());
            ps.setString(2, question.getContent());
            setNullableUuid(ps, 3, question.getAssignmentId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public McqQuestions findById(UUID questionId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {
            
            ps.setString(1, questionId.toString());
            
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
    public List<McqQuestions> findAll() {
        List<McqQuestions> list = new ArrayList<>();
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
    public List<McqQuestions> findByAssignmentId(UUID assignmentId) {
        List<McqQuestions> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ASSIGNMENT_SQL)) {
            
            setNullableUuid(ps, 1, assignmentId);
            
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
    public boolean update(McqQuestions question) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
            
            ps.setString(1, question.getContent());
            setNullableUuid(ps, 2, question.getAssignmentId());
            ps.setString(3, question.getId().toString());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(UUID questionId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            
            ps.setString(1, questionId.toString());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
