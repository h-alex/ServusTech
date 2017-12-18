package com.servustech.alex.servustech.model.category;

import com.servustech.alex.servustech.model.ServerResponse;
import com.servustech.alex.servustech.model.FoursquareResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CategoryClient {

    @GET("categories")
    Call<FoursquareResponse<ServerResponse<Category>>> categories();
}
