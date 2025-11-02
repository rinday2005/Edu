/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CommentDAO;

import java.util.List;
import java.util.UUID;
import model.*;

/**
 *
 * @author ADMIN
 */
public interface ICommentDAO {
   // COMMENTS (CRUD)
    void create(Comments c);
    Comments findById(UUID commentID);
    void update(Comments c);
    void delete(UUID commentID);              // hard delete
    void softDelete(UUID commentID);          // status='Deleted'

    // BỔ SUNG
    void updateStatus(UUID commentID, String newStatus);
    List<Comments> listByArticle(UUID articleID, int offset, int limit);

    // THEO INSTRUCTOR (yêu cầu)
    List<CommentMedia> listByInstructor(UUID instructorID, int offset, int limit);
    int countByInstructor(UUID instructorID);

    // COMMENT MEDIA (CRUD)
    void createMedia(CommentMedia m);
    CommentMedia findMediaById(UUID commentMediaID);
    void updateMedia(CommentMedia m);
    void deleteMedia(UUID commentMediaID);
}
