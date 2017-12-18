package com.servustech.alex.servustech.fragments.googlemaps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.servustech.alex.servustech.model.custominfomarkers.CustomInfoMaker;
import com.servustech.alex.servustech.model.venue.Location;
import com.servustech.alex.servustech.model.venue.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback, GoogleMapsContract.View {
    private GoogleMapsContract.Presenter mPresenter;
    private GoogleMap mMap;

    @BindView(R.id.mapView)
    protected MapView mMapView;
    @BindView(R.id.mapView_info_window)
    protected RelativeLayout mInfoWindow;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.google_maps_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter = new GoogleMapsPresenter(this);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        if (getArguments() != null) {
            getActivity().setTitle(getArguments()
                    .getString(MainScreenActivity.FRAGMENT_TITLE_KEY));
        }

        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong. Please reload the page", Toast.LENGTH_SHORT).show();
            // If something went bad, stop processing
            return rootView;
        }

        mMapView.getMapAsync(this);
        mPresenter.requestVenues();
        mInfoWindow.setVisibility(View.VISIBLE);
        return rootView;
    }


    @Override
    public void setPresenter(GoogleMapsContract.Presenter presenter) {
        mPresenter = presenter;
    }


    /*Called when the Map is ready.*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mPresenter.isReady()) {
            mPresenter.displayMarkers();
        }
    }


    @Override
    public boolean isReady() {
        return mMap != null;
    }


    @Override
    public void displayMarkers(List<Venue> venues) {
        mInfoWindow.setVisibility(View.INVISIBLE); // hide the 'loading' window

        for (Venue venue : venues) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(venue.getLocation().getLat(), venue.getLocation().getLng()))
                    .title(venue.getName());

            mMap.setInfoWindowAdapter(new CustomInfoMaker(getActivity().getApplicationContext()));
            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(venue);
        }
        // Move the map to the last venue
        Location lastLocation = venues.get(venues.size() / 2).getLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLat(), lastLocation.getLng()), 17));
    }


    @Override
    public void onFailure(GoogleMapsContract.OnFailure error) {
        switch (error) {
            case NO_INTERNET_CONNECTION:
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                return;
            case LIST_EMPTY:
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.null_list), Toast.LENGTH_SHORT).show();
                return;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    /* All the methods below must be overwritten to called the respective functions
    * from MapView, otherwise it causes error*/

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mMapView.onLowMemory();
        super.onLowMemory();
    }


}
