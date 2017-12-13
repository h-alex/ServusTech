package com.example.alex.servustech.fragments.googlemaps;

import com.example.alex.servustech.BaseView;

/**
 * Created by alexch on 12.12.2017.
 */

interface GoogleMapsContract {
    interface View extends BaseView<Presenter> {
        void onVenuesReceived();
        void onVenuesNotReceived();

        void showDetailsAboutVenue();
    }

    interface Presenter {
        void requestVenues();
        void getDetailsAboutVenue();
    }

}
