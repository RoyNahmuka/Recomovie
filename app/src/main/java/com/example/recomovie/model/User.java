package com.example.recomovie.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    Integer id;
    String userName;
    String address;
    String phone;
    String password;

    public User(String userName, String address, String phone, String password) {
        this.userName = userName;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Map<String, Object> toJson() {

        Map<String, Object> json = new HashMap<>();

        json.put("id", id);
        json.put("userName", userName);
        json.put("address", address);
        json.put("phone", phone);
        json.put("password", password);

        return json;

    }
}
