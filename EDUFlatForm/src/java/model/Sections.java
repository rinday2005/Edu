/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.UUID;

public class Sections {
    private UUID sectionID;
    private UUID userID;      // có thể null
    private boolean status;   // BIT
    private String name;
    private String description;
    private UUID courseID;    // có thể null

    public Sections() {}

    public Sections(UUID sectionID, UUID userID, boolean status, String name, String description, UUID courseID) {
        this.sectionID = sectionID;
        this.userID = userID;
        this.status = status;
        this.name = name;
        this.description = description;
        this.courseID = courseID;
    }

    public UUID getSectionID() { return sectionID; }
    public void setSectionID(UUID sectionID) { this.sectionID = sectionID; }

    public UUID getUserID() { return userID; }
    public void setUserID(UUID userID) { this.userID = userID; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getCourseID() { return courseID; }
    public void setCourseID(UUID courseID) { this.courseID = courseID; }
}
