/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */

public class LessionMaterialPK implements Serializable {

    private UUID lessionID;
  
    private UUID id;

    public LessionMaterialPK() {
    }

    public LessionMaterialPK(UUID lessionID, UUID id) {
        this.lessionID = lessionID;
        this.id = id;
    }

    public UUID getLessionID() {
        return lessionID;
    }

    public void setLessionID(UUID lessionID) {
        this.lessionID = lessionID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "model.LessionMaterialPK[ lessionID=" + lessionID + ", id=" + id + " ]";
    }
    
}
