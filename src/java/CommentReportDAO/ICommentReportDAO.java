package CommentReportDAO;

import model.CommentReport;
import java.util.List;
import java.util.UUID;

public interface ICommentReportDAO {
    boolean insert(CommentReport report);
    List<CommentReport> findByCommentId(UUID commentID);
    List<CommentReport> findAllPending();
    List<CommentReport> findAll();
    boolean updateStatus(UUID reportID, String status);
    boolean delete(UUID reportID);
    boolean exists(UUID commentID, UUID reporterID); // Kiểm tra xem người dùng đã báo cáo bình luận này chưa
}

