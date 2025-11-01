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

public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID cartID;
    
    private UUID courseID;

    private UUID userID;

    public Cart() {
    }

    public Cart(UUID cartID) {
        this.cartID = cartID;
    }

    public UUID getCartID() {
        return cartID;
    }

    public void setCartID(UUID cartID) {
        this.cartID = cartID;
    }

    public UUID getCourseID() {
        return courseID;
    }

    public void setCourseID(UUID courseID) {
        this.courseID = courseID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartID != null ? cartID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cartID == null && other.cartID != null) || (this.cartID != null && !this.cartID.equals(other.cartID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cart[ cartID=" + cartID + " ]";
    }
    
}
