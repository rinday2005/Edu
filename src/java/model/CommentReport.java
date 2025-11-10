package model;

import java.util.Date;
import java.util.UUID;

public class CommentReport {
    private UUID reportID;
    private UUID commentID;
    private UUID reporterID; // userID của người báo cáo
    private String reason; // Lý do báo cáo (ví dụ: "spam", "inappropriate", v.v.)
    private Date createAt;
    private String status; // "pending", "resolved", "dismissed"

    public CommentReport() {
    }

    public CommentReport(UUID reportID, UUID commentID, UUID reporterID, String reason, Date createAt, String status) {
        this.reportID = reportID;
        this.commentID = commentID;
        this.reporterID = reporterID;
        this.reason = reason;
        this.createAt = createAt;
        this.status = status;
    }

    public UUID getReportID() {
        return reportID;
    }

    public void setReportID(UUID reportID) {
        this.reportID = reportID;
    }

    public UUID getCommentID() {
        return commentID;
    }

    public void setCommentID(UUID commentID) {
        this.commentID = commentID;
    }

    public UUID getReporterID() {
        return reporterID;
    }

    public void setReporterID(UUID reporterID) {
        this.reporterID = reporterID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
}

