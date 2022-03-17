package com.example.recomovie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ReviewListRvFragment extends Fragment {
    ReviewListRvViewModel viewModel;
    Button addReviewBtn;
    Button loginBtn;
    Button profileBtn;
    ReviewListViewAdapter adapter;
    SwipeRefreshLayout swipeRefresh;

    interface OnItemClickListener {
        void onItemCLick(View v,int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ReviewListRvViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_list_rv, container, false);


        RecyclerView list = view.findViewById(R.id.movielist_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefresh = view.findViewById(R.id.reviewlist_swiperefresh);
        swipeRefresh.setOnRefreshListener(() -> Model.instance.refreshReviewsList());

        adapter = new ReviewListViewAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((v, position) -> {
            String reviewId=viewModel.getReviewList().getValue().get(position).getId();
            Navigation.findNavController(v).navigate(ReviewListRvFragmentDirections.actionReviewListRvFragmentToReviewPageFragment(reviewId));
        });

        addReviewBtn = view.findViewById(R.id.add_review_btn);
        addReviewBtn.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_reviewListRvFragment_to_createReviewFragment));
        loginBtn = view.findViewById(R.id.login_button);
        loginBtn.setOnClickListener((v)-> Navigation.findNavController(v).navigate(R.id.action_reviewListRvFragment_to_loginFragment));
        profileBtn = view.findViewById(R.id.profile_btn);
        profileBtn.setOnClickListener((v)-> Navigation.findNavController(v).navigate(R.id.action_reviewListRvFragment_to_profile_Page_Fragment));


        setHasOptionsMenu(true);
        viewModel.getReviewList().observe(getViewLifecycleOwner(), list1 -> refresh());
        swipeRefresh.setRefreshing(Model.instance.getReviewListLoadingState().getValue() == Model.ReviewListLoadingState.loading);
        Model.instance.getReviewListLoadingState().observe(getViewLifecycleOwner(), studentListLoadingState -> {
            if (studentListLoadingState == Model.ReviewListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });

        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    class ReviewListViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        TextView username;
        TextView description;
        ImageView movieImage;

        public ReviewListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            movieName = itemView.findViewById(R.id.list_row_movie_name);
            username = itemView.findViewById(R.id.list_row_username);
            description = itemView.findViewById(R.id.list_row_movie_description);
            movieImage = itemView.findViewById(R.id.list_row_movie_image);


            //TODO: change this behave to navigation logic
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemCLick(v, position);
                }
            });
        }

        public void bind(Review review){
            description.setText(review.getDescription());
            movieName.setText(review.getMovieName());
            username.setText(review.getUsername());
            if(review.getMovieImageUrl() != null) {
                Picasso.get()
                        .load(review.getMovieImageUrl())
                        .into(movieImage);
            }
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
            Review review = viewModel.getReviewList().getValue().get((position));
            holder.bind(review);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getReviewList().getValue() == null){
                return 0;
            }
            return viewModel.getReviewList().getValue().size();
        }
    }

}