package com.example.alex.servustech.model;

import com.google.gson.annotations.SerializedName;


public class Icon {
    private static final String DEFAULT_ICON_SIZE = "bg_64";

    @SerializedName("prefix")
    private String prefix;

    @SerializedName("suffix")
    private String suffix;

    public Icon() {
    }

    public String getImageURL() {
        return prefix + DEFAULT_ICON_SIZE + suffix;
    }

    public Icon(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
