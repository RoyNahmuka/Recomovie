package com.example.recomovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);


        Fragment fragment = new MapFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }
}