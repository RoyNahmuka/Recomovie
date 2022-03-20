package com.example.recomovie.model;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recomovie.R;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Review {
    String id;
    String description;
    String movieImageUrl;
    String username;
    String creatorId;
    Integer stars;
    String movieName;
    String year;
    List<String> actors;
    GeoPoint geoPoint;

    public Review(){};

    public Review(String id, String movieName, String description, String username, String creatorId, Integer stars){
        this.id=id;
        this.movieName = movieName;
        this.description = description;
        this.username = username;
        this.creatorId = creatorId;
        this.stars = stars;
    }

    public Review(String id, String movieName, String description, String username, String creatorId, Integer stars, String movieImageUrl, String year, List<String> actors, GeoPoint geoPoint){
        this.id=id;
        this.movieName = movieName;
        this.description = description;
        this.username = username;
        this.creatorId = creatorId;
        this.stars = stars;
        this.movieImageUrl = movieImageUrl;
        this.year = year;
        this.actors = actors;
        this.geoPoint = geoPoint;
    }

    public void setId(String id) { this.id = id; }

    public void setMovieImageUrl(String movieImageUrl) { this.movieImageUrl = movieImageUrl; }

    public String getCreatorId() { return creatorId; }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        stars = stars;
    }

    public void setMovieImage(String url) {
        this.movieImageUrl = url;
    }

    public String getMovieImageUrl() {return this.movieImageUrl;}

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Map<String, Object> toJson() {

        Map<String, Object> json = new HashMap<>();

        json.put("id", id);
        json.put("movieName", movieName);
        json.put("description", description);
        json.put("username", username);
        json.put("creatorId", creatorId);
        json.put("stars", stars);
        json.put("movieImageUrl", movieImageUrl);
        json.put("year",year);
        json.put("actors",actors);
        json.put("geopoint",geoPoint);

        return json;

    }
    public static Review create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String movieName = (String) json.get("movieName");
        String description = (String) json.get("description");
        String username=(String) json.get("username");
        String creatorId =(String) json.get("creatorId");
        String movieImageUrl=(String) json.get("movieImageUrl");
        Integer stars= Integer.parseInt(String.valueOf(json.get("stars")));
        String year = (String) json.get("year");
        List<String> actors = (List<String>) json.get("actors");
        GeoPoint geoPoint = (GeoPoint) json.get("geopoint");

        Review review = new Review(id,movieName,description,username,creatorId, stars,movieImageUrl,year,actors,geoPoint);
        return review;
    }


}
