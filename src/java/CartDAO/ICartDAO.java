package CartDAO;


import java.util.UUID;


public interface ICartDAO {
    void addToCart(UUID userID, UUID courseID);
    void deleteCourse(UUID userID);
    void deleteCourseByID(UUID courseID);
    int countItemCart(UUID userID);
    boolean isCourseExist(UUID userID, UUID courseID);
    public void moveCoursesFromCartToUserCourse(UUID userID);
}
