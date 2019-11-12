package com.example.ddb.Entities;

import java.util.Date;

public abstract class User {
    private String UserID;
    private String Password;
    private Date LastModified;
    private Address Address;

    public User() {}

    public User(String userID, String password, Date lastModified, com.example.ddb.Entities.Address address) {
        UserID = userID;
        Password = password;
        LastModified = lastModified;
        Address = address;
    }

    public String getUserID() {return UserID; }
    public void setUserID(String userID) {UserID = userID; }
    public String getPassword() {return Password; }
    public void setPassword(String password) {Password = password; }
    public Date getLastModified() {return LastModified; }
    public void setLastModified(Date lastModified) {LastModified = lastModified;}
    public com.example.ddb.Entities.Address getAddress() {return Address; }
    public void setAddress(com.example.ddb.Entities.Address address) {Address = address;}

    public abstract boolean search(String string);
    public abstract boolean generatePassword();
}
