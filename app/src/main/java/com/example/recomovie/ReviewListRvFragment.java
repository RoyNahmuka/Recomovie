package com.example.recomovie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

import java.util.LinkedList;
import java.util.List;

public class ReviewListRvFragment extends Fragment {
    List<Review> reviewList = new LinkedList<>();
    Button addReviewBtn;
    Button loginBtn;

    interface OnItemClickListener {
        void onItemCLick(View v,int position);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_list_rv, container, false);
        reviewList = Model.instance.getAllReviews();

        RecyclerView list = view.findViewById(R.id.movielist_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        ReviewListViewAdapter adapter = new ReviewListViewAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((v, position) -> {
            String reviewId=reviewList.get(position).getId();
            Navigation.findNavController(v).navigate(ReviewListRvFragmentDirections.actionReviewListRvFragmentToReviewPageFragment(reviewId));
        });

        addReviewBtn = view.findViewById(R.id.add_review_btn);
        addReviewBtn.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_reviewListRvFragment_to_createReviewFragment));
        loginBtn = view.findViewById(R.id.login_button);
        loginBtn.setOnClickListener((v)-> Navigation.findNavController(v).navigate(R.id.action_reviewListRvFragment_to_loginFragment));
        return view;
    }


    class ReviewListViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        TextView username;
        TextView description;

        public ReviewListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            movieName = itemView.findViewById(R.id.list_row_movie_name);
            username = itemView.findViewById(R.id.list_row_username);
            description = itemView.findViewById(R.id.list_row_movie_description);


            //TODO: change this behave to navigation logic
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemCLick(v,position);
                }
            });
        }
    }


    class ReviewListViewAdapter extends RecyclerView.Adapter<ReviewListViewHolder>{
        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public ReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.movie_list_row, parent, false);
            ReviewListViewHolder holder = new ReviewListViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewListViewHolder holder, int position) {
            //Review class binding
            Review review = reviewList.get(position);
            holder.description.setText(review.getDescription());
            holder.movieName.setText(review.getMovieName());
            holder.username.setText(review.getUsername());
        }

        @Override
        public int getItemCount() {
            return reviewList.size();
        }
    }

}