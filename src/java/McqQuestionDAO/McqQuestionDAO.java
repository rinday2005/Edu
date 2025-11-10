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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import model.McqChoices;
import model.McqQuestions;

/**
 *
 * @author ADMIN
 */
public class McqQuestionDAO implements IMcqQuestionDAO {
    
    private static final String TABLE = "dbo.McqQuestions";
    
    private static final String INSERT_SQL = "INSERT INTO " + TABLE + 
        " ([Id], [Content], [AssignmentId]) " +
        "VALUES (?, ?, ?)";
    
    private static final String SELECT_BY_ID_SQL = "SELECT [Id], [Content], [AssignmentId] " +
        "FROM " + TABLE + " WHERE [Id] = ?";
    
    private static final String SELECT_ALL_SQL = "SELECT [Id], [Content], [AssignmentId] " +
        "FROM " + TABLE + " ORDER BY [Content] ASC";
    
    private static final String SELECT_BY_ASSIGNMENT_SQL = "SELECT [Id], [Content], [AssignmentId] " +
        "FROM " + TABLE + " WHERE [AssignmentId] = ? ORDER BY [Content] ASC";
    
    private static final String UPDATE_SQL = "UPDATE " + TABLE + 
        " SET [Content]=?, [AssignmentId]=? " +
        "WHERE [Id]=?";
    
