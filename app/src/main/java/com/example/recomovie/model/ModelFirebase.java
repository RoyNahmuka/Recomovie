package com.example.recomovie.model;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.recomovie.model.common.Listener;
import com.example.recomovie.model.movie.Movie;
import com.example.recomovie.model.users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void addReview(Review review,Model.AddReviewListener listener){
        Map<String, Object> json = review.toJson();
        db.collection("reviews")
                .add(json)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            String reviewId = task.getResult().getId();
                            review.setId(reviewId);
                            db.collection("reviews").document(reviewId).set(review);
                        }
                    }
                })
                .addOnSuccessListener(task -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void getReview(String id, Listener listener) {
        db.collection("reviews")
                .document(id).get().addOnCompleteListener(task -> {
            Review review = null;
            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                if (doc != null) {
                    if (doc.exists()) {
                        review = new Review();
                        review.create(doc.getData());
                    }
                }
            }
            listener.onComplete(review);
        });
    }

    public void updateReview(Review review, String reviewId, EmptyListener listener) {
        Map<String, Object> reviewMap = review.toJson();
        db.collection("reviews").document(reviewId).set(reviewMap, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener != null) listener.onComplete();
            }
        });
    }

    public void removeReview(String reviewId,Model.AddReviewListener listener){
        db.collection("reviews")
                .document(reviewId)
                .delete()
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }


    public List<Review> setReviewList(){
        List<Review> reviewList=new LinkedList<>();
        db.collection("reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                reviewList.add(Review.create(document.getData()));
                            }

                        }
                    }
                });
        return reviewList;
    }

    public interface GetAllReviewListener{
        void onComplete(List<Review> list);
    }
    public interface GetAllMovieListener{
        void onComplete(List<Movie> list);
    }

    public void getReviewList(GetAllReviewListener listener) {
        db.collection("reviews")
                //.whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdateDate,0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Review> list = new LinkedList<Review>();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Map<String, Object > data = doc.getData();
                                Review review = Review.create(data);
                                if (review != null) {
                                    list.add(review);
                                }
                            }
                    }
                    listener.onComplete(list);
                });
    }

    public void getMovieList(GetAllMovieListener listener) {
        db.collection("Movies")
                //.whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdateDate,0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Movie> list = new LinkedList<Movie>();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Map<String, Object > data = doc.getData();
                            Movie movie = Movie.create(data);
                            if (movie != null) {
                                list.add(movie);
                            }
                        }
                    }
                    listener.onComplete(list);
                });
    }


    public void SpecificUserReviews(String username){
        db.collection("reviews")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Review> myReviews = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                myReviews.add(Review.create(document.getData()));
                            }
                        }
                       // listener.onComplete(myReviews);
                    }
                });
    }

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public void saveImage(Bitmap imageBitmap, String imageName, Model.SaveImageListener listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("/movie_images/" + imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            listener.onComplete(null);
        }).addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Uri downloadUrl = uri;
                    listener.onComplete(downloadUrl.toString());
                }
            });
        });
    }
}
