package com.servustech.alex.servustech.fragments.recycleView;

import com.servustech.alex.servustech.utils.BaseView;
import com.servustech.alex.servustech.model.category.Category;

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
