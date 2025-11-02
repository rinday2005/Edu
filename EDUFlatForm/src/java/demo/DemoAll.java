package demo;

import CommentDAO.Commentdao;
// Nếu interface của bạn tên là IComment (không phải ICommentDAO) thì đổi dòng dưới:
import CommentDAO.ICommentDAO;

import model.Comments;
import model.CommentMedia;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DemoAll {

    // ĐỔI 3 ID NÀY THÀNH ID THỰC TẾ CÓ TRONG DB CỦA BẠN
    private static final UUID INSTRUCTOR_ID = UUID.fromString("2EF10307-878E-48D1-A86C-CDE7CC55D418");
    private static final UUID USER_ID       = UUID.fromString("DB2E4276-1051-4786-9E4F-8F27934BE642");
    private static final UUID ARTICLE_ID    = UUID.fromString("2114AEBE-BC17-4C1B-92B7-164EFAF9A616");

    public static void main(String[] args) {
        // Nếu class dao không implements đúng interface -> kiểm tra lại tên interface và import
        ICommentDAO dao = new Commentdao();

        // 1) Tạo comment mới (dùng setter để tránh lỗi constructor)
        UUID cmtId = UUID.randomUUID();
        Comments c = new Comments();
        c.setCommentID(cmtId);
        c.setUserID(USER_ID);
        c.setCreateAt(new Date());
        c.setParentID(null);
        c.setContent("This is a demo comment");
        c.setArticleID(ARTICLE_ID);
        c.setLessionID(null);
        // Nếu bảng có cột status và bạn có setter, có thể đặt mặc định:
        // c.setStatus("Pending");

        dao.create(c);
        log("Created comment: " + cmtId);

        // 2) Lấy lại comment theo ID
        Comments found = dao.findById(cmtId);
        log("FindById -> " + (found != null ? found.toString() : "null"));

        // 3) Cập nhật nội dung comment
        if (found != null) {
            found.setContent("Updated content from test main");
            dao.update(found);
            log("Updated comment content");
        }

        // 4) Đổi trạng thái (duyệt)
        dao.updateStatus(cmtId, "Approved");
        log("Set status = Approved");

        // 5) Tạo media cho comment
        UUID mediaId = UUID.randomUUID();
        CommentMedia m = new CommentMedia(
                mediaId,
                USER_ID,
                new Date(),
                "https://example.com/image.png",
                cmtId
        );
        // Nếu interface của bạn có các hàm media sau thì dùng, nếu không hãy bỏ/đổi cho khớp interface thực tế
        dao.createMedia(m);
        log("Created media: " + mediaId);

        // 6) Đọc media theo ID
        CommentMedia mFound = dao.findMediaById(mediaId);
        log("FindMediaById -> " + (mFound != null ? mFound.toString() : "null"));

        // 7) Sửa media
        if (mFound != null) {
            mFound.setContentUrl("https://example.com/updated.png");
            dao.updateMedia(mFound);
            log("Updated media url");
        }

        // 8) Liệt kê comment theo bài viết (phân trang)
        List<Comments> pageComments = dao.listByArticle(ARTICLE_ID, 0, 10);
        log("listByArticle size=" + pageComments.size());
        for (Comments it : pageComments) {
            log(" - " + it.getCommentID() + " | " + it.getContent());
        }

        // 9) Liệt kê media theo instructor + tổng số (phân trang)
        List<CommentMedia> listMedia = dao.listByInstructor(INSTRUCTOR_ID, 0, 10);
        int totalMedia = dao.countByInstructor(INSTRUCTOR_ID);
        log("listByInstructor size=" + listMedia.size() + ", total=" + totalMedia);
        for (CommentMedia it : listMedia) {
            log(" - mediaID=" + it.getCommentMediaID() + " commentID=" + it.getCommentID());
        }

        // 10) "Soft delete" comment: đổi status thành Deleted
        dao.updateStatus(cmtId, "Deleted");
        log("Soft-deleted (status=Deleted) comment " + cmtId);

        // 11) Xoá cứng media (nếu muốn)
        dao.deleteMedia(mediaId);
        log("Hard-deleted media " + mediaId);

        // 12) Xoá cứng comment (nếu muốn)
        dao.delete(cmtId);
        log("Hard-deleted comment " + cmtId);

        log("=== DONE ===");
    }

    private static void log(String s) {
        System.out.println("[TEST] " + s);
    }
}
