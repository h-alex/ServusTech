package com.example.alex.servustech.activities.mainScreenFlow;

import com.example.alex.servustech.BaseView;
import com.example.alex.servustech.model.User;

interface MainScreenContract {
    interface View extends BaseView<Presenter> {
        void showCredentials(User user);
    }

    interface Presenter {
        void getCredentials();
    }
}
