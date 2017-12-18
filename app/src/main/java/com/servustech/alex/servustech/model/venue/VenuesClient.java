package com.servustech.alex.servustech.model.venue;

import com.servustech.alex.servustech.model.FoursquareResponse;
import com.servustech.alex.servustech.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VenuesClient {
    @GET("search")
    Call<FoursquareResponse<ServerResponse<Venue>>> venues(@Query("ll") String ll);

}
