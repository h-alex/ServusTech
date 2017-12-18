package com.servustech.alex.servustech.model.custominfomarkers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.model.venue.Venue;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoMaker implements GoogleMap.InfoWindowAdapter {
    private View infoWindow;

    public CustomInfoMaker(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        infoWindow = inflater.inflate(R.layout.marker_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView contact = infoWindow.findViewById(R.id.marker_info_contact);
        TextView address = infoWindow.findViewById(R.id.marker_info_address);

        TextView title = infoWindow.findViewById(R.id.marker_info_title);
        Venue venue = (Venue) marker.getTag();

        title.setText(venue.getName());
        if (venue.getContact() != null) {
            contact.setText(venue.getContact().toString());
        } else {
            contact.setText(infoWindow.getContext().getString(R.string.no_contact));
        }

        if (venue.getLocation() != null && venue.getLocation().getAddress() != null
                && !venue.getLocation().getAddress().isEmpty()) {
                address.setText(venue.getLocation().getAddress());
        } else {
            address.setText(infoWindow.getContext().getString(R.string.no_location));
        }

        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
