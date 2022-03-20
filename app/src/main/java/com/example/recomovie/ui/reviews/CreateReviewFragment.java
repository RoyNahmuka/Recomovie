package com.example.recomovie.ui.reviews;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.lang.UScript;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recomovie.R;
import com.example.recomovie.RecomovieApplication;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.ModelFirebase;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.common.Listener;
import com.example.recomovie.model.movie.Movie;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;
import com.example.recomovie.ui.authentication.ProfilePageFragmentArgs;
import com.google.firebase.firestore.GeoPoint;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CreateReviewFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_PICK = 2;
    final static int RESAULT_SUCCESS = 0;

    private UsersModel usersModel = UsersModel.instance;
    private Model model = Model.instance;

    String movieName;

    TextView description;
    List<String> actors;
    String year;
    GeoPoint geoPoint;
    ImageView movieImage;
    String stars;
    Button submit;
    ImageButton camera;
    ImageButton gallery;
    Bitmap imageBitmap;
    List<Movie> movies;
    List<String> movieNames;
    Spinner moviesSpinner;
    Spinner rateSpinner;
    String reviewId;
    Review existingReview;

    Movie currentMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_review, container, false);
        description = view.findViewById(R.id.create_review_description_input);
        camera = view.findViewById(R.id.create_review_camera);
        gallery = view.findViewById(R.id.create_review_gallery);
        submit = view.findViewById(R.id.create_review_submit_btn);
        movieImage = view.findViewById(R.id.create_review_image_input);
        moviesSpinner = view.findViewById(R.id.movies_spinner);
        rateSpinner = view.findViewById(R.id.rate_spinner);
        reviewId = ProfilePageFragmentArgs.fromBundle(getArguments()).getReviewId();
        if (reviewId != null) {
            Review review = Model.instance.getReviewById(reviewId);
            System.out.println(review.getDescription());
            existingReview = review;
            movieName=review.getMovieName();
            description.setText(review.getDescription());
            Picasso.get()
                    .load(review.getMovieImageUrl())
                    .into(movieImage);
        }
        Model.instance.getAllMovies(movieList-> {
            movies = movieList;
            movieNames = new ArrayList<>();
            for (Movie movie : movies) {
                movieNames.add(movie.getName());
            }

            ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1,movieNames);
            adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            moviesSpinner.setAdapter(adp1);


            List<String> rates = new LinkedList<>();
            for (Integer i=1;i<6;i++){
                rates.add(i.toString());
            }
            ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1,rates);
            adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            rateSpinner.setAdapter(adp2);

            rateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    // TODO Auto-generated method stub
                    stars = rates.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

            moviesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    // TODO Auto-generated method stub
                    currentMovie = movies.get(position);
                    movieName = currentMovie.getName();
                    actors = currentMovie.getActors();
                    year = currentMovie.getYear();
                    geoPoint = currentMovie.getGeoPoint();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        });

        camera.setOnClickListener(v -> {
            openCamera();
        });

        gallery.setOnClickListener(v -> {
            openGallery();
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        return view;
    }


    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                try {
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    movieImage.setImageBitmap(imageBitmap);
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to select image from gallery",Toast.LENGTH_LONG).show();
                }
            }
        }else if (requestCode == REQUEST_IMAGE_PICK){
            if(resultCode == RESULT_OK){
                try {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    movieImage.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to select image from gallery",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void onSubmit() {
        User user = usersModel.getCurrentUser();
        Random rand = new Random();
        String movieUrlId = user.getId() + rand.nextInt(32) + ".jpg";
        Review review = new Review("", movieName, description.getText().toString(), user.getName(),user.getId(), Integer.parseInt(stars),null,year,actors,geoPoint);
        if(existingReview != null) {
            Review currentReview = Model.instance.getReviewById(reviewId);
            Model.instance.updateReview(currentReview, reviewId, () -> {
                NavController navController = Navigation.findNavController(getView());
                navController.navigateUp();
            });
        }else {
            if (imageBitmap == null) {
                Model.instance.addReview(review, () -> Navigation.findNavController(submit).navigateUp());
            } else {
                Model.instance.saveImage(imageBitmap, movieUrlId + ".jpg", url -> {
                    review.setMovieImage(url);
                    Model.instance.addReview(review, () -> Navigation.findNavController(submit).navigateUp());
                });
            }
        }
    }
}

