// src/McqUserAnswerDAO/IMcqUserAnswerDAO.java
package McqUserAnswerDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.McqUserAnswer;
import model.McqUserAnswerPK;

public interface IMcqUserAnswerDAO {
    void insert(McqUserAnswerPK key) throws SQLException;
    boolean delete(McqUserAnswerPK key) throws SQLException;
    boolean exists(McqUserAnswerPK key) throws SQLException;

    McqUserAnswer findById(McqUserAnswerPK key) throws SQLException;
    List<McqUserAnswer> findAll() throws SQLException;
    List<McqUserAnswer> findBySubmission(UUID submissionId) throws SQLException;
    List<McqUserAnswer> findByChoice(UUID mcqChoiceId) throws SQLException;

    // Tuỳ chọn: “đổi khóa” (xóa key cũ, thêm key mới)
    boolean updateKey(McqUserAnswerPK oldKey, McqUserAnswerPK newKey) throws SQLException;

    // Tuỳ chọn: insert nhiều dòng một lần
    int[] bulkInsert(List<McqUserAnswerPK> keys) throws SQLException;
    
    void saveUserAnswers(UUID submissionId, List<UUID> selectedChoiceIds) throws SQLException ;
    void deleteBySubmissionID(UUID submissionId) throws SQLException;
    List<UUID> findChoicesBySubmissionID(UUID submissionID)throws SQLException;
}
