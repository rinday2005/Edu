package model;

import java.util.List;
import java.util.UUID;

public class Assignment {
    private UUID assignmentID;
    private UUID userID;
    private String name;
    private String description;
    private int order;
    private UUID sectionID;

    // 🔹 Danh sách câu hỏi liên quan – không có trong DB, chỉ dùng trong logic Java
    private List<McqQuestions> questions;

    public Assignment() {}

    public Assignment(UUID assignmentID, UUID userID, String name, String description, int order, UUID sectionID) {
        this.assignmentID = assignmentID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.order = order;
        this.sectionID = sectionID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public UUID getSectionID() {
        return sectionID;
    }

    public void setSectionID(UUID sectionID) {
        this.sectionID = sectionID;
    }

    // 🔹 Thêm getter/setter cho danh sách câu hỏi
    public List<McqQuestions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<McqQuestions> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentID=" + assignmentID +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", order=" + order +
                ", sectionID=" + sectionID +
                '}';
    }
}