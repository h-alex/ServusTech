package com.servustech.alex.servustech.model.venue;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("address")
    private String address;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    public Location() {}

    @Override
    public String toString() {
        return address;
    }

    public Location(String address, float lat, float lng) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }


    public String getAddress() {
        return address;
    }



    public void setAddress(String address) {
        this.address = address;
    }



    public double getLat() {
        return lat;
    }



    public void setLat(double lat) {
        this.lat = lat;
    }



    public double getLng() {
        return lng;
    }



    public void setLng(double lng) {
        this.lng = lng;
    }
}
