package com.example.alex.servustech.activities.registerFlow;

import com.example.alex.servustech.exceptions.InvalidCredentials;
import com.example.alex.servustech.model.User;
import com.example.alex.servustech.utils.IUserValidator;
import com.example.alex.servustech.utils.UserDAO;

public class RegisterPresenter implements RegisterContract.Presenter {

    private UserDAO mUserDAO;
    private IUserValidator mValidator;
    private RegisterContract.View mRegisterView;


    RegisterPresenter(UserDAO userDAO, RegisterContract.View view) {
        mUserDAO = userDAO;
        mRegisterView = view;
        mValidator = null;
    }

    @Override
    public void setUserValidator(IUserValidator userValidator) {
        mValidator = userValidator;
    }

    @Override
    public void addUser(User user, String confirmationPassword) {
        if (mValidator != null) {
            try {
                mValidator.validate(user, confirmationPassword);
            } catch (InvalidCredentials ic) {
                mRegisterView.showErrors(ic.getMessage());
            }
            mUserDAO.create(user);
        }
    }

}
