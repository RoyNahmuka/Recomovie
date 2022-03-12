package com.example.recomovie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.User;

import java.util.LinkedList;
import java.util.List;


public class RegisterFragment extends Fragment {
    List<User> userList = new LinkedList<>();
    TextView userName;
    TextView Address;
    TextView phone;
    TextView password;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        userName = view.findViewById(R.id.user_register_name);
        Address = view.findViewById(R.id.user_address);
        phone = view.findViewById(R.id.user_phone);
        password = view.findViewById(R.id.register_Password);
        submit = view.findViewById(R.id.register_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        return view;
    }

    public void onSubmit() {
        User user = new User(userName.getText().toString(), Address.getText().toString(), phone.getText().toString(), password.getText().toString());
        Model.instance.addUser(user,()->{});

    }
}