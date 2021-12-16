package com.example.recomovie.model;

import android.media.Image;
import android.widget.ImageView;

public class Review {
    String movieName;
    String description;
    String username;
    Integer Stars;
    Integer Likes;
    ImageView movieImage;

    public Review(String movieName, String description, String username){
        this.movieName = movieName;
        this.description = description;
        this.username = username;
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
}
