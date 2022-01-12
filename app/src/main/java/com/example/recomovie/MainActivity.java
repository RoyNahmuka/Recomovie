package com.example.recomovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Profile_Page_Fragment profile_page = new Profile_Page_Fragment();
        profile_page.setUserName("Username");

        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.profile_container,profile_page);
        tran.commit();

    }
}