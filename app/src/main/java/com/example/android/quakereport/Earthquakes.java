package com.example.android.quakereport;

public class Earthquakes {
    private double magnitude;
    private String city, date;

    public Earthquakes(double magnitude, String city, String date){
        this.magnitude = magnitude;
        this.city = city;
        this.date = date;
    }

    public String getMagnitude() {
        return String.valueOf(magnitude);
    }

    public String getCity(){
        return city;
    }

    public String getDate(){
        return date;
    }
}
