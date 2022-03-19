package com.example.recomovie.ui.authentication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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

import com.example.recomovie.R;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;
import com.example.recomovie.ui.reviews.ReviewListRvFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class ProfilePageFragment extends Fragment {
    private UsersModel usersModel = UsersModel.instance;
    SwipeRefreshLayout swipeRefresh;
    ProfilePageRvViewModel viewModel;
    UserReviewListViewAdapter adapter;
    TextView username;
    TextView name;
    TextView phone;
    List<Review> reviewList;
    interface OnItemClickListener {
        void onItemCLick(View v,int position);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ProfilePageRvViewModel.class);
    }



    public void displayUserDate(User user){
        if(user != null) {
            username.setText(user.getEmail());
            name.setText(user.getName());
            phone.setText(user.getPhoneNumber());
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_page, container, false);
        User currentUser = usersModel.getCurrentUser();
        username = view.findViewById(R.id.profile_details_username);
        name = view.findViewById(R.id.profile_details_full_name);
        phone = view.findViewById(R.id.profile_details_phone);
        Log.d(currentUser.getEmail() + currentUser.getName()+ currentUser.getPhoneNumber(),"test");
        displayUserDate(currentUser);

        RecyclerView list = view.findViewById(R.id.myReviewsList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserReviewListViewAdapter();
        list.setAdapter(adapter);
        adapter.setOnItemClickListener((v, position) -> {
            String reviewId=viewModel.getReviewList().getValue().get(position).getId();
            Navigation.findNavController(v).navigate(ReviewListRvFragmentDirections.actionReviewListRvFragmentToReviewPageFragment(reviewId));
        });


        return view;
    }


    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    class UserReviewListViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        TextView description;
        ImageView movieImage;
        Button delete;
        Button edit;
        String reviewId;

        public UserReviewListViewHolder(@NonNull View itemView, ProfilePageFragment.OnItemClickListener listener) {
            super(itemView);
            movieName = itemView.findViewById(R.id.my_review_movie_name);
            description = itemView.findViewById(R.id.my_review_movie_description);
            movieImage = itemView.findViewById(R.id.my_review_movie_image);
            delete = itemView.findViewById(R.id.my_review_delete);
            edit = itemView.findViewById(R.id.my_review_edit);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Model.instance.removeReview(reviewId, ()->{});
                    Model.instance.refreshReviewsList();
                    viewModel.getReviewList().getValue().remove(position);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemCLick(v, position);
                }
            });
        }

        public void bind(Review review){
            reviewId = review.getId();
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


    class UserReviewListViewAdapter extends RecyclerView.Adapter<UserReviewListViewHolder>{
        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public UserReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.my_review_list_row, parent, false);
            UserReviewListViewHolder holder = new UserReviewListViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserReviewListViewHolder holder, int position) {
            //Review class binding
            Review review = viewModel.getReviewList().getValue().get(position);
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