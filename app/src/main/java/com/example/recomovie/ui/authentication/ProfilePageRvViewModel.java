package com.example.recomovie.ui.authentication;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

import java.util.List;


public class ProfilePageRvViewModel extends ViewModel {

    LiveData<List<Review>> reviewList;

    public ProfilePageRvViewModel(){
        reviewList = Model.instance.getAllUserReviews();
    }

    public LiveData<List<Review>> getReviewList() {
        Log.d(String.valueOf(reviewList.getValue().size()), "tea");
        return reviewList;
    }

    public void setReviewList(LiveData<List<Review>> reviewList) {
        this.reviewList = reviewList;
    }
}
