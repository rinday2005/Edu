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
        " ([Id], [Content], [IsCorrect], [McqQuestionId]) " +
        "VALUES (?, ?, ?, ?)";
    
    private static final String SELECT_BY_ID_SQL = "SELECT [Id], [Content], [IsCorrect], [McqQuestionId] " +
        "FROM " + TABLE + " WHERE [Id] = ?";
    
    private static final String SELECT_ALL_SQL = "SELECT [Id], [Content], [IsCorrect], [McqQuestionId] " +
        "FROM " + TABLE + " ORDER BY [Content] ASC";
    
    private static final String SELECT_BY_QUESTION_SQL = "SELECT [Id], [Content], [IsCorrect], [McqQuestionId] " +
        "FROM " + TABLE + " WHERE [McqQuestionId] = ? ORDER BY [Content] ASC";
    
    private static final String UPDATE_SQL = "UPDATE " + TABLE + 
        " SET [Content]=?, [IsCorrect]=?, [McqQuestionId]=? " +
        "WHERE [Id]=?";
    
    private static final String DELETE_SQL = "DELETE FROM " + TABLE + " WHERE [Id] = ?";
    
    private static final String DELETE_BY_QUESTION_SQL = "DELETE FROM " + TABLE + " WHERE [McqQuestionId] = ?";
    
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
        c.setId(getUuid(rs, "Id"));
        c.setContent(rs.getString("Content"));
        c.setIsCorrect(rs.getBoolean("IsCorrect"));
        // Try McqQuestionId first (as per database), then fallback to other variations
        UUID questionId = getUuid(rs, "McqQuestionId");
        if (questionId == null) {
            questionId = getUuid(rs, "mcqQuestionId");
        }
        if (questionId == null) {
            questionId = getUuid(rs, "mcqQuestionID");
        }
        c.setMcqQuestionId(questionId);
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
    public boolean insertWithConnection(McqChoices choice, Connection con) {
        // Validate connection
        if (con == null) {
            System.err.println("[McqChoiceDAO] insertWithConnection - Connection is NULL!");
            return false;
        }
        
        // Validate connection is not closed
        try {
            if (con.isClosed()) {
                System.err.println("[McqChoiceDAO] insertWithConnection - Connection is CLOSED!");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("[McqChoiceDAO] insertWithConnection - Error checking connection status: " + e.getMessage());
            return false;
        }
        
        try (PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            
            String choiceId = choice.getId().toString();
            String content = choice.getContent();
            boolean isCorrect = choice.isIsCorrect();
            String questionId = choice.getMcqQuestionId() != null ? choice.getMcqQuestionId().toString() : "NULL";
            
            System.out.println("[McqChoiceDAO] insertWithConnection - Choice ID: " + choiceId);
            System.out.println("[McqChoiceDAO] insertWithConnection - Content: " + content);
            System.out.println("[McqChoiceDAO] insertWithConnection - IsCorrect: " + isCorrect);
            System.out.println("[McqChoiceDAO] insertWithConnection - Question ID: " + questionId);
            System.out.println("[McqChoiceDAO] insertWithConnection - SQL: " + INSERT_SQL);
            System.out.println("[McqChoiceDAO] insertWithConnection - Connection valid: " + (con != null && !con.isClosed()));
            
            ps.setString(1, choiceId);
            ps.setString(2, content);
            ps.setBoolean(3, isCorrect);
            
            // Debug: Check mcqQuestionId before setting
            UUID questionIdValue = choice.getMcqQuestionId();
            System.out.println("[McqChoiceDAO] insertWithConnection - Setting McqQuestionId parameter: " + questionIdValue);
            if (questionIdValue == null) {
                System.err.println("[McqChoiceDAO] insertWithConnection - ERROR: McqQuestionId is NULL!");
                return false;
            }
            
            setNullableUuid(ps, 4, questionIdValue);
            
            // Execute the insert
            System.out.println("[McqChoiceDAO] insertWithConnection - Executing INSERT statement...");
            int result = ps.executeUpdate();
            System.out.println("[McqChoiceDAO] insertWithConnection - ExecuteUpdate result: " + result);
            
            if (result > 0) {
                System.out.println("[McqChoiceDAO] insertWithConnection - Choice inserted successfully");
            } else {
                System.err.println("[McqChoiceDAO] insertWithConnection - No rows affected");
            }
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("[McqChoiceDAO] insertWithConnection - SQLException: " + e.getMessage());
            System.err.println("[McqChoiceDAO] insertWithConnection - SQLState: " + e.getSQLState());
            System.err.println("[McqChoiceDAO] insertWithConnection - ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("[McqChoiceDAO] insertWithConnection - Unexpected exception: " + e.getClass().getName());
            System.err.println("[McqChoiceDAO] insertWithConnection - Error message: " + e.getMessage());
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

