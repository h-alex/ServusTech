package com.example.alex.servustech.model;

import android.graphics.Bitmap;

/**
 * User class.
 * It contains an email, a password and a Bitmap
 */
public class User {

    private String email;
    private String password;
    private Bitmap image;

    public User() {
        email = "";
        password = "";
        image = null;
    }

    public User(String email, String password, Bitmap image) {
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(String email, String password) {
        this(email, password, null);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
