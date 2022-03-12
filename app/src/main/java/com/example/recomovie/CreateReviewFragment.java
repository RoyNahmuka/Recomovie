package com.example.recomovie;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.ModelFirebase;
import com.example.recomovie.model.Review;

import java.util.LinkedList;
import java.util.List;

public class CreateReviewFragment extends Fragment {
    List<Review> reviewList = new LinkedList<>();
    TextView movieName;
    TextView description;
    TextView actors;
    ImageView movieImage;
    int stars;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_review, container, false);
        reviewList = Model.instance.getAllReviews();
        movieName = view.findViewById(R.id.create_review_movie_name_input);
        description = view.findViewById(R.id.create_review_description_input);
//        actors = findViewById(R.id.create_review_actors_input);
//        movieName = findViewById(R.id.create_review_image_input);
        submit = view.findViewById(R.id.create_review_submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        return view;
    }

    public void onSubmit() {
        Review review = new Review("1",movieName.getText().toString(),description.getText().toString(),"username",5,5);
        Model.instance.addReview(review,()->Navigation.findNavController(submit).navigateUp());
    }

}