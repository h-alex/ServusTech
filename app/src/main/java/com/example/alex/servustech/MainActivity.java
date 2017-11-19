package com.example.alex.servustech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.alex.servustech.Model.MainScreenActivity;
import com.example.alex.servustech.Model.SharedPreferencesAccessor;
import com.example.alex.servustech.Model.SignUpActivity;
import com.example.alex.servustech.Model.UserDAO;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* We check if the user is registered or not.
        If he is not, we take him to the register screen. Otherwise, we show the content
         */
        if (userIsRegistered()) {
            startActivity(new Intent(this, SignUpActivity.class));
            finish(); // If the user does not want to sign up, we will take him out of the app
        } else {
            startActivity(new Intent(this, MainScreenActivity.class));
            finish();
        }
    }

    /**
     * @return TRUE if the user is registered, false otherwise
     */
    private boolean userIsRegistered() {
        /* We read the SharedPreference file for an user.
         * If what we get is not null, it means that there is an user
          * in our system, so it will return false
          * If no user is in the system, it will return true
          */
        UserDAO userDAO = new SharedPreferencesAccessor(getApplicationContext());
        return userDAO.read() == null;
    }

}
