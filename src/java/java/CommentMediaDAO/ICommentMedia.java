package CommentMediaDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.CommentMedia;

public interface ICommentMedia {
    void insert(CommentMedia m) throws SQLException;
    void update(CommentMedia m) throws SQLException;
    boolean delete(UUID commentMediaID) throws SQLException;

    CommentMedia findById(UUID commentMediaID) throws SQLException;
    List<CommentMedia> findAll() throws SQLException;

    List<CommentMedia> findByUser(UUID userID) throws SQLException;
    List<CommentMedia> findByComment(UUID commentID) throws SQLException;
}
