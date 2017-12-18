package com.servustech.alex.servustech.fragments.recycleView;

import com.servustech.alex.servustech.model.category.Category;
import com.servustech.alex.servustech.model.ServerResponse;
import com.servustech.alex.servustech.model.FoursquareResponse;
import com.servustech.alex.servustech.model.category.CategoryClient;
import com.servustech.alex.servustech.utils.RetrofitConnector;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecycleViewPresenter implements RecycleViewContract.Presenter {
    private static final String TAG = "RecycleViewPresenter";
    RecycleViewContract.View mView;
    private List<Category> mCategories;

    RecycleViewPresenter(RecycleViewContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void requestResults() {
        CategoryClient client = RetrofitConnector.getInstance()
                .getRetrofit()
                .create(CategoryClient.class);
        Call<FoursquareResponse<ServerResponse<Category>>> responseCall = client.categories();
        responseCall.enqueue(setCallback());
    }

    private Callback<FoursquareResponse<ServerResponse<Category>>> setCallback() {
        return new Callback<FoursquareResponse<ServerResponse<Category>>>() {
            @Override
            public void onResponse(Call<FoursquareResponse<ServerResponse<Category>>> call, Response<FoursquareResponse<ServerResponse<Category>>> response) {
                if (!response.isSuccessful()) {
                    mView.onFailure(RecycleViewContract.OnFailure.LIST_EMPTY);
                    return;
                }
                if (response.body() != null){
                    mCategories = response.body().getResponse().getResponseList();
                    if (mCategories != null) {
                        for (int i = 0; i < 50; i++) {
                            mCategories.add(new Category("Dummy ID " + i, "Dummy description " + i));
                        }
                        mView.onSuccess(mCategories);
                    } else {
                        mView.onFailure(RecycleViewContract.OnFailure.LIST_EMPTY);
                    }
                }

            }

            @Override
            public void onFailure(Call<FoursquareResponse<ServerResponse<Category>>> call, Throwable t) {
                mView.onFailure(RecycleViewContract.OnFailure.NO_INTERNET_CONNECTION);
            }
        };
    }

}
