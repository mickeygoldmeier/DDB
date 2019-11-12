package com.example.ddb.Entities;

import java.util.Date;

public class Company extends User {
    private String Name;

    public Company() {
    }

    public Company(String userID, String password, Date lastModified, com.example.ddb.Entities.Address address, String name) {
        super(userID, password, lastModified, address);
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public boolean search(String string) {
        return false;
    }

    @Override
    public boolean generatePassword() {
        return false;
    }
}
