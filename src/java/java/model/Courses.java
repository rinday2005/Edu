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
/** Plain POJO (no JPA/JAXB) */
public class Courses {
    private UUID courseID;
    private UUID userID;
    private String name;
    private String description;
    private String imgURL;
    private int rating;
    private int price;
    private String level;
    private boolean isApproved;

    public Courses() {
    }

    public Courses(UUID courseID, UUID userID, String name, String description, String imgURL, int rating, int price, String level, boolean isApproved) {
        this.courseID = courseID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.imgURL = imgURL;
        this.rating = rating;
        this.price = price;
        this.level = level;
        this.isApproved = isApproved;
    }

    public Courses(UUID userID, String name, String description, String imgURL, int rating, int price, String level, boolean isApproved) {
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.imgURL = imgURL;
        this.rating = rating;
        this.price = price;
        this.level = level;
        this.isApproved = isApproved;
    }

    public Courses(String name, String description, String imgURL, int rating, int price, String level, boolean isApproved) {
        this.name = name;
        this.description = description;
        this.imgURL = imgURL;
        this.rating = rating;
        this.price = price;
        this.level = level;
        this.isApproved = isApproved;
    }
    
    public UUID getCourseID() { return courseID; }
    public void setCourseID(UUID courseID) { this.courseID = courseID; }

    public UUID getUserID() { return userID; }
    public void setUserID(UUID userID) { this.userID = userID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImgURL() { return imgURL; }
    public void setImgURL(String imgURL) { this.imgURL = imgURL; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

}
