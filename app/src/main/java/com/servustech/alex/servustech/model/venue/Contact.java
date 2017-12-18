package com.servustech.alex.servustech.model.venue;

import com.google.gson.annotations.SerializedName;

public class Contact {
    private static final String NO_CONTACT = "No contact";

    @SerializedName("phone")
    private String phone;

    @SerializedName("formattedPhone")
    private String formattedPhone;

    @SerializedName("twitter")
    private String twitter;

    public Contact() {}


    public Contact(String phone, String formattedPhone, String twitter) {
        this.phone = phone;
        this.formattedPhone = formattedPhone;
        this.twitter = twitter;
    }


    @Override
    public String toString() {
        if (formattedPhone != null && !formattedPhone.isEmpty()) {
            return formattedPhone;
        }
        if (phone != null && !phone.isEmpty()) {
            return phone;
        }
        if (twitter != null && !twitter.isEmpty()) {
            return "Twitter: " + twitter;
        }
        return NO_CONTACT;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFormattedPhone() {
        return formattedPhone;
    }

    public void setFormattedPhone(String formattedPhone) {
        this.formattedPhone = formattedPhone;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
