package com.example.gamesmannna.trytestapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gamesmannna on 16.06.2018.
 */

public class Request {

    @SerializedName("name")
    private String Name;

    @SerializedName("phone")
    private String Phone;


    @SerializedName("address")
    private String Address;

    @SerializedName("movies")
    private List<Order> Movies;

    @SerializedName("status")
    private String Status;


    @SerializedName("total")
    private String Total;

    public Request() {

        this.Status = "0";
    }

    public Request(String phone, String name, String address, String total,List<Order> movies) {
        this.Phone = phone;
        this.Name = name;
        this.Address = address;
        this.Total = total;
        this.Movies = movies;
        //this.status = status;
    }

    public String getStatus() {
       this.Status = "0";
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        this.Total = total;
    }

    public List<Order> getMovies() {
        return Movies;
    }

    public void setMovies(List<Order> movies) {
        this.Movies = movies;
    }
}
