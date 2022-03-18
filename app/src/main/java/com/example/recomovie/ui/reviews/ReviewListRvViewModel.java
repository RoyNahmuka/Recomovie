package com.example.recomovie.ui.reviews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

import java.util.LinkedList;
import java.util.List;

public class ReviewListRvViewModel extends ViewModel {

    LiveData<List<Review>> reviewList;

    public ReviewListRvViewModel(){
        reviewList = Model.instance.getAll();
    }

    public LiveData<List<Review>> getReviewList() {
        return reviewList;
    }

    public void setReviewList(LiveData<List<Review>> reviewList) {
        this.reviewList = reviewList;
    }
}
