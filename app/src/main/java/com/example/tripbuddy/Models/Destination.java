package com.example.tripbuddy.Models;

public class Destination {
    private String id;
    private String name;
    private String location;
    private int imageResId;
    private double longitude;
    private double latitude;

    public Destination(String id,String name, String location, int imageResId, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
        this.longitude = longitude;
        this.latitude  = latitude;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String getLocation() {
        return location;
    }

    public int getImageResId() {
        return imageResId;
    }
}