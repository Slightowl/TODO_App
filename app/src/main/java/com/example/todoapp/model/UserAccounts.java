package com.example.todoapp.model;

public class UserAccounts {

    private int id;
    private String userName;
    private String password;

    public UserAccounts(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    /* may want to create objects */
    public UserAccounts() {

    }

    public UserAccounts(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /* getter and setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
