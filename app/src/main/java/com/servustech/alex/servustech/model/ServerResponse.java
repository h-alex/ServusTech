package com.servustech.alex.servustech.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ServerResponse<T> {
    @SerializedName(value = "", alternate = {"categories", "venues"})
    private List<T> responseList;

    public ServerResponse() {
        responseList = null;
    }

    public ServerResponse(List<T> categoryList) {
        this.responseList = categoryList;
    }

    public List<T> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<T> responseList) {
        this.responseList = responseList;
    }
}
