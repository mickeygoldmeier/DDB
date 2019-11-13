package com.example.ddb.Data;

import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.User;

import java.util.Date;
import java.util.LinkedList;

public class Users {
    private static LinkedList<User> UsersList = new LinkedList<>();

    public static User getUser(String id) throws Exception {
        UsersList.add(new Company("123", "123", new Date(), new Address(), "my company"));
        UsersList.add(new Company("321", "123", new Date(), new Address(), "my company 2"));
        UsersList.add(new Company("1234", "123", new Date(), new Address(), "my company 3"));

        for (User user : UsersList)
            if (user.getUserID().equals(id))
                return user;
        throw new Exception();
    }
}
