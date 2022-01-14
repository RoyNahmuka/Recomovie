package com.example.recomovie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REVIEW_ID = "ARG_REVIEW_ID";
    private String reviewId;

    public ReviewPageFragment() {

    }

    public static ReviewPageFragment newInstance(String reviewId) {
        ReviewPageFragment fragment = new ReviewPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REVIEW_ID, reviewId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reviewId = getArguments().getString(ARG_REVIEW_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_page, container, false);
        Review clickedReview = Model.instance.getReviewByIndex(Integer.valueOf(reviewId));

        TextView movieName = view.findViewById(R.id.review_info_input_movie_name);
        TextView description = view.findViewById(R.id.review_info_input_description);
        TextView category = view.findViewById(R.id.review_info_input_category);
//        TextView actors = findViewById(R.id.review_info_input_actors);
        TextView year = view.findViewById(R.id.review_info_input_year);
        TextView country = view.findViewById(R.id.review_info_input_country);
        TextView rate = view.findViewById(R.id.review_info_input_stars);

        movieName.setText(clickedReview.getMovieName());
        description.setText(clickedReview.getDescription());
        category.setText(clickedReview.getCategory());
//        actors.setText(clickedReview.getActors());
        year.setText(clickedReview.getYear());
        country.setText(clickedReview.getCountry());
//        rate.setText(clickedReview.getStars());
        return view;
    }
}