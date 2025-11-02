// src/CourseReviewDAO/ICourseReviewDAO.java
package CourseReviewDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.CourseReview;

public interface ICourseReview {
    void insert(CourseReview r) throws SQLException;
    void update(CourseReview r) throws SQLException;
    boolean delete(UUID courseReviewID) throws SQLException;

    CourseReview findById(UUID courseReviewID) throws SQLException;
    List<CourseReview> findAll() throws SQLException;

    List<CourseReview> findByCourse(UUID courseID) throws SQLException;
    List<CourseReview> findByUser(UUID userID) throws SQLException;

    // tiện ích nhanh (không bắt buộc)
    boolean updateComment(UUID courseReviewID, String newComment) throws SQLException;
}
