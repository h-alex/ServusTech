package com.example.alex.servustech.activities.mainScreenFlow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.alex.servustech.MainActivity;
import com.example.alex.servustech.model.User;
import com.example.alex.servustech.R;
import com.example.alex.servustech.utils.UserDAO;
import com.example.alex.servustech.utils.UserDAOImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainScreenActivity extends AppCompatActivity implements MainScreenContract.View {
    private MainScreenContract.Presenter mPresenter;

    private Unbinder mUnbinder;

    @BindView(R.id.tv_user_email) TextView mUserEmail;
    @BindView(R.id.tv_user_password) TextView mUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new MainScreenPresenter(this, new UserDAOImpl(getApplicationContext()));
        mPresenter.getCredentials();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mUnbinder.unbind();
    }

    /* TODO DELETE ME!
    * I'm used for testing only! */
    public void deleteMe(View view) {

        UserDAO userDAO = new UserDAOImpl(getApplicationContext());
        userDAO.delete(null);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showCredentials(User user) {
        mUserEmail.setText(user.getEmail());
        mUserPassword.setText(user.getPassword());
    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
