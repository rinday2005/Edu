package McqUserAnswerPKDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.McqUserAnswerPK;

public interface IMcsUserAnswerPKDAO {

      /** Thêm một bản ghi (cặp khóa submissionID + mcqChoiceID). */
    void insert(McqUserAnswerPK key) throws SQLException;

    /** Xóa một bản ghi theo khóa chính. @return true nếu xóa thành công. */
    boolean delete(McqUserAnswerPK key) throws SQLException;

    /** Kiểm tra tồn tại một bản ghi theo khóa chính. */
    boolean exists(McqUserAnswerPK key) throws SQLException;

    /** Tìm một bản ghi theo khóa chính. @return key nếu có, null nếu không. */
    McqUserAnswerPK findById(McqUserAnswerPK key) throws SQLException;

    /** Lấy toàn bộ bản ghi. */
    List<McqUserAnswerPK> findAll() throws SQLException;

    /** Lấy tất cả bản ghi theo submissionId. */
    List<McqUserAnswerPK> findBySubmission(UUID submissionId) throws SQLException;

    /** Lấy tất cả bản ghi theo mcqChoiceId. */
    List<McqUserAnswerPK> findByChoice(UUID mcqChoiceId) throws SQLException;

    /** Cập nhật khóa chính (thay oldKey bằng newKey). */
    boolean updateKey(McqUserAnswerPK oldKey, McqUserAnswerPK newKey) throws SQLException;
}
