package com.example.android.quakereport;

public class Earthquake {
    private String magnitude, city, date, time;
    private long timeInMilliseconds;

    public Earthquake(String magnitude, String city, Long time){
        this.magnitude = magnitude;
        this.city = city;
        this.timeInMilliseconds = time;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getCity(){
        return city;
    }

    public String getDate(){
        return date;
    }

    public String getTime() {
        return time;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }
}
