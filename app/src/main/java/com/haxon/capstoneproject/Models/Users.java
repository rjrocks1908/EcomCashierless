package com.haxon.capstoneproject.Models;

//this class contains the users data and used for to take the data from database and get it for use
public class Users {

    private String name, phone, password;
    public Users(){

    }

    public Users(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
