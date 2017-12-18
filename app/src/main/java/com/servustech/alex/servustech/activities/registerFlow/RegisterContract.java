package com.servustech.alex.servustech.activities.registerFlow;

import com.servustech.alex.servustech.model.User;
import com.servustech.alex.servustech.utils.IUserValidator;

interface RegisterContract {
    interface View {
        void showErrors(String errors);
    }

    interface Presenter {
        void addUser(User user, String confirmationPassword);
        void setUserValidator(IUserValidator iUserValidator);
    }
}
