package com.example.gamesmannna.trytestapp.Model;

/**
 * Created by Gamesmannna on 12.06.2018.
 */

public class Order {
    private String MovieId;
    private String MovieName;
    private String Quantity;
    private String Price;
    private String Discount;

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
}
