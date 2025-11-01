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

public class Submissions implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID submissionID;

    private Date createAt;
  
    private String status;

    private UUID assignmentID;
 
    private UUID userID;

    public Submissions() {
    }

    public Submissions(UUID submissionID, Date createAt, String status, UUID assignmentID, UUID userID) {
        this.submissionID = submissionID;
        this.createAt = createAt;
        this.status = status;
        this.assignmentID = assignmentID;
        this.userID = userID;
    }

    public UUID getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(UUID submissionID) {
        this.submissionID = submissionID;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(UUID assignmentID) {
        this.assignmentID = assignmentID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    
}
