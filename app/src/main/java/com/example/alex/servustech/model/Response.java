package com.example.alex.servustech.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexch on 06.12.2017.
 */

public class Response {
    @SerializedName("categories")
    private List<Category> categoryList;

    public Response() {
        categoryList = null;
    }

    public Response(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
