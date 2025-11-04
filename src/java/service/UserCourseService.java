// UserCourseService.java
package service;

import UserCourseDAO.UserCourseDAO;
import java.util.UUID;

public class UserCourseService {
    UserCourseDAO UCD = new UserCourseDAO();
    
    public boolean isCourseExist(UUID userID, UUID courseID){
        return UCD.isCourseExist(userID, courseID);
    }
}


