package com.example.recomovie.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recomovie.R;
import com.example.recomovie.RecomovieApplication;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model(); //Singleton of the model
    private UsersModel usersModel = UsersModel.instance;
    Executor executor = Executors.newFixedThreadPool(1);

    MutableLiveData<List<Review>> reviewList = new MutableLiveData<List<Review>>();
    MutableLiveData<ReviewListLoadingState> reviewListLoadingState = new MutableLiveData<ReviewListLoadingState>();

    public LiveData<ReviewListLoadingState> getReviewListLoadingState() {
        return reviewListLoadingState;
    }
    public enum ReviewListLoadingState{
        loading,
        loaded
    }
//    List<User> userList=new LinkedList<>();
    ModelFirebase modelFirebase = new ModelFirebase();
    private Model() {
        reviewListLoadingState.setValue(ReviewListLoadingState.loaded);
    }

    public LiveData<List<Review>> getAll(){
        if (reviewList.getValue() == null) { refreshReviewsList(); };
        return reviewList;
    }

    public LiveData<List<Review>> getAllUserReviews(){
        User user = usersModel.getCurrentUser();
        List<Review> userReviews = new LinkedList<>();
        Log.d(String.valueOf(this.reviewList.getValue().size()), "size");
        for(Review review: this.reviewList.getValue()){
            Log.d(review.getCreatorId() + " userId "+ user.getId(),"size");
            if(review.getCreatorId().compareTo(user.getId()) == 0){
                userReviews.add(review);
            }
        }
        MutableLiveData<List<Review>> userReviewList = new MutableLiveData<List<Review>>();
        userReviewList.setValue(userReviews);
        return userReviewList;
    }

    public void refreshReviewsList(){
        reviewListLoadingState.setValue(ReviewListLoadingState.loading);
        modelFirebase.getReviewList(new ModelFirebase.GetAllReviewListener() {
            @Override
            public void onComplete(List<Review> list) {
               // List<Review> stList = AppLocalDb.db.reviewDao().getAll();
                reviewList.postValue(list);
                reviewListLoadingState.postValue(ReviewListLoadingState.loaded);
                    }


        });
    }


    public int getNumOfReviews() { return  reviewList.getValue().size(); }
    public Review getReviewByIndex(int index) {return reviewList.getValue().get(index);}
    public Review getReviewById(String id) {
       for(Review review: this.reviewList.getValue()){
           if(review.getId().compareTo(id) == 0)
               return review;
       }
       return null;
    }
//    public User getUserById(int userId){return userList.get(userId);}
    public Review removeReviewByIndex(int index) {return reviewList.getValue().remove(index);}

//    public void addUser(User user,AddUserListener listener) {
//        modelFirebase.addUser(user,listener);
//        userList.add(user);
//    }

    public interface AddReviewListener{
        void onComplete();
    }

    public interface GetReviewsListener{
        void onComplete(List<Review> myReviews);
    }

    public void removeReview(String reviewId, AddReviewListener listener){
        modelFirebase.removeReview(reviewId,listener);
    }

    public void addReview(Review review,AddReviewListener listener) {
        modelFirebase.addReview(review,listener);
        reviewList.getValue().add(review);
    }

    public void SpecificUserReviews(String username) {
        modelFirebase.SpecificUserReviews(username);
    }


    public interface AddUserListener {
        void onComplete();
    }

    public interface SaveImageListener{
        void onComplete(String url);
    }
    public void saveImage(Bitmap imageBitmap, String imageName, SaveImageListener listener) {
        modelFirebase.saveImage(imageBitmap, imageName, listener);
    }
}
