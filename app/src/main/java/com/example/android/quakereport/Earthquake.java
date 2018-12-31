package com.example.android.quakereport;

public class Earthquake {
    private String location, URI;
    private double magnitude;
    private long timeInMilliseconds;

    public Earthquake(Double magnitude, String city, Long time, String URI) {
        this.magnitude = magnitude;
        this.location = city;
        this.timeInMilliseconds = time;
        this.URI = URI;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public String getURI(){
        return URI;
    }
}
