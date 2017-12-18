package com.servustech.alex.servustech.fragments.googlemaps;

import com.servustech.alex.servustech.utils.BaseView;
import com.servustech.alex.servustech.model.venue.Venue;

import java.util.List;


interface GoogleMapsContract {
    interface View extends BaseView<Presenter> {
        /**
         * Call this method to make sure the View is ready.
         * This should be called when doing operations for the first time
         * @return true - the view can start processing requests
         * @return false - the view is not ready yet for processing requests
         */
        boolean isReady();
        void displayMarkers(List<Venue> venues);
        void onFailure(OnFailure error);

    }



    interface Presenter {
        /**
         * Call this method to make sure the Presenter is ready.
         * This should be called when doing operations for the first time
         * @return true - the Presenter can start processing requests
         * @return false - the Presenter is not ready yet for processing requests
         */
        boolean isReady();

        /**
         * Called the when you want to display the markers on your map.
         * This should be called after isReady(), to make sure you have what to work with
         */
        void displayMarkers();
        void requestVenues();
    }

    enum OnFailure{LIST_EMPTY, NO_INTERNET_CONNECTION}
}
