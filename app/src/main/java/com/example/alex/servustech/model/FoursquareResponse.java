package com.example.alex.servustech.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FoursquareResponse {

    @SerializedName("response")
    private Response response;


    public Response getResponse ()
    {
        return response;
    }

    public void setResponse (Response response)
    {
        this.response = response;
    }

    public List<Category> getCategories() {
        return response.getCategoryList();
    }

}
