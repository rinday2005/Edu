package service;

import CommentDAO.Commentdao;
import CommentDAO.ICommentDAO;
import java.util.List;
import java.util.UUID;
import model.Comments;
import model.CommentMedia;

/** Service mỏng: chỉ ủy quyền sang DAO. */
public class CommentService {

    private final ICommentDAO dao;

    public CommentService() {
        this.dao = new Commentdao();              // dùng DAO thật
    }
    public CommentService(ICommentDAO dao) {      // tiện cho unit test/mock
        this.dao = dao;
    }

    // ===== Comment =====
   
    public void createComment(Comments c)                  { dao.create(c); }
    
    public Comments getCommentById(UUID commentID)         { return dao.findById(commentID); }
    
    public void updateComment(Comments c)                  { dao.update(c); }
    
    public void deleteComment(UUID commentID)              { dao.delete(commentID); }
    
    public void softDeleteComment(UUID commentID)          { dao.softDelete(commentID); }
    
    public void updateStatus(UUID commentID, String s)     { dao.updateStatus(commentID, s); }
   
    public List<Comments> listCommentsByArticle(UUID aId, int offset, int limit) {
        return dao.listByArticle(aId, offset, limit);
    }

    // ===== CommentMedia =====
    
    public void createMedia(CommentMedia m)                { dao.createMedia(m); }
    
    public CommentMedia getMediaById(UUID mediaID)         { return dao.findMediaById(mediaID); }
    
    public void updateMedia(CommentMedia m)                { dao.updateMedia(m); }
   
    public void deleteMedia(UUID mediaID)                  { dao.deleteMedia(mediaID); }

    
    public List<CommentMedia> listMediaByInstructor(UUID instructorID, int offset, int limit) {
        return dao.listByInstructor(instructorID, offset, limit);
    }

    public int countMediaByInstructor(UUID instructorID)   { return dao.countByInstructor(instructorID); }
}
