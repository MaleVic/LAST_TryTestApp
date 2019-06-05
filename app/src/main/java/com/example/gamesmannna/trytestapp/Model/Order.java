package com.example.gamesmannna.trytestapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gamesmannna on 12.06.2018.
 */

public class Order {
    @SerializedName("movieId")
    private String MovieId;
    @SerializedName("movieName")
    private String MovieName;
    @SerializedName("quantity")
    private String Quantity;
    @SerializedName("price")
    private String Price;
    @SerializedName("discount")
    private String Discount;
    @SerializedName("orderID")
    private String OrderId;

    public Order() {
    }

    public Order(String movieId, String movieName, String quantity, String price, String discount) {
        MovieId = movieId;
        MovieName = movieName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
