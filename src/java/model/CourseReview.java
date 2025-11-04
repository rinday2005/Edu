/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


/**
 *
 * @author ADMIN
 */

public class CourseReview implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private UUID courseReviewID;

    private Date createAt;
  
    private String comment;

    private UUID courseID;
    
    private UUID userID;

    public CourseReview() {
    }

    public CourseReview(UUID courseReviewID) {
        this.courseReviewID = courseReviewID;
    }

    public UUID getCourseReviewID() {
        return courseReviewID;
    }

    public void setCourseReviewID(UUID courseReviewID) {
        this.courseReviewID = courseReviewID;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseReviewID != null ? courseReviewID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseReview)) {
            return false;
        }
        CourseReview other = (CourseReview) object;
        if ((this.courseReviewID == null && other.courseReviewID != null) || (this.courseReviewID != null && !this.courseReviewID.equals(other.courseReviewID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CourseReview[ courseReviewID=" + courseReviewID + " ]";
    }
    
}
