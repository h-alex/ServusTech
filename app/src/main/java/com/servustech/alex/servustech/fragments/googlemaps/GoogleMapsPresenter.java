package com.servustech.alex.servustech.fragments.googlemaps;


import com.servustech.alex.servustech.model.FoursquareResponse;
import com.servustech.alex.servustech.model.ServerResponse;
import com.servustech.alex.servustech.model.venue.Venue;
import com.servustech.alex.servustech.model.venue.VenuesClient;
import com.servustech.alex.servustech.utils.RetrofitConnector;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoogleMapsPresenter implements GoogleMapsContract.Presenter {
    List<Venue> venues;
    private GoogleMapsContract.View mView;

    public GoogleMapsPresenter() {}

    GoogleMapsPresenter(GoogleMapsContract.View view) {
        mView = view;
    }

    @Override
    public boolean isReady() {
        return venues != null;
    }

    @Override
    public void displayMarkers() {
        this.displayMarkersIfReady();
    }

    @Override
    public void requestVenues() {
        VenuesClient client = RetrofitConnector.getInstance()
                .getRetrofit()
                .create(VenuesClient.class);
        // interrogate the server for the venues
        Call<FoursquareResponse<ServerResponse<Venue>>> responseCall = client.venues("40.7484,-73.9857");
        responseCall.enqueue(setCallback());
    }

    private Callback<FoursquareResponse<ServerResponse<Venue>>> setCallback() {
        return new Callback<FoursquareResponse<ServerResponse<Venue>>>() {
            @Override
            public void onResponse(Call<FoursquareResponse<ServerResponse<Venue>>> call, Response<FoursquareResponse<ServerResponse<Venue>>> response) {
                if (!response.isSuccessful()) {
                    mView.onFailure(GoogleMapsContract.OnFailure.LIST_EMPTY);
                    return;
                }
                if (response.body() != null) {
                    venues = response.body().getResponse().getResponseList();
                    if (venues != null) {
                        displayMarkersIfReady();
                    } else {
                        mView.onFailure(GoogleMapsContract.OnFailure.LIST_EMPTY);
                    }
                }

            }

            @Override
            public void onFailure(Call<FoursquareResponse<ServerResponse<Venue>>> call, Throwable t) {
                mView.onFailure(GoogleMapsContract.OnFailure.NO_INTERNET_CONNECTION);
            }
        };
    }


    /**
     * Called this method to check if you can send the data
     */
    private void displayMarkersIfReady() {
        if (mView.isReady()) {
            mView.displayMarkers(venues);
        }
    }
}
