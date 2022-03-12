package com.example.recomovie.model;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recomovie.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Review {
    String id;
    String movieName;
    String description;
    String username;
    Integer Stars;
    Integer Likes;
    ImageView movieImage;

    public Review(){};
    public Review(String movieName, String description, String username){
        this.movieName = movieName;
        this.description = description;
        this.username = username;
    }
    public Review(String id, String movieName, String description, String username, Integer Stars,Integer Likes, ImageView movieImage){
        this.id=id;
        this.movieName = movieName;
        this.description = description;
        this.username = username;
        this.Stars = Stars;
        this.Likes = Likes;
        this.movieImage = movieImage;
    }

    List<String> actors;

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
        return Stars;
    }

    public void setStars(Integer stars) {
        Stars = stars;
    }

    public Integer getLikes() {
        return Likes;
    }

    public void setLikes(Integer likes) {
        Likes = likes;
    }

    public ImageView getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(ImageView movieImage) {
        this.movieImage = movieImage;
    }

    public Map<String, Object> toJson() {

        Map<String, Object> json = new HashMap<>();

        json.put("id", id);
        json.put("movieName", movieName);
        json.put("description", description);
        json.put("username", username);
        json.put("Stars", Stars);
        json.put("Likes", Likes);
        json.put("movieImage", movieImage);

        return json;

    }

}
