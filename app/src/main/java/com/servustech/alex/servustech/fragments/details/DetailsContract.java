package com.servustech.alex.servustech.fragments.details;

import com.servustech.alex.servustech.utils.BaseView;
import com.servustech.alex.servustech.model.User;
import com.servustech.alex.servustech.utils.UserDAO;

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
