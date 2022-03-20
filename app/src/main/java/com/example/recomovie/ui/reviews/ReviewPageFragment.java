package com.example.recomovie.ui.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.recomovie.R;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.squareup.picasso.Picasso;

public class ReviewPageFragment extends Fragment {
    ImageView movieImage;
    TextView movieName;
    TextView description;
    TextView category;
    TextView actors;
    TextView year;
    TextView country;
    TextView rate;

    public void findViewElements(View view) {
         movieImage = view.findViewById(R.id.review_info_movie_image);
         movieName = view.findViewById(R.id.review_info_input_movie_name);
         description = view.findViewById(R.id.review_info_input_description);
         actors = view.findViewById(R.id.review_info_input_actors);
         year = view.findViewById(R.id.review_info_input_year);
         rate = view.findViewById(R.id.review_info_input_stars);
    }

    public void setElementsData(Review review){
        String movieActors = "";
        for(String actor: review.getActors()){
            movieActors += actor + "\n";
        }
        if (review.getMovieImageUrl() != null){
        Picasso.get()
                .load(review.getMovieImageUrl())
                .into(movieImage);
    }
        movieName.setText(review.getMovieName());
        description.setText(review.getDescription());
        actors.setText(movieActors);
        year.setText(review.getYear());
        rate.setText(review.getStars()+ " ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_page, container, false);
        String reviewID =ReviewPageFragmentArgs.fromBundle(getArguments()).getReviewId();
        Review review = Model.instance.getReviewById(reviewID);
        findViewElements(view);
        setElementsData(review);
        return view;
    }
}
