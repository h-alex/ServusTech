package com.servustech.alex.servustech.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.servustech.alex.servustech.model.User;

public class UserDAOImpl implements UserDAO {
    private SharedPreferences sharedPreferences;

    public UserDAOImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences("register", Context.MODE_PRIVATE);
    }

    /**
     * Adds the user to the SharedPreference file.
     * If there already is an user, it will overwrite him.
     *
     * @param user - the user to be added
     */
    @Override
    public void create(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", user.getEmail());
        editor.putString("pass", user.getPassword());

        editor.commit();
    }

    /**
     * Reads the SharedPreferences file.
     * If there is an email and a password, it will return them as an User object
     * Otherwise, it will return null
     *
     * @return
     */
    @Override
    public User read() {
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("pass", "");
        if ("".equals(email) && "".equals(password)) {
            return null;
        }
        return new User(email, password);
    }

    /**
     * Not implemented
     *
     * @param user
     */
    @Override
    public void update(User user) {
    }

    /**
     * Not implemented
     *
     * @param user
     */
    @Override
    public void delete(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("pass");

        editor.commit();
    }
}
