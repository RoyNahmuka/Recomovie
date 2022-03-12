package com.example.recomovie.model;

import java.util.List;

public class Movie {
    String name;
    String summary;
    String year;
    List<String> actors;
    List<Review> reviews;
    Number rate;

    public Movie(String name, String summary, String year, List<String> actors, List<Review> reviews, Number rate) {
        this.name = name;
        this.summary = summary;
        this.year = year;
        this.actors = actors;
        this.reviews = reviews;
        this.rate = rate;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Number getRate() {
        return rate;
    }

    public void setRate(Number rate) {
        this.rate = rate;
    }

}
