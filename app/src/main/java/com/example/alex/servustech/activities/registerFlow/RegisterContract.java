package com.example.alex.servustech.activities.registerFlow;

import com.example.alex.servustech.model.User;
import com.example.alex.servustech.utils.IUserValidator;

interface RegisterContract {
    interface View {
        void showErrors(String errors);
    }

    interface Presenter {
        void addUser(User user, String confirmationPassword);
        void setUserValidator(IUserValidator iUserValidator);
    }
}
