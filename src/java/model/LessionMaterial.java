/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */

public class LessionMaterial {
    private UUID lessionID;
    private UUID id;
    private String type;
    private String url;

    // getters & setters
    public UUID getLessionID() { return lessionID; }
    public void setLessionID(UUID lessionID) { this.lessionID = lessionID; }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}