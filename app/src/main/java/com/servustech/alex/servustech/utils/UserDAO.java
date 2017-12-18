package com.servustech.alex.servustech.utils;


import com.servustech.alex.servustech.model.User;

public interface UserDAO {
    void create (User user);
    User read ();
    void update (User user);
    void delete (User user);
}
