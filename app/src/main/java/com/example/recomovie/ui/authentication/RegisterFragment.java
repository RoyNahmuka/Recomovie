package com.example.recomovie.ui.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recomovie.R;
import com.example.recomovie.model.common.Listener;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.LinkedList;
import java.util.List;


public class RegisterFragment extends Fragment {
    private UsersModel usersModel = UsersModel.instance;

    List<User> userList = new LinkedList<>();
    TextView email;
    TextView name;
    TextView phone;
    TextView password;
    TextView errorMsg;
    Button submit;
    ProgressBar progressBar;

    public void findViewElemnts(View view){
        email = view.findViewById(R.id.user_email);
        name = view.findViewById(R.id.user_fullName);
        phone = view.findViewById(R.id.user_phone);
        password = view.findViewById(R.id.register_Password);
        submit = view.findViewById(R.id.register_button);
        progressBar = view.findViewById(R.id.register_progress_bar);
        errorMsg = view.findViewById(R.id.register_error_msg);
    }

    public boolean isAllFieldsFulfilled(){
        return !(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText())
                || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(phone.getText()));
    }

    public boolean isPhoneNumberValid() {
        return phone.getText().toString().matches("[0-9]+");
    }

    public boolean isEmailValid(){
        return email.getText().toString().matches("^(.+)@(.+)$");
    }

    public boolean isAllInputsValid(){

        if(!isAllFieldsFulfilled()){
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText("Please fulfilled all input");
            return false;
        }
        if(!isPhoneNumberValid()){
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText("Please enter correct phone number");
             return false;
        }
        if(!isEmailValid()){
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText("Please enter correct email");
            return false;

        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        findViewElemnts(view);
        progressBar.setVisibility(View.VISIBLE);
        errorMsg.setVisibility(View.INVISIBLE);
        submit.setOnClickListener(submitView -> {
            progressBar.setVisibility(View.VISIBLE);
            if(!isAllInputsValid()) {
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            User user = new User(name.getText().toString(), email.getText().toString(), phone.getText().toString());
            usersModel.registerUser(user, password.getText().toString(),new Listener<Task<AuthResult>>() {
                @Override
                public void onComplete(Task<AuthResult> data) {
                    if (data.isSuccessful()) {
                        NavController navigation = Navigation.findNavController(view);
                        navigation.navigateUp();
                        navigation.navigateUp();
                    } else {
//                        progressBar.setVisibility(View.INVISIBLE);
                        errorMsg.setText(data.getException().getMessage());
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                }
            });
        });

        return view;
    }

}