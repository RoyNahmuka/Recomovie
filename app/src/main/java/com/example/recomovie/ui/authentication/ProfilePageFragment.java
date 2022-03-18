package com.example.recomovie.ui.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recomovie.R;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;

import java.util.LinkedList;
import java.util.List;


public class ProfilePageFragment extends Fragment {
    private UsersModel usersModel = UsersModel.instance;

    TextView username;
    TextView name;
    TextView phone;
    List<Review> reviewList = new LinkedList<>();

    public void displayUserDate(User user){
        username.setText(user.getEmail());
        name.setText(user.getName());
        phone.setText(user.getPhoneNumber());
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
        Log.d(currentUser.getEmail() + currentUser.getName()+ currentUser.getPhoneNumber().toString(),"test");
        displayUserDate(currentUser);

        RecyclerView list = view.findViewById(R.id.myReviewsList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;
    }


}