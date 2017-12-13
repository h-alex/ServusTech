package com.example.alex.servustech.model.interfaces;

import com.example.alex.servustech.model.FoursquareResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface FoursquareClient {

    @GET("categories")
    Call<FoursquareResponse> categories();
}
