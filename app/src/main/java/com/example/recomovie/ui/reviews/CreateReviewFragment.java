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
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recomovie.R;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.ModelFirebase;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.MonthDay;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CreateReviewFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_PICK = 2;
    final static int RESAULT_SUCCESS = 0;

    private UsersModel usersModel = UsersModel.instance;

    TextView movieName;
    TextView description;
    TextView actors;
    ImageView movieImage;
    int stars;
    Button submit;
    ImageButton camera;
    ImageButton gallery;
    Bitmap imageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_review, container, false);
        movieName = view.findViewById(R.id.create_review_movie_name_input);
        description = view.findViewById(R.id.create_review_description_input);
        camera = view.findViewById(R.id.create_review_camera);
        gallery = view.findViewById(R.id.create_review_gallery);
        submit = view.findViewById(R.id.create_review_submit_btn);
        movieImage = view.findViewById(R.id.create_review_image_input);

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
        Review review = new Review("", movieName.getText().toString(), description.getText().toString(), user.getName(),user.getId(), 5, 5);
        if (imageBitmap == null){
            Model.instance.addReview(review, () -> Navigation.findNavController(submit).navigateUp());
        }else{
            Model.instance.saveImage(imageBitmap, movieUrlId + ".jpg", url -> {
                review.setMovieImage(url);
                Model.instance.addReview(review, () -> Navigation.findNavController(submit).navigateUp());
            });
        }
    }



}