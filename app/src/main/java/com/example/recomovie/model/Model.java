package com.example.recomovie.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model(); //Singleton of the model
    List<Review> reviewList = new LinkedList<>();

    private Model() {
        for(Integer i = 0; i < 10; i++){
            addReview(new Review(i.toString(),i.toString(),i.toString()));
        }
    }

    public List<Review> getAllReviews() { return reviewList;}
    public void addReview(Review review) { reviewList.add(review);}
}
