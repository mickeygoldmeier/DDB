package com.example.ddb.Entities;

import  java.util.Calendar;
public abstract class User {
    private String UserID;
    private String Password;
    private Calendar LastModified;
    private Address Address;

    public User() {}

    public User(String userID, String password, Calendar lastModified, com.example.ddb.Entities.Address address) {
        UserID = userID;
        Password = password;
        LastModified = lastModified;
        Address = address;
    }

    public String getUserID() {return UserID; }
    public void setUserID(String userID) {UserID = userID; }
    public String getPassword() {return Password; }
    public void setPassword(String password) {Password = password; }
    public Calendar getLastModified() {return LastModified; }
    public void setLastModified(Calendar lastModified) {LastModified = lastModified;}
    public com.example.ddb.Entities.Address getAddress() {return Address; }
    public void setAddress(com.example.ddb.Entities.Address address) {Address = address;}

    public abstract boolean search(String string);
    public abstract boolean generatePassword();
}
