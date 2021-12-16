package com.example.recomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class MovieListRvActivity extends AppCompatActivity {
    List<Review> reviewList = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_rv);
        reviewList = Model.instance.getAllReviews();

        RecyclerView list = findViewById(R.id.movielist_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter();
        list.setAdapter(adapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        TextView username;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.list_row_movie_name);
            username = itemView.findViewById(R.id.list_row_username);
            description = itemView.findViewById(R.id.list_row_movie_description);
        }
    }

        class Adapter extends RecyclerView.Adapter<ViewHolder>{

            @NonNull
            @Override
            public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.movie_list_row, parent, false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                //Review binding
                Review review = reviewList.get(position);
                holder.description.setText(review.getDescription());//add movie.getDiscription
                holder.movieName.setText(review.getMovieName());
                holder.username.setText(review.getUsername());
            }

            @Override
            public int getItemCount() {
                return reviewList.size();
            }
        }

}