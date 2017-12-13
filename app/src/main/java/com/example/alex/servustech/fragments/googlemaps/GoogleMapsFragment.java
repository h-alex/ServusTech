package com.example.alex.servustech.fragments.googlemaps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alex.servustech.R;
import com.example.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback, GoogleMapsContract.View {
    private GoogleMapsContract.Presenter mPresenter;
    private GoogleMap mMap;
    private MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.google_maps_fragment, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            // TODO close the activity and prompt the user to reload it.
            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong. Please reload the page", Toast.LENGTH_SHORT).show();
        }

        if (getArguments() != null) {
            getActivity().setTitle(getArguments()
                    .getString(MainScreenActivity.FRAGMENT_TITLE_KEY));
        }

        return rootView;
    }


    @Override
    public void setPresenter(GoogleMapsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onVenuesReceived() {
        // put markers on the map
    }

    @Override
    public void onVenuesNotReceived() {
        // display error message and make user refresh the activity
    }

    @Override
    public void showDetailsAboutVenue() {
        // put a Info windows above the marker
    }


    /*Called when the Map is ready.*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(getActivity().getApplicationContext(), "Map is ready!", Toast.LENGTH_SHORT).show();
    }



    /* All the methods below must be overwritten to called the respective functions
    * from MapView, otherwise it causes error*/

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
