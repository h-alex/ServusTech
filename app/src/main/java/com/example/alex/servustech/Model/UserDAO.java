package com.example.alex.servustech.Model;


public interface UserDAO {
    void create (User user);
    User read ();
    void update (User user);
    void delete (User user);
}
