// McqQuestionsService.java
package service;
import model.McqQuestions;
import model.McqChoices;
import McqQuestionDAO.McqQuestionDAO;
import McqQuestionDAO.IMcqQuestionDAO;
import McqChoiceDAO.McqChoiceDAO;
import McqChoiceDAO.IMcqChoiceDAO;
import DAO.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class McqQuestionsService{
    private IMcqQuestionDAO questionDAO = new McqQuestionDAO();
    private IMcqChoiceDAO choiceDAO = new McqChoiceDAO();
    
    public boolean createQuestion(McqQuestions question, List<McqChoices> choices) {
        Connection con = null;
        try {
            System.out.println("[McqQuestionsService] createQuestion - Starting transaction");
            System.out.println("[McqQuestionsService] createQuestion - Question ID: " + question.getId());
            System.out.println("[McqQuestionsService] createQuestion - Question Content: " + question.getContent());
            System.out.println("[McqQuestionsService] createQuestion - Assignment ID: " + question.getAssignmentId());
            System.out.println("[McqQuestionsService] createQuestion - Choices count: " + (choices != null ? choices.size() : 0));
            
            // Lấy connection và bắt đầu transaction
            System.out.println("[McqQuestionsService] createQuestion - Getting database connection...");
            con = DBConnection.getConnection();
            if (con == null) {
                System.err.println("[McqQuestionsService] createQuestion - CRITICAL: Failed to get database connection!");
                System.err.println("[McqQuestionsService] createQuestion - Connection is NULL - cannot proceed");
                return false;
            }
            
            // Verify connection is valid
            try {
                if (con.isClosed()) {
                    System.err.println("[McqQuestionsService] createQuestion - CRITICAL: Connection is already closed!");
                    return false;
                }
                System.out.println("[McqQuestionsService] createQuestion - Connection is valid and open");
            } catch (SQLException e) {
                System.err.println("[McqQuestionsService] createQuestion - CRITICAL: Error checking connection: " + e.getMessage());
                return false;
            }
            
            System.out.println("[McqQuestionsService] createQuestion - Database connection obtained successfully");
            System.out.println("[McqQuestionsService] createQuestion - Connection auto-commit: " + con.getAutoCommit());
            
            // Tắt auto-commit để bắt đầu transaction
            con.setAutoCommit(false);
            System.out.println("[McqQuestionsService] createQuestion - Auto-commit disabled, transaction started");
            
            // Verify question has assignmentId before inserting
            UUID questionAssignmentId = question.getAssignmentId();
            UUID questionId = question.getId();
            
            System.out.println("[McqQuestionsService] createQuestion - Verifying question before insert:");
            System.out.println("  - Question ID: " + questionId);
            System.out.println("  - Assignment ID: " + questionAssignmentId);
            
            if (questionAssignmentId == null) {
                System.err.println("[McqQuestionsService] createQuestion - CRITICAL: question.getAssignmentId() is NULL!");
                System.err.println("[McqQuestionsService] createQuestion - Cannot insert question without AssignmentId");
                con.rollback();
                return false;
            }
            
            if (questionId == null) {
                System.err.println("[McqQuestionsService] createQuestion - CRITICAL: question.getId() is NULL!");
                System.err.println("[McqQuestionsService] createQuestion - Cannot insert question without Id");
                con.rollback();
                return false;
            }
            
            // Tạo question trước
            System.out.println("[McqQuestionsService] createQuestion - Inserting question with ID: " + questionId + ", AssignmentId: " + questionAssignmentId);
            boolean questionCreated = questionDAO.insertWithConnection(question, con);
            if (!questionCreated) {
                System.err.println("[McqQuestionsService] createQuestion - Failed to insert question");
                con.rollback();
                System.err.println("[McqQuestionsService] createQuestion - Transaction rolled back");
                return false;
            }
            System.out.println("[McqQuestionsService] createQuestion - Question inserted successfully");
            
            // Tạo các choices
            if (choices != null && !choices.isEmpty()) {
                System.out.println("[McqQuestionsService] createQuestion - Inserting " + choices.size() + " choices...");
                System.out.println("[McqQuestionsService] createQuestion - Question ID for choices: " + questionId);
                
                for (int i = 0; i < choices.size(); i++) {
                    McqChoices choice = choices.get(i);
                    
                    // Set McqQuestionId BEFORE inserting
                    System.out.println("[McqQuestionsService] createQuestion - Setting McqQuestionId for choice " + (i + 1) + " to: " + questionId);
                    choice.setMcqQuestionId(questionId);
                    
                    // Verify it was set
                    UUID choiceQuestionId = choice.getMcqQuestionId();
                    if (choiceQuestionId == null) {
                        System.err.println("[McqQuestionsService] createQuestion - CRITICAL: choice.getMcqQuestionId() is NULL after setting!");
                        System.err.println("[McqQuestionsService] createQuestion - Choice " + (i + 1) + " cannot be inserted");
                        con.rollback();
                        return false;
                    }
                    
                    System.out.println("[McqQuestionsService] createQuestion - Choice " + (i + 1) + " verified:");
                    System.out.println("  - Choice ID: " + choice.getId());
                    System.out.println("  - Choice Content: " + choice.getContent());
                    System.out.println("  - McqQuestionId: " + choiceQuestionId);
                    
                    System.out.println("[McqQuestionsService] createQuestion - Inserting choice " + (i + 1) + "/" + choices.size());
                    boolean choiceCreated = choiceDAO.insertWithConnection(choice, con);
                    if (!choiceCreated) {
                        System.err.println("[McqQuestionsService] createQuestion - Failed to insert choice " + (i + 1) + ": " + choice.getContent());
                        con.rollback();
                        System.err.println("[McqQuestionsService] createQuestion - Transaction rolled back");
                        return false;
                    }
                    System.out.println("[McqQuestionsService] createQuestion - Choice " + (i + 1) + " inserted successfully");
                }
                System.out.println("[McqQuestionsService] createQuestion - All choices inserted successfully");
            } else {
                System.out.println("[McqQuestionsService] createQuestion - No choices to insert");
            }
            
            // Commit transaction nếu tất cả đều thành công
            System.out.println("[McqQuestionsService] createQuestion - Committing transaction...");
            con.commit();
            System.out.println("[McqQuestionsService] createQuestion - Transaction committed successfully");
            System.out.println("[McqQuestionsService] createQuestion - Successfully created question with " + 
                (choices != null ? choices.size() : 0) + " choices");
            return true;
        } catch (Exception e) {
            System.err.println("[McqQuestionsService] createQuestion - Exception occurred: " + e.getClass().getName());
            System.err.println("[McqQuestionsService] createQuestion - Exception message: " + e.getMessage());
            e.printStackTrace();
            if (con != null) {
                try {
                    System.err.println("[McqQuestionsService] createQuestion - Rolling back transaction...");
                    con.rollback();
                    System.err.println("[McqQuestionsService] createQuestion - Transaction rolled back due to exception");
                } catch (SQLException rollbackEx) {
                    System.err.println("[McqQuestionsService] createQuestion - Failed to rollback: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Reset auto-commit
                    con.close();
                    System.out.println("[McqQuestionsService] createQuestion - Connection closed");
                } catch (SQLException e) {
                    System.err.println("[McqQuestionsService] createQuestion - Error closing connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    
    public boolean updateQuestion(McqQuestions question, List<McqChoices> choices) {
        try {
            // Cập nhật question
            boolean questionUpdated = questionDAO.update(question);
            if (!questionUpdated) {
                return false;
            }
            
            // Xóa tất cả choices cũ và tạo mới
            if (choices != null) {
                choiceDAO.deleteByQuestionId(question.getId());
                for (McqChoices choice : choices) {
                    choice.setMcqQuestionId(question.getId());
                    choiceDAO.insert(choice);
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteQuestion(UUID questionId) {
        try {
            // Xóa choices trước
            choiceDAO.deleteByQuestionId(questionId);
            // Xóa question
            return questionDAO.deleteById(questionId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public McqQuestions getQuestionById(UUID questionId) {
        return questionDAO.findById(questionId);
    }
    
    public List<McqQuestions> getQuestionsByAssignment(UUID assignmentId) {
        return questionDAO.findByAssignmentId(assignmentId);
    }
    
    public List<McqChoices> getChoicesByQuestion(UUID questionId) {
        return choiceDAO.findByQuestionId1(questionId);
    }
    
    public McqChoices getChoiceById(UUID choiceId) {
        return choiceDAO.findById(choiceId);
    }
}
