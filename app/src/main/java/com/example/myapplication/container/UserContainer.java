package com.example.myapplication.container;

import com.example.myapplication.data.model.LoggedInUser;

public class UserContainer {

    private static LoggedInUser user = new LoggedInUser("1", "EgorBel", false);
    private static LoggedInUser admin = new LoggedInUser("2", "Admin", false);


    public static LoggedInUser getUser(){
        return user;
    }

    public static void setNewDataUser(String name, boolean logged){

        user.setDisplayName(name);
        user.setLogged(logged);
    }

    public static void setNewDataAdmin(String name, boolean logged){

        admin.setDisplayName(name);
        admin.setLogged(logged);
    }

    public static LoggedInUser getAdmin(){
        return admin;
    }
}
