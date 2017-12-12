package com.example.alex.servustech.fragments.details;

import com.example.alex.servustech.BaseView;
import com.example.alex.servustech.model.User;
import com.example.alex.servustech.utils.UserDAO;

public interface DetailsContract {
    interface View extends BaseView<Presenter> {
        void showCredentials(User user);
    }

    interface Presenter {
        void getCredentials();
        void setDAO(UserDAO userDAO);
        void setView(DetailsContract.View view);
    }
}
