package com.example.recomovie.model;

import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model(); //Singleton of the model
    List<Review> reviewList = new LinkedList<>();
    ModelFirebase modelFirebase = new ModelFirebase();
    private Model() {
        for (Integer i = 0; i < 10; i++) {
            addReview(new Review("Name: " + i.toString(), "description: " + i.toString(), "Year: " + i.toString(), "Country: " + i.toString(), "Category: " + i.toString(), "UserName: " + i.toString(), 5, 630, null));
        }
    }

    public List<Review> getAllReviews() {return reviewList; }
    public Review getReviewByIndex(int index) {return reviewList.get(index);}
    public Review removeReviewByIndex(int index) {return reviewList.remove(index);}
    public void addReview(Review review) { reviewList.add(review);}
}
