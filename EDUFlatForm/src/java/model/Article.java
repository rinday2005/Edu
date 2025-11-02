package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID articleID;
    private UUID userID;
    private Date createAt;        // DB: date
    private String status;        // Draft / Published / Pending Review...
    private String title;
    private String content;

    // Trường thống kê (không bắt buộc tồn tại trong DB)
    private Integer viewCount = 0;
    private Integer commentCount = 0;

    public Article() {}

    public Article(UUID articleID, UUID userID, Date createAt, String status, String title, String content) {
        this.articleID = articleID;
        this.userID = userID;
        this.createAt = createAt;
        this.status = status;
        this.title = title;
        this.content = content;
    }

    public Article(UUID userID, Date createAt, String status, String title, String content) {
        this(null, userID, createAt, status, title, content);
    }

    public Article(UUID articleID, UUID userID, Date createAt, String status, String title, String content,
                   Integer viewCount, Integer commentCount) {
        this(articleID, userID, createAt, status, title, content);
        this.viewCount = viewCount != null ? viewCount : 0;
        this.commentCount = commentCount != null ? commentCount : 0;
    }

    public UUID getArticleID() { return articleID; }
    public void setArticleID(UUID articleID) { this.articleID = articleID; }

    public UUID getUserID() { return userID; }
    public void setUserID(UUID userID) { this.userID = userID; }

    public Date getCreateAt() { return createAt; }
    public void setCreateAt(Date createAt) { this.createAt = createAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount != null ? viewCount : 0; }

    public Integer getCommentCount() { return commentCount; }
    public void setCommentCount(Integer commentCount) { this.commentCount = commentCount != null ? commentCount : 0; }

    @Override
    public String toString() {
        return "Article{" +
                "articleID=" + articleID +
                ", userID=" + userID +
                ", createAt=" + createAt +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", content(len)=" + (content != null ? content.length() : 0) +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(articleID, article.articleID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleID);
    }
}
