/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import AssignmentDAO.AssignmentDAO;
import AssignmentDAO.IAssignment;
import model.Assignment;
import java.util.List;
import java.util.UUID;
import java.sql.SQLException;
import java.util.Map;
/**
 *
 * @author ADMIN
 */
public class AssignmentService {
    private final IAssignment dao;
    private SubmissionsService submissionsService;
    public AssignmentService() {
        this.dao = new AssignmentDAO(); // ✅ class thật
        this.submissionsService = new SubmissionsService();
    }
    public void create(Assignment a) {
        dao.create(a);
    }
    
    public Assignment findById(UUID id) {
        return dao.findById(id);
    }
    
    public List<Assignment> findAll() {
        return dao.findAll();
    }
    
    public List<Assignment> findByUserID(UUID userID) {
        List<Assignment> all = dao.findAll();
        List<Assignment> result = new java.util.ArrayList<>();
        for (Assignment a : all) {
            if (a.getUserID() != null && a.getUserID().equals(userID)) {
                result.add(a);
            }
        }
        return result;
    }
    
    public List<Assignment> findBySectionID(UUID sectionID) {
        List<Assignment> all = dao.findAll();
        List<Assignment> result = new java.util.ArrayList<>();
        for (Assignment a : all) {
            if (a.getSectionID() != null && a.getSectionID().equals(sectionID)) {
                result.add(a);
            }
        }
        return result;
    }
    
    public List<Assignment> findByLessionID(UUID lessionID) {
        return dao.findBySectionsID(lessionID);
    }
    
    public int update(Assignment a) {
        return dao.update(a);
    }
    
    public int deleteById(UUID id) {
        return dao.deleteById(id);
    }
    
    public Assignment getAssignmentWithQuestions(UUID sectionID) throws SQLException {
        return dao.getBySectionId(sectionID);
    }

    public void submitAssignment(UUID userID, UUID assignmentID, List<UUID> selectedChoiceIds, UUID submissionID) throws SQLException {
        submissionsService.saveSubmission(submissionID, userID, assignmentID);
        // Note: insertUserAnswers should be handled by McqUserAnswerService
        // This method may need to be refactored to use McqUserAnswerService
    }

    public Map<UUID, UUID> getUserAnswers(UUID submissionID) throws SQLException {
        return submissionsService.getUserAnswersBySubmission(submissionID);
    }
    public Assignment getByAssignmentId(UUID assignmentID) throws SQLException{
        return dao.getByAssignmentId(assignmentID);
    }
}
