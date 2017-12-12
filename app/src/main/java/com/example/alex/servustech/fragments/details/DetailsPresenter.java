package com.example.alex.servustech.fragments.details;

import com.example.alex.servustech.utils.UserDAO;


public class DetailsPresenter implements DetailsContract.Presenter {
    private UserDAO mUserDAO;
    private DetailsContract.View mMainScreenView;

    public DetailsPresenter(){
    }

    DetailsPresenter(DetailsContract.View view, UserDAO userDAO) {
        mUserDAO = userDAO;
        mMainScreenView = view;
    }

    public void setDAO(UserDAO userDAO) {
        mUserDAO = userDAO;
    }

    public void setView(DetailsContract.View view) {
        mMainScreenView = view;
    }

    @Override
    public void getCredentials() {
        mMainScreenView.showCredentials(mUserDAO.read());
    }
}
