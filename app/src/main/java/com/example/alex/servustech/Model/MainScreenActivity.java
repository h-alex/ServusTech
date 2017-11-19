package com.example.alex.servustech.Model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.alex.servustech.R;

public class MainScreenActivity extends AppCompatActivity {
    private UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        userDAO = new SharedPreferencesAccessor(getApplicationContext());
        showUserCredentials();
    }

    /* We show the user's password and email on the screen */
    private void showUserCredentials() {
        User user = userDAO.read();

        ((TextView) findViewById(R.id.tv_user_email)).setText(user.getEmail());
        ((TextView) findViewById(R.id.tv_user_password)).setText(user.getPassword());
    }

}
