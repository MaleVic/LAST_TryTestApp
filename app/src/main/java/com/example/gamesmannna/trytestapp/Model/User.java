package com.example.gamesmannna.trytestapp.Model;

/**
 * Created by Gamesmannna on 28.05.2018.
 */

public class User {
    private String name;
    private String password;
    private String phone;

    public User() {
    }

    public User(String Pname, String Ppassword, String Pphone) {
        name = Pname;
        password = Ppassword;

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
