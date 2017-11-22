package com.example.alex.servustech.MainScreenFlow;


import com.example.alex.servustech.Model.User;

public interface UserDAO {
    void create (User user);
    User read ();
    void update (User user);
    void delete (User user);
}
