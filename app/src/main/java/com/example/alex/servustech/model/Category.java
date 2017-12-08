package com.example.alex.servustech.model;

import com.google.gson.annotations.SerializedName;


public class Category {

     /*You should always put @SerializedName so the GSon will know what this is
     * This way, you can avoid null-pointers exceptions etc*/
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private Icon icon;

    public Category() {
        this.id = null;
        this.name = null;
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
