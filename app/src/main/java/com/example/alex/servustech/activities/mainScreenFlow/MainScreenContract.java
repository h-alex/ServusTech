package com.example.alex.servustech.activities.mainScreenFlow;

import com.example.alex.servustech.BaseView;
import com.example.alex.servustech.model.User;
import com.example.alex.servustech.utils.UserDAO;

public interface MainScreenContract {
    interface View extends BaseView<Presenter> {
        void showCredentials(User user);
    }

    interface Presenter {
        void getCredentials();
        void setDAO(UserDAO userDAO);
        void setView(MainScreenContract.View view);
    }
}
