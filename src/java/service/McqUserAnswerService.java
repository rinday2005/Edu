// McqUserAnswerService.java
package service;

import McqUserAnswerDAO.IMcqUserAnswerDAO;
import McqUserAnswerDAO.McqUserAnswerDAO;
import model.McqUserAnswer;
import model.McqUserAnswerPK;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class McqUserAnswerService {
    private final IMcqUserAnswerDAO mcqUserAnswerDAO;

    public McqUserAnswerService() {
        this.mcqUserAnswerDAO = new McqUserAnswerDAO();
    }

    public McqUserAnswerService(IMcqUserAnswerDAO mcqUserAnswerDAO) {
        this.mcqUserAnswerDAO = mcqUserAnswerDAO;
    }

    public void insert(McqUserAnswerPK key) throws SQLException {
        if (key == null) {
            throw new IllegalArgumentException("McqUserAnswerPK cannot be null");
        }
        mcqUserAnswerDAO.insert(key);
    }

    public boolean delete(McqUserAnswerPK key) throws SQLException {
        if (key == null) {
            return false;
        }
        return mcqUserAnswerDAO.delete(key);
    }

    public boolean exists(McqUserAnswerPK key) throws SQLException {
        if (key == null) {
            return false;
        }
        return mcqUserAnswerDAO.exists(key);
    }

    public McqUserAnswer findById(McqUserAnswerPK key) throws SQLException {
        if (key == null) {
            return null;
        }
        return mcqUserAnswerDAO.findById(key);
    }

    public List<McqUserAnswer> findAll() throws SQLException {
        return mcqUserAnswerDAO.findAll();
    }

    public List<McqUserAnswer> findBySubmission(UUID submissionId) throws SQLException {
        if (submissionId == null) {
            return java.util.Collections.emptyList();
        }
        return mcqUserAnswerDAO.findBySubmission(submissionId);
    }

    public List<McqUserAnswer> findByChoice(UUID mcqChoiceId) throws SQLException {
        if (mcqChoiceId == null) {
            return java.util.Collections.emptyList();
        }
        return mcqUserAnswerDAO.findByChoice(mcqChoiceId);
    }

    public boolean updateKey(McqUserAnswerPK oldKey, McqUserAnswerPK newKey) throws SQLException {
        if (oldKey == null || newKey == null) {
            return false;
        }
        return mcqUserAnswerDAO.updateKey(oldKey, newKey);
    }

    public int[] bulkInsert(List<McqUserAnswerPK> keys) throws SQLException {
        if (keys == null || keys.isEmpty()) {
            return new int[0];
        }
        return mcqUserAnswerDAO.bulkInsert(keys);
    }

    public void saveUserAnswers(UUID submissionId, List<UUID> selectedChoiceIds) throws SQLException {
        if (submissionId == null) {
            throw new IllegalArgumentException("Submission ID cannot be null");
        }
        if (selectedChoiceIds == null || selectedChoiceIds.isEmpty()) {
            return;
        }
        mcqUserAnswerDAO.saveUserAnswers(submissionId, selectedChoiceIds);
    }

    public void deleteBySubmissionID(UUID submissionId) throws SQLException {
        if (submissionId == null) {
            return;
        }
        mcqUserAnswerDAO.deleteBySubmissionID(submissionId);
    }

    public List<UUID> findChoicesBySubmissionID(UUID submissionID) throws SQLException {
        if (submissionID == null) {
            return java.util.Collections.emptyList();
        }
        return mcqUserAnswerDAO.findChoicesBySubmissionID(submissionID);
    }
}
