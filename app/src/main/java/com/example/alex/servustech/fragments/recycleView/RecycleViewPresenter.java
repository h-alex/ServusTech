package com.example.alex.servustech.fragments.recycleView;

import android.util.Log;

import com.example.alex.servustech.model.Category;
import com.example.alex.servustech.model.FoursquareResponse;
import com.example.alex.servustech.model.interfaces.FoursquareClient;
import com.example.alex.servustech.utils.RetrofitConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RecycleViewPresenter implements RecycleViewContract.Presenter {
    private static final String TAG = "RecycleViewPresenter";

    private static final String URL = "https://api.foursquare.com/v2/venues/";
    private static final String CLIENT_ID = "LZGSXOHYCY2T5OEROLIAW4XPK4XY1TALXQUDTBN15ZSS3SPZ";
    private static final String CLIENT_SECRET = "KOXCULTUGRCHJ4Z3BVD3WCVYU12WECZRMXSDED05MFACHUAR";
    private static final String VERSION_UP_TO = "20171206";

    RecycleViewContract.View mView;
    private List<Category> mCategories;

    RecycleViewPresenter(RecycleViewContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void requestResults() {
        FoursquareClient client = RetrofitConnector.getInstance()
                .getRetrofit()
                .create(FoursquareClient.class);
        Call<FoursquareResponse> responseCall = client.categories();
        responseCall.enqueue(setCallback());
    }

    private Callback<FoursquareResponse> setCallback() {
        return new Callback<FoursquareResponse>() {
            @Override
            public void onResponse(Call<FoursquareResponse> call, Response<FoursquareResponse> response) {
                FoursquareResponse foursquareResponse = response.body();
                if (foursquareResponse == null) {
                    mView.onFailure(RecycleViewContract.OnFailure.LIST_EMPTY);
                    return;
                }
                mCategories = foursquareResponse.getCategories(); // body can be null
                if (mCategories != null) {
                    for (int i = 0; i <50 ; i++) {
                        mCategories.add(new Category("Dummy ID " + i, "Dummy description "+ i  ));
                    }
                    mView.onSuccess(mCategories);
                } else {
                    mView.onFailure(RecycleViewContract.OnFailure.LIST_EMPTY);
                }
            }

            @Override
            public void onFailure(Call<FoursquareResponse> call, Throwable t) {
                mView.onFailure(RecycleViewContract.OnFailure.NO_INTERNET_CONNECTION);
            }
        };
    }

}
