package com.servustech.alex.servustech.model;

import com.google.gson.annotations.SerializedName;


public class FoursquareResponse<T> {

    @SerializedName("response")
    private T response;

    public T getResponse ()
    {
        return response;
    }

    public void setResponse (T response)
    {
        this.response = response;
    }

}
