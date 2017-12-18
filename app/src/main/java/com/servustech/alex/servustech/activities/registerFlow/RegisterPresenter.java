package com.servustech.alex.servustech.activities.registerFlow;

import com.servustech.alex.servustech.exceptions.InvalidCredentials;
import com.servustech.alex.servustech.model.User;
import com.servustech.alex.servustech.utils.IUserValidator;
import com.servustech.alex.servustech.utils.UserDAO;

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
                return;
            }
            mUserDAO.create(user);
        }
    }

}
