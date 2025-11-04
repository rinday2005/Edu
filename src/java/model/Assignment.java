/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.UUID;


/**
 *
 * @author ADMIN
 */
public class Assignment {
    private UUID assignmentID;
    private UUID userID;
    private String name;
    private String description;
    private int order;
    private UUID sectionID;
    private UUID lessionID;

    public Assignment(UUID assignmentID, UUID userID, String name, String description, int order, UUID sectionID) {
        this.assignmentID = assignmentID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.order = order;
        this.sectionID = sectionID;
    }
    
    public Assignment(UUID assignmentID, UUID userID, String name, String description, int order, UUID sectionID, UUID lessionID) {
        this.assignmentID = assignmentID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.order = order;
        this.sectionID = sectionID;
        this.lessionID = lessionID;
    }

    public Assignment() {
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

    public UUID getLessionID() {
        return lessionID;
    }

    public void setLessionID(UUID lessionID) {
        this.lessionID = lessionID;
    }

    @Override
    public String toString() {
        return "Assignment{" + "assignmentID=" + assignmentID + ", userID=" + userID + ", name=" + name + ", description=" + description + ", order=" + order + ", sectionID=" + sectionID + ", lessionID=" + lessionID + '}';
    }

    
}