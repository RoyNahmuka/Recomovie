package com.example.recomovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
public class ReviewPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_page, container, false);
        String reviewID =ReviewPageFragmentArgs.fromBundle(getArguments()).getReviewId();
        System.out.println(reviewID);
        Review clickedReview= Model.instance.getReviewByIndex(Integer.parseInt(reviewID));
        TextView movieName = view.findViewById(R.id.review_info_input_movie_name);
        TextView description = view.findViewById(R.id.review_info_input_description);
        TextView category = view.findViewById(R.id.review_info_input_category);
//        TextView actors = findViewById(R.id.review_info_input_actors);
        TextView year = view.findViewById(R.id.review_info_input_year);
        TextView country = view.findViewById(R.id.review_info_input_country);
        TextView rate = view.findViewById(R.id.review_info_input_stars);

        movieName.setText(clickedReview.getMovieName());
        description.setText(clickedReview.getDescription());
//        rate.setText(clickedReview.getStars());
        return view;
    }
}
