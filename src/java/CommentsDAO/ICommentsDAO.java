package CommentsDAO;

import java.util.List;
import java.util.UUID;
import model.Comments;

public interface ICommentsDAO {
    List<Comments> findByLessionId(UUID lessionId);
    List<Comments> findByCourseId(UUID courseId);
    boolean insert(Comments c);
    boolean updateContent(UUID commentId, UUID userId, String content, boolean isAdmin);
    boolean delete(UUID commentId, UUID userId, boolean isAdmin);
}


