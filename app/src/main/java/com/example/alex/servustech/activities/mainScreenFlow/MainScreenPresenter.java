package com.example.alex.servustech.activities.mainScreenFlow;

import com.example.alex.servustech.utils.UserDAO;


public class MainScreenPresenter implements MainScreenContract.Presenter {
    private UserDAO mUserDAO;
    private MainScreenContract.View mMainScreenView;

    public MainScreenPresenter(){
    }

    MainScreenPresenter(MainScreenContract.View view, UserDAO userDAO) {
        mUserDAO = userDAO;
        mMainScreenView = view;
    }

    public void setDAO(UserDAO userDAO) {
        mUserDAO = userDAO;
    }

    public void setView(MainScreenContract.View view) {
        mMainScreenView = view;
    }

    @Override
    public void getCredentials() {
        mMainScreenView.showCredentials(mUserDAO.read());
    }
}
