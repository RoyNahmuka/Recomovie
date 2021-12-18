package com.example.recomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

        adapter.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemCLick(int position) {
            }
        });
    }

    interface OnItemClickListener{
        void onItemCLick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        TextView username;
        TextView description;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            movieName = itemView.findViewById(R.id.list_row_movie_name);
            username = itemView.findViewById(R.id.list_row_username);
            description = itemView.findViewById(R.id.list_row_movie_description);


            //TODO: change this behave to navigation logic
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemCLick(position);
                    Intent intent = new Intent(getApplicationContext(), ReviewInfo.class);
                    intent.putExtra("review_position", position);
                    startActivity(intent);
                }
            });
        }
    }

        class Adapter extends RecyclerView.Adapter<ViewHolder>{
            OnItemClickListener listener;

            public void setOnItemClickListener(OnItemClickListener listener){
                this.listener = listener;
            }

            @NonNull
            @Override
            public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.movie_list_row, parent, false);
                ViewHolder holder = new ViewHolder(view, listener);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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