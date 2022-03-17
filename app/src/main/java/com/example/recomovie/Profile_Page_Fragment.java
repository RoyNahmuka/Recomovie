package com.example.recomovie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;

import java.util.LinkedList;
import java.util.List;


public class Profile_Page_Fragment extends Fragment {

    TextView usernameTV;
    TextView addressTV;
    TextView phoneTV;
    private String username;
    List<Review> reviewList = new LinkedList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile__page_, container, false);
        usernameTV = view.findViewById(R.id.profile_user_name);
        if(username!=null)
            usernameTV.setText(username);

        Model.instance.SpecificUserReviews("shoval");
        System.out.println("PPF:" +reviewList);
        RecyclerView list = view.findViewById(R.id.myReviewsList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    public void setUserName(String username) {

       if(usernameTV != null)
           usernameTV.setText(username);
    }

    public void setAddressTV(TextView addressTV) {
        this.addressTV = addressTV;
    }


    public void setPhoneTV(TextView phoneTV) {
        this.phoneTV = phoneTV;
    }



}