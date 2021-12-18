package com.example.recomovie;

import androidx.appcompat.app.AppCompatActivity;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class CreateReview extends AppCompatActivity {
    List<Review> reviewList = new LinkedList<>();
    TextView movieName;
    TextView description;
    TextView actors;
    TextView year;
    TextView country;
    TextView category;
    ImageView movieImage;
    int stars;
    Button submit;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        reviewList = Model.instance.getAllReviews();
        movieName = findViewById(R.id.create_review_movie_name_input);
        description = findViewById(R.id.create_review_description_input);
//        actors = findViewById(R.id.create_review_actors_input);
        year = findViewById(R.id.create_review_year_input);
        country = findViewById(R.id.create_review_country_input);
        category = findViewById(R.id.create_review_category_input);
//        movieName = findViewById(R.id.create_review_image_input);
        submit = findViewById(R.id.create_review_submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    public void onSubmit() {
        Review review = new Review(movieName.getText().toString(),description.getText().toString(),year.getText().toString(),country.getText().toString(),category.getText().toString(),"username",5,5,null);
        Model.instance.addReview(review);
        Intent intent = new Intent(getApplicationContext(), MovieListRvActivity.class);
        startActivity(intent);
    }
}