package com.example.ddb.Data;

import java.util.Calendar;

import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.User;

import java.util.LinkedList;

public class Users {
    private static LinkedList<User> UsersList = new LinkedList<>();
    private RegisteredPackagesDS registeredPackagesDS;

    public static User getUser(String id) throws Exception {
        UsersList.add(new Company("123", "123", Calendar.getInstance(), new Address(), "איציק משלוחים"));
        UsersList.add(new Company("321", "123", Calendar.getInstance(), new Address(), "my company 2"));
        UsersList.add(new Company("1234", "123", Calendar.getInstance(), new Address(), "my company 3"));

        for (User user : UsersList)
            if (user.getUserID().equals(id))
                return user;
        throw new Exception();
    }
}
