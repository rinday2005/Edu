/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.UUID;


/**
 *
 * @author ADMIN
 */

public class UserCourse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private UUID userCourseID;
   
    private UUID courseID;
  
    private UUID userID;

    public UserCourse() {
    }

    public UserCourse(UUID userCourseID, UUID courseID, UUID userID) {
        this.userCourseID = userCourseID;
        this.courseID = courseID;
        this.userID = userID;
    }

    public UUID getUserCourseID() {
        return userCourseID;
    }

    public void setUserCourseID(UUID userCourseID) {
        this.userCourseID = userCourseID;
    }

    public UUID getCourseID() {
        return courseID;
    }

    public void setCourseID(UUID courseID) {
        this.courseID = courseID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    
}
