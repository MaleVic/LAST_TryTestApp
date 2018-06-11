package com.example.gamesmannna.trytestapp.Model;

/**
 * Created by Gamesmannna on 04.06.2018.
 */

public class Movie {
   private String Name,Image, Price, Discount, MenuID, Description;

    public Movie() {
    }

    public Movie(String name, String image, String price, String discount, String menuID, String description) {
        Name = name;
        Image = image;
        Price = price;
        Discount = discount;
        MenuID = menuID;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }
}
