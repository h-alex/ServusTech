package com.example.alex.servustech.activities.mainScreenFlow;

import com.example.alex.servustech.utils.UserDAO;


public class MainScreenPresenter implements MainScreenContract.Presenter {
    private UserDAO mUserDAO;
    private MainScreenContract.View mMainScreenView;

    MainScreenPresenter(MainScreenContract.View view, UserDAO userDAO) {
        mUserDAO = userDAO;
        mMainScreenView = view;
    }

    @Override
    public void getCredentials() {
        mMainScreenView.showCredentials(mUserDAO.read());
    }
}
