/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import java.util.Date;
import java.util.UUID;

public class Article {

    private UUID articleID;
    private UUID userID;
    private Date createAt;
    private String status;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer commentCount;

    public Article(UUID articleID, UUID userID, Date createAt, String status, String title, String content) {
        this.articleID = articleID;
        this.userID = userID;
        this.createAt = createAt;
        this.status = status;
        this.title = title;
        this.content = content;
    }

    public Article(UUID userID, Date createAt, String status, String title, String content) {
        this.userID = userID;
        this.createAt = createAt;
        this.status = status;
        this.title = title;
        this.content = content;
    }

    public Article(UUID articleID, UUID userID, Date createAt, String status, String title, String content, Integer viewCount, Integer commentCount) {
        this.articleID = articleID;
        this.userID = userID;
        this.createAt = createAt;
        this.status = status;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
    }

    public Article() {
    }

    public UUID getArticleID() {
        return articleID;
    }

    public void setArticleID(UUID articleID) {
        this.articleID = articleID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "Article{" + "articleID=" + articleID + ", userID=" + userID + ", createAt=" + createAt + ", status=" + status + ", title=" + title + ", content=" + content + ", viewCount=" + viewCount + ", commentCount=" + commentCount + '}';
    }

   

}
