package com.example.recomovie.model;

import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model(); //Singleton of the model
    List<Review> reviewList;
    List<User> userList=new LinkedList<>();
    ModelFirebase modelFirebase = new ModelFirebase();
    private Model() {
        reviewList=modelFirebase.setReviewList();
    }

    public List<Review> getAllReviews() {return reviewList; }
    public List<User> getUserList() { return userList; }
    public Review getReviewByIndex(int index) {return reviewList.get(index);}
    public User getUserById(int userId){return userList.get(userId);}
    public Review removeReviewByIndex(int index) {return reviewList.remove(index);}

    public void addUser(User user,AddUserListener listener) {
        modelFirebase.addUser(user,listener);
        userList.add(user);
    }

    public interface AddReviewListener{
        void onComplete();
    }
    public void addReview(Review review,AddReviewListener listener) {
        modelFirebase.addReview(review,listener);
        reviewList.add(review);
    }

    public interface AddUserListener {
        void onComplete();
    }
}
