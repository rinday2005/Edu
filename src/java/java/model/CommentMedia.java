/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.Date;
import java.util.UUID;

public class CommentMedia {
    private UUID commentMediaID;
    private UUID userID;
    private Date createAt;
    private String contentUrl;
    private UUID commentID;

    public CommentMedia(UUID commentMediaID, UUID userID, Date createAt, String contentUrl, UUID commentID) {
        this.commentMediaID = commentMediaID;
        this.userID = userID;
        this.createAt = createAt;
        this.contentUrl = contentUrl;
        this.commentID = commentID;
    }

    public CommentMedia() {
    }

    public UUID getCommentMediaID() {
        return commentMediaID;
    }

    public void setCommentMediaID(UUID commentMediaID) {
        this.commentMediaID = commentMediaID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public UUID getCommentID() {
        return commentID;
    }

    public void setCommentID(UUID commentID) {
        this.commentID = commentID;
    }

    @Override
    public String toString() {
        return "CommentMedia{" + "commentMediaID=" + commentMediaID + ", userID=" + userID + ", createAt=" + createAt + ", contentUrl=" + contentUrl + ", commentID=" + commentID + '}';
    }

    
}


