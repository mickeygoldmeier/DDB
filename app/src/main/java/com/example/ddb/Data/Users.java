package com.example.ddb.Data;

import java.util.Calendar;

import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Person;
import com.example.ddb.Entities.User;

import java.util.LinkedList;
import java.util.List;

public class Users {
    public static List<User> getUsersList() {
        return UsersList;
    }

    public static void setUsersList(List<User> usersList) {
        for (User user:usersList){
            if (user instanceof Person)
                UsersList.add(user);
        }
    }

    private static List<User> UsersList = new LinkedList<>();

}
