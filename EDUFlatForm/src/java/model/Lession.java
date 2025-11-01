/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.UUID;

public class Lession {
    private UUID lessionID;      // theo tên cột trong DB của bạn
    private UUID userID;
    private String name;
    private String description;
    private String videoUrl;
    private UUID sectionID;
    private int videoDuration;   // giây
    private boolean status;

    public UUID getLessionID() { return lessionID; }
    public void setLessionID(UUID lessionID) { this.lessionID = lessionID; }
    public UUID getUserID() { return userID; }
    public void setUserID(UUID userID) { this.userID = userID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public UUID getSectionID() { return sectionID; }
    public void setSectionID(UUID sectionID) { this.sectionID = sectionID; }
    public int getVideoDuration() { return videoDuration; }
    public void setVideoDuration(int videoDuration) { this.videoDuration = videoDuration; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}