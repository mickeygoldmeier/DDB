package com.example.ddb.Data;

import java.util.ArrayList;
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
    private static List<User> UsersList = new LinkedList<>();

    public static List<User> getUsersList() {
        return UsersList;
    }

    public static void setUsersList(List<User> usersList) {
        /*for (User user:usersList){
            if (user instanceof Person)
                UsersList.add(user);
        }
        */
        UsersList = usersList;
    }

    public static User getUser(String id) throws Exception {

        for (User user : UsersList)
            if (user.getUserID().equals(id))
                return user;
        throw new Exception();
    }

    public static String[] getStringPhoneList(){
        List<String> list = new ArrayList<String>();
        for (User user:UsersList){
            list.add(user.getUserID());
        }
        return  list.toArray(new String[list.size()]);
    }


}
