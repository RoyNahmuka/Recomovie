package com.example.recomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

public class ReviewInfo extends AppCompatActivity {
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_info);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            position = bundle.getInt("review_position");
        }
        Review clickedReview = Model.instance.getReviewByIndex(position);

        TextView movieName = findViewById(R.id.review_info_input_movie_name);
        TextView description = findViewById(R.id.review_info_input_description);
        TextView category = findViewById(R.id.review_info_input_category);
//        TextView actors = findViewById(R.id.review_info_input_actors);
        TextView year = findViewById(R.id.review_info_input_year);
        TextView country = findViewById(R.id.review_info_input_country);
        TextView rate = findViewById(R.id.review_info_input_stars);

        movieName.setText(clickedReview.getMovieName());
        description.setText(clickedReview.getDescription());
        category.setText(clickedReview.getCategory());
//        actors.setText(clickedReview.getActors());
        year.setText(clickedReview.getYear());
        country.setText(clickedReview.getCountry());
//        rate.setText(clickedReview.getStars());
    }
}