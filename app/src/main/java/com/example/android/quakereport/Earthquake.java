package com.example.android.quakereport;

public class Earthquake {
    private String location;
    private double magnitude;
    private long timeInMilliseconds;

    public Earthquake(Double magnitude, String city, Long time) {
        this.magnitude = magnitude;
        this.location = city;
        this.timeInMilliseconds = time;
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
}
