/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.Serializable;
import java.util.Collection;

import java.util.Date;
import java.util.UUID;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userID;
    private String phoneNumber;
    private String email;
    private String avatarUrl;
    private boolean status;
    private String password;
    private String role;
    private String userName;
    private Date createAt;
    private Date lastModifiedAt;
    private String lastModifiedID;
    private String bio;
    private Date dateofbirth;
    private String fullName;
    private String token;
    private String refreshToken;
    private String loginProvider;
    private String providerKey;
    private int enrollmentCount;
    private Collection<Courses> coursesCollection; // If Courses is also not a JPA entity, this might need further adjustment or removal

   public User() {
        this.userID = UUID.randomUUID().toString().toUpperCase();
        this.avatarUrl = "";
        this.token = "";
        this.refreshToken = "";
        this.loginProvider = null;
        this.providerKey = null;
        this.bio = "";
        this.dateofbirth = null;
        this.enrollmentCount = 0;
        this.createAt = new Date();
        this.lastModifiedAt = this.createAt;
        this.role = "Learner";
        this.status = true;
    }

    public User(String userName, String password, String email, String fullName, String phone, String role) {
        this(); // Gọi constructor mặc định
        this.userName = safeString(userName);
        this.password = safeString(password);
        this.email = safeString(email);
        this.fullName=safeString(fullName);
        this.phoneNumber = safeString(phone);
        this.role = mapRole(role);
    }

    // -------------------- HÀM HỖ TRỢ --------------------
    private String safeString(String input) {
        return (input == null) ? "" : input.trim();
    }

    private String mapRole(String role) {
        if (role == null) return "Learner";
        switch (role.trim().toLowerCase()) {
            case "admin":
                return "Admin";
            case "instructor":
                return "Instructor";
            default:
                return "Learner";
        }
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedID() {
        return lastModifiedID;
    }

    public void setLastModifiedID(String lastModifiedID) {
        this.lastModifiedID = lastModifiedID;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getLoginProvider() {
        return loginProvider;
    }

    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public void setProviderKey(String providerKey) {
        this.providerKey = providerKey;
    }

    public int getEnrollmentCount() {
        return enrollmentCount;
    }

    public void setEnrollmentCount(int enrollmentCount) {
        this.enrollmentCount = enrollmentCount;
    }

    public Collection<Courses> getCoursesCollection() {
        return coursesCollection;
    }

    public void setCoursesCollection(Collection<Courses> coursesCollection) {
        this.coursesCollection = coursesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.User[ userID=" + userID + ", fullName=" + fullName + " ]";
    }
    
}