    private static final String DELETE_SQL = "DELETE FROM " + TABLE + " WHERE [Id] = ?";
    
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
        q.setId(getUuid(rs, "Id"));
        q.setContent(rs.getString("Content"));
        // Try AssignmentId first (as per database), then fallback to other variations
        UUID assignmentId = getUuid(rs, "AssignmentId");
        if (assignmentId == null) {
            assignmentId = getUuid(rs, "assignmentId");
        }
        if (assignmentId == null) {
            assignmentId = getUuid(rs, "assignmentID");
        }
        q.setAssignmentId(assignmentId);
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
    public boolean insertWithConnection(McqQuestions question, Connection con) {
        // Validate connection
        if (con == null) {
            System.err.println("[McqQuestionDAO] insertWithConnection - Connection is NULL!");
            return false;
        }
        
        // Validate connection is not closed
        try {
            if (con.isClosed()) {
                System.err.println("[McqQuestionDAO] insertWithConnection - Connection is CLOSED!");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("[McqQuestionDAO] insertWithConnection - Error checking connection status: " + e.getMessage());
            return false;
        }
        
        try (PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            
            String questionId = question.getId().toString();
            String content = question.getContent();
            String assignmentId = question.getAssignmentId() != null ? question.getAssignmentId().toString() : "NULL";
            
            System.out.println("[McqQuestionDAO] insertWithConnection - Question ID: " + questionId);
            System.out.println("[McqQuestionDAO] insertWithConnection - Content: " + content);
            System.out.println("[McqQuestionDAO] insertWithConnection - Assignment ID: " + assignmentId);
            System.out.println("[McqQuestionDAO] insertWithConnection - SQL: " + INSERT_SQL);
            System.out.println("[McqQuestionDAO] insertWithConnection - Connection valid: " + (con != null && !con.isClosed()));
            
            ps.setString(1, questionId);
            ps.setString(2, content);
            
            // Debug: Check assignmentId before setting
            UUID assignmentIdValue = question.getAssignmentId();
            System.out.println("[McqQuestionDAO] insertWithConnection - Setting AssignmentId parameter: " + assignmentIdValue);
            if (assignmentIdValue == null) {
                System.err.println("[McqQuestionDAO] insertWithConnection - ERROR: AssignmentId is NULL!");
                return false;
            }
            
            setNullableUuid(ps, 3, assignmentIdValue);
            
            // Execute the insert
            System.out.println("[McqQuestionDAO] insertWithConnection - Executing INSERT statement...");
            int result = ps.executeUpdate();
            System.out.println("[McqQuestionDAO] insertWithConnection - ExecuteUpdate result: " + result);
            
            if (result > 0) {
                System.out.println("[McqQuestionDAO] insertWithConnection - Question inserted successfully");
            } else {
                System.err.println("[McqQuestionDAO] insertWithConnection - No rows affected");
            }
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("[McqQuestionDAO] insertWithConnection - SQLException: " + e.getMessage());
            System.err.println("[McqQuestionDAO] insertWithConnection - SQLState: " + e.getSQLState());
            System.err.println("[McqQuestionDAO] insertWithConnection - ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("[McqQuestionDAO] insertWithConnection - Unexpected exception: " + e.getClass().getName());
            System.err.println("[McqQuestionDAO] insertWithConnection - Error message: " + e.getMessage());
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
        System.out.println("[McqQuestionDAO] findByAssignmentId - Starting query");
        System.out.println("[McqQuestionDAO] findByAssignmentId - AssignmentId: " + assignmentId);
        System.out.println("[McqQuestionDAO] findByAssignmentId - AssignmentId string: " + (assignmentId != null ? assignmentId.toString() : "NULL"));
        System.out.println("[McqQuestionDAO] findByAssignmentId - SQL: " + SELECT_BY_ASSIGNMENT_SQL);
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ASSIGNMENT_SQL)) {
            
            // Thử sử dụng setObject thay vì setString
            if (assignmentId != null) {
                ps.setObject(1, assignmentId);
                System.out.println("[McqQuestionDAO] findByAssignmentId - Set parameter using setObject: " + assignmentId);
            } else {
                ps.setNull(1, Types.VARCHAR);
                System.out.println("[McqQuestionDAO] findByAssignmentId - Set parameter to NULL");
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    McqQuestions question = mapRow(rs);
                    list.add(question);
                    count++;
                    System.out.println("[McqQuestionDAO] findByAssignmentId - Found question " + count + ": " + question.getId() + " - " + question.getContent());
                    System.out.println("[McqQuestionDAO] findByAssignmentId - Question AssignmentId: " + question.getAssignmentId());
                }
                System.out.println("[McqQuestionDAO] findByAssignmentId - Total questions found: " + count);
                
                // Nếu không tìm thấy, thử sử dụng truy vấn chuỗi
                if (count == 0 && assignmentId != null) {
                    System.out.println("[McqQuestionDAO] findByAssignmentId - No results with setObject, trying with setString...");
                    try (PreparedStatement ps2 = con.prepareStatement(SELECT_BY_ASSIGNMENT_SQL)) {
                        ps2.setString(1, assignmentId.toString());
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            int count2 = 0;
                            while (rs2.next()) {
                                McqQuestions question = mapRow(rs2);
                                list.add(question);
                                count2++;
                                System.out.println("[McqQuestionDAO] findByAssignmentId - Found question (setString) " + count2 + ": " + question.getId());
                            }
                            System.out.println("[McqQuestionDAO] findByAssignmentId - Total questions found with setString: " + count2);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("[McqQuestionDAO] findByAssignmentId - Error querying questions by assignmentId: " + e.getMessage());
            System.err.println("[McqQuestionDAO] findByAssignmentId - SQLState: " + e.getSQLState());
            System.err.println("[McqQuestionDAO] findByAssignmentId - ErrorCode: " + e.getErrorCode());
            System.err.println("[McqQuestionDAO] findByAssignmentId - AssignmentId: " + assignmentId);
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

    @Override
    public List<McqQuestions> getQuestionsByAssignment(UUID assignmentId) throws SQLException {
        System.out.println("[McqQuestionDAO] getQuestionsByAssignment - Looking for questions with assignmentId: " + assignmentId);
        String sql = """
            SELECT q.[Id] AS QuestionId,
                   q.[Content] AS QuestionContent,
                   c.[Id] AS ChoiceId,
                   c.[Content] AS ChoiceContent,
                   c.[IsCorrect]
            FROM [dbo].[McqQuestions] q
            LEFT JOIN [dbo].[McqChoices] c
              ON q.[Id] = c.[McqQuestionId]
            WHERE q.[AssignmentId] = ?
            ORDER BY q.[Id]
        """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, assignmentId);
            System.out.println("[McqQuestionDAO] getQuestionsByAssignment - Executing SQL with assignmentId: " + assignmentId);
            ResultSet rs = ps.executeQuery();
            Map<UUID, McqQuestions> map = new LinkedHashMap<>();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                UUID qid = UUID.fromString(rs.getString("QuestionId"));
                McqQuestions q = map.get(qid);
                if (q == null) {
                    q = new McqQuestions();
                    q.setId(qid);
                    q.setContent(rs.getString("QuestionContent"));
                    q.setAssignmentId(assignmentId);
                    q.setMcqChoicesCollection(new ArrayList<>());
                    map.put(qid, q);
                    System.out.println("[McqQuestionDAO] getQuestionsByAssignment - Found question: " + qid + " - " + q.getContent());
                }
                String cidStr = rs.getString("ChoiceId");
                if (cidStr != null) {
                    McqChoices c = new McqChoices();
                    c.setId(UUID.fromString(cidStr));
                    c.setContent(rs.getString("ChoiceContent"));
                    c.setIsCorrect(rs.getBoolean("IsCorrect"));
                    c.setMcqQuestionId(qid);
                    q.getMcqChoicesCollection().add(c);
                    System.out.println("[McqQuestionDAO] getQuestionsByAssignment - Added choice: " + cidStr + " to question: " + qid);
                }
            }
            System.out.println("[McqQuestionDAO] getQuestionsByAssignment - Total rows: " + rowCount + ", Questions found: " + map.size());
            List<McqQuestions> result = new ArrayList<>(map.values());
            return result;
        } catch (SQLException e) {
            System.err.println("[McqQuestionDAO] getQuestionsByAssignment - SQL Error: " + e.getMessage());
            System.err.println("[McqQuestionDAO] getQuestionsByAssignment - SQLState: " + e.getSQLState());
            System.err.println("[McqQuestionDAO] getQuestionsByAssignment - ErrorCode: " + e.getErrorCode());
            throw e;
    }
}

    @Override
    public void saveUserAnswers(UUID submissionId, Map<UUID, UUID> userAnswers) throws SQLException {
        String sql = "INSERT INTO McqUserAnswer (SubmissionId, McqChoiceId) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (UUID choiceId : userAnswers.values()) {
                ps.setObject(1, submissionId);
                ps.setObject(2, choiceId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
