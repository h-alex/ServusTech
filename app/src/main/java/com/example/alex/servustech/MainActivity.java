package com.example.alex.servustech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.example.alex.servustech.utils.UserDAOImpl;
import com.example.alex.servustech.activities.registerFlow.RegisterActivity;
import com.example.alex.servustech.utils.UserDAO;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* We check if the user is registered or not.
        If he is not, we take him to the register screen. Otherwise, we show the content
         */
        if (userIsRegistered()) {
            startActivity(new Intent(this, RegisterActivity.class));
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
        UserDAO userDAO = new UserDAOImpl(getApplicationContext());
        return userDAO.read() == null;
    }

}
