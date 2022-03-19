package com.example.recomovie.model;

import android.location.Geocoder;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class Movie {
    String name;
    String summary;
    String year;
    List<String> actors;
    GeoPoint geoPoint;

    public Movie(String name, String summary, String year, List<String> actors, GeoPoint geoPoint) {
        this.name = name;
        this.summary = summary;
        this.year = year;
        this.actors = actors;
        this.geoPoint = geoPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
