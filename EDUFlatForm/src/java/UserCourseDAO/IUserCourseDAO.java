package UserCourseDAO;

import java.util.*;
import model.UserCourse;

public interface IUserCourseDAO {
    void create(UserCourse uc);
    int  deleteById(UUID userCourseId);
    int  update(UserCourse uc);
    UserCourse findById(UUID userCourseId);
    List<UserCourse> findByUser(UUID userId);
    List<UserCourse> findByCourse(UUID courseId);
    List<UserCourse> findAll();
}
