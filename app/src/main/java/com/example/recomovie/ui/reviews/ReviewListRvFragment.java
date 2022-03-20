package com.example.recomovie.ui.reviews;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recomovie.R;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.squareup.picasso.Picasso;

public class ReviewListRvFragment extends Fragment {
    ReviewListRvViewModel viewModel;
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
        TextView stars;

        public ReviewListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            movieName = itemView.findViewById(R.id.list_row_movie_name);
            username = itemView.findViewById(R.id.list_row_username);
            description = itemView.findViewById(R.id.list_row_movie_description);
            movieImage = itemView.findViewById(R.id.list_row_movie_image);
            stars = itemView.findViewById(R.id.star_rate);

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
            stars.setText(review.getStars().toString());
            System.out.println(stars);
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