package com.example.alex.servustech.utils;


import com.example.alex.servustech.model.User;

public interface UserDAO {
    void create (User user);
    User read ();
    void update (User user);
    void delete (User user);
}
