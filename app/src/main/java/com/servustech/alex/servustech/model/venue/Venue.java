package com.servustech.alex.servustech.model.venue;

import com.google.gson.annotations.SerializedName;

public class Venue {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private Location location;

    @SerializedName("contact")
    private
    Contact contact;

    public Venue() {
    }


    public Venue(String id, String name, Location location, Contact contact) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contact = contact;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        String s = "";
        s += contact + "\n";
        s += location;
        return s;
    }
}
