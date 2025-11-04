// McqQuestionsService.java
package service;
import model.McqQuestions;
import model.McqChoices;
import McqQuestionDAO.McqQuestionDAO;
import McqQuestionDAO.IMcqQuestionDAO;
import McqChoiceDAO.McqChoiceDAO;
import McqChoiceDAO.IMcqChoiceDAO;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class McqQuestionsService{
    private IMcqQuestionDAO questionDAO = new McqQuestionDAO();
    private IMcqChoiceDAO choiceDAO = new McqChoiceDAO();
    
    public boolean createQuestion(McqQuestions question, List<McqChoices> choices) {
        try {
            // Tạo question trước
            boolean questionCreated = questionDAO.insert(question);
            if (!questionCreated) {
                return false;
            }
            
            // Tạo các choices
            if (choices != null && !choices.isEmpty()) {
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
        return choiceDAO.findByQuestionId(questionId);
    }
    
    public McqChoices getChoiceById(UUID choiceId) {
        return choiceDAO.findById(choiceId);
    }
}
