package com.example.recomovie.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ModelFirebase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void addReview(Review review){
        Map<String, Object> json = review.toJson();
        db.collection("reviews")
                .add(json);
//                .addOnSuccessListener(unused -> listener.onComplete())
//                .addOnFailureListener(e -> listener.onComplete());
    }


}
