package com.example.alex.servustech.fragments.recycleView;

import com.example.alex.servustech.BaseView;
import com.example.alex.servustech.model.Category;

import java.util.List;


interface RecycleViewContract {
    interface View extends BaseView<Presenter> {
        void onSuccess(List<Category> results);

        void onFailure(OnFailure failure);
    }

    interface Presenter {
        void requestResults();
    }

    enum OnFailure {
        NO_INTERNET_CONNECTION,
        LIST_EMPTY
    }
}
