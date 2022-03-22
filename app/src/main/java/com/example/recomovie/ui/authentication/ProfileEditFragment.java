package com.example.recomovie.ui.authentication;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recomovie.R;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;
import com.squareup.picasso.Picasso;

public class ProfileEditFragment extends Fragment {
    private UsersModel usersModel = UsersModel.instance;
    TextView username;
    TextView name;
    TextView phone;
    Button save;
    ImageButton camera;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView profileImage;
    Bitmap imageBitmap;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        username = view.findViewById(R.id.profile_edit_details_full_name);
        name= view.findViewById(R.id.profile_edit_fullname);
        phone = view.findViewById(R.id.profile_edit_phone);
        save = view.findViewById(R.id.profile_edit_save);
        camera = view.findViewById(R.id.profile_edit_camera);
        profileImage = view.findViewById(R.id.profile_image);
        profileImage.setVisibility(View.INVISIBLE);
        progressBar = view.findViewById(R.id.editProfile_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        User user = usersModel.getCurrentUser();
        username.setText(user.getEmail());
        usersModel.getUser(user.getId(), currentUser -> {
        if(currentUser.getImageUrl() != null){
            Picasso.get()
                    .load(currentUser.getImageUrl())
                    .into(profileImage);
        }
            profileImage.setVisibility(View.VISIBLE);
        });
        camera.setOnClickListener(v -> {
            openCamera();
        });

        save.setOnClickListener(v -> {
          onSubmit(user,v);
        });
        return view;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                try {
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    profileImage.setImageBitmap(imageBitmap);
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to select image from gallery",Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public void onSubmit(User user, View v) {
        String phoneNumber = phone.getText().toString();
        String fullName = name.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        if (imageBitmap != null) {
            Model.instance.saveImage(imageBitmap, user.getId() + ".jpg", url -> {
                User newUser = new User(fullName, user.getEmail(), phoneNumber, user.getId(), url);
                usersModel.userUpdate(newUser, () -> {
                    Navigation.findNavController(v).navigateUp();
                });
            });
        }else{

            User newUser = new User(fullName, user.getEmail(), phoneNumber, user.getId());
            if(user.getImageUrl() != null)
                newUser.setImageUrl(user.getImageUrl());
            usersModel.userUpdate(newUser, () -> {
                Navigation.findNavController(v).navigateUp();
            });
        }
    }

}