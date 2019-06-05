package com.example.gamesmannna.trytestapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gamesmannna on 28.05.2018.
 */

public class User {
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("phone")
    private String phone;
    @SerializedName("isStaff")
    private String IsStaff;

    public User() {
    }

    public User(String Pname, String Ppassword, String Pphone) {
        name = Pname;
        password = Ppassword;
        IsStaff = "false";
        phone=Pphone;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String setName(String Pname) {
        name = Pname;
        return name;
    }

    public String getpassword() {
        return password;
    }

    /*public void setPassword(String password) {
        password = Ppassword;
    }*/
}
