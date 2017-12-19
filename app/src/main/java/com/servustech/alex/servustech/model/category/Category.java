package com.servustech.alex.servustech.model.category;

import com.servustech.alex.servustech.model.Icon;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Category {

     /*You should always put @SerializedName so the GSon will know what this is
     * This way, you can avoid null-pointers exceptions etc*/
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private Icon icon;

    @SerializedName("categories")
    private List<Category> categories;

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

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getNumberOfSubcategories() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }
}
