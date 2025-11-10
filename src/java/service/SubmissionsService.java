// SubmissionsService.java
package service;

import SubmissionsDAO.ISubmissionsDAO;
import SubmissionsDAO.SubmissionsDAO;
import model.Submissions;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SubmissionsService {
    private final ISubmissionsDAO submissionsDAO;

    public SubmissionsService() {
        this.submissionsDAO = new SubmissionsDAO();
    }

    public SubmissionsService(ISubmissionsDAO submissionsDAO) {
        this.submissionsDAO = submissionsDAO;
    }

    public void create(Submissions submission) {
        if (submission == null) {
            throw new IllegalArgumentException("Submission cannot be null");
        }
        submissionsDAO.create(submission);
    }

    public int update(Submissions submission) {
        if (submission == null) {
            throw new IllegalArgumentException("Submission cannot be null");
        }
        return submissionsDAO.update(submission);
    }

    public int deleteById(UUID submissionId) {
        if (submissionId == null) {
            throw new IllegalArgumentException("Submission ID cannot be null");
        }
        return submissionsDAO.deleteById(submissionId);
    }

    public Submissions findById(UUID submissionId) {
        if (submissionId == null) {
            return null;
        }
        return submissionsDAO.findById(submissionId);
    }

    public int updateStatus(UUID userId, boolean status) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return submissionsDAO.updateStatus(userId, status);
    }

    public List<Submissions> findByAssignment(UUID assignmentId) {
        if (assignmentId == null) {
            return java.util.Collections.emptyList();
        }
        return submissionsDAO.findByAssignment(assignmentId);
    }

    public List<Submissions> findByUser(UUID userId) {
        if (userId == null) {
            return java.util.Collections.emptyList();
        }
        return submissionsDAO.findByUser(userId);
    }

    public List<Submissions> findAll() {
        return submissionsDAO.findAll();
    }

    public void saveSubmission(UUID submissionId, UUID userId, UUID assignmentId) throws SQLException {
        if (submissionId == null || userId == null || assignmentId == null) {
            throw new IllegalArgumentException("Submission ID, User ID, and Assignment ID cannot be null");
        }
        submissionsDAO.saveSubmission(submissionId, userId, assignmentId);
    }

    public UUID findByUserAndAssignment(UUID userId, UUID assignmentId) throws SQLException {
        if (userId == null || assignmentId == null) {
            return null;
        }
        return submissionsDAO.findByUserAndAssignment(userId, assignmentId);
    }

    public Map<UUID, UUID> getUserAnswersBySubmission(UUID submissionID) throws SQLException {
        if (submissionID == null) {
            return java.util.Collections.emptyMap();
        }
        return submissionsDAO.getUserAnswersBySubmission(submissionID);
    }
}
