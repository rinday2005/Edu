/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;
import java.util.UUID;

public class Comments {
    private UUID commentID;
    private UUID userID;
    private Date createAt;
    private UUID parentID;
    private String content;
    private UUID articleID;
    private UUID lessionID;
    private String status;

    public Comments(UUID commentID, UUID userID, Date createAt, UUID parentID, String content, UUID articleID, UUID lessionID, String status) {
        this.commentID = commentID;
        this.userID = userID;
        this.createAt = createAt;
        this.parentID = parentID;
        this.content = content;
        this.articleID = articleID;
        this.lessionID = lessionID;
        this.status = status;
    }

    

    public Comments() {
    }

    public UUID getCommentID() {
        return commentID;
    }

    public void setCommentID(UUID commentID) {
        this.commentID = commentID;
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

    public UUID getParentID() {
        return parentID;
    }

    public void setParentID(UUID parentID) {
        this.parentID = parentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getArticleID() {
        return articleID;
    }

    public void setArticleID(UUID articleID) {
        this.articleID = articleID;
    }

    public UUID getLessionID() {
        return lessionID;
    }

    public void setLessionID(UUID lessionID) {
        this.lessionID = lessionID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Comments{" + "commentID=" + commentID + ", userID=" + userID + ", createAt=" + createAt + ", parentID=" + parentID + ", content=" + content + ", articleID=" + articleID + ", lessionID=" + lessionID + ", status=" + status + '}';
    }

    

    
}
