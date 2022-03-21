package com.example.recomovie.ui.authentication;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recomovie.model.EmptyListener;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

import java.util.List;


public class ProfilePageRvViewModel extends ViewModel {

    Model model;

    LiveData<List<Review>> reviewList;
    public void refreshAllReviews(EmptyListener listener) {
        model = Model.instance;
        model.refreshReviewsList();
    }

    public ProfilePageRvViewModel(){
        reviewList = Model.instance.getAllUserReviews();
    }

    public LiveData<List<Review>> getReviewList() {
        return reviewList;
    }

    public void setReviewList(LiveData<List<Review>> reviewList) {
        this.reviewList = reviewList;
    }

}
