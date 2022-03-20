package com.example.recomovie.ui.reviews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recomovie.model.EmptyListener;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.common.Listener;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;

import java.util.LinkedList;
import java.util.List;

public class ReviewListRvViewModel extends ViewModel {
    Model model;
    LiveData<List<Review>> reviewList;

    public ReviewListRvViewModel(){
        reviewList = Model.instance.getAll();
    }

    public LiveData<List<Review>> getReviewList() {
        return reviewList;
    }
    public void getReview(String reviewId, Listener<Review> listener) {
        model.getReview(reviewId, listener);
    }

    public void updateReview(Review review, String reviewID, EmptyListener listener) {
        User user = UsersModel.instance.getCurrentUser();
        if (user != null) {
            review.setId(user.getId());
        }
        Model.instance.updateReview(review,reviewID, listener);
    }

    public void setReviewList(LiveData<List<Review>> reviewList) {
        this.reviewList = reviewList;
    }
}
