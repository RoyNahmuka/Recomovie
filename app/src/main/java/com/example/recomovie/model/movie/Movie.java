package com.example.recomovie.model.movie;

import android.location.Geocoder;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.recomovie.model.Review;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import java.util.Map;

@Entity
public class Movie {
    @PrimaryKey
    @NonNull
    String name;
    String year;
    List<String> actors;
    @Ignore
    GeoPoint geoPoint;

    public Movie(String name, String year, List<String> actors, GeoPoint geoPoint) {
        this.name = name;
        this.year = year;
        this.actors = actors;
        this.geoPoint = geoPoint;
    }
    public Movie() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static Movie create(Map<String, Object> json) {
        String name = (String) json.get("name");
        String year = (String) json.get("year");
        List<String> actors = (List<String>) json.get("actors");
        GeoPoint geoPoint = (GeoPoint) json.get("geopoint");

        Movie movie = new Movie(name,year,actors,geoPoint);
        return movie;
    }
}
