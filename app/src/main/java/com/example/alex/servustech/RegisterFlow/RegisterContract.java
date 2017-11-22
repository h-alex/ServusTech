package com.example.alex.servustech.RegisterFlow;

/**
 * Created by alexch on 22.11.2017.
 */

interface RegisterContract {
    interface View {
        public void showErrors();
    }

    interface Presenter {
        public void addUser();
    }
}
