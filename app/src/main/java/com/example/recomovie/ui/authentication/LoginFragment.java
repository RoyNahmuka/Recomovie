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
import com.example.recomovie.model.users.UsersModel;


public class LoginFragment extends Fragment {

    private UsersModel usersModel = UsersModel.instance;
    TextView email;
    TextView password;
    TextView errorMsg;
    Button Login;
    Button Register;
    ProgressBar progressBar;

    public void findViewElements(View view){
        Register = view.findViewById(R.id.go_to_register);
        email = view.findViewById(R.id.login_email);
        password = view.findViewById(R.id.login_password);
        Login = view.findViewById(R.id.login_button);
        errorMsg = view.findViewById(R.id.login_error_msg);
        progressBar = view.findViewById(R.id.login_progress_bar);

    }
    public boolean isAllFieldsFulfilled(){
        return !(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()));
    }
    public boolean isEmailValid(){
        return email.getText().toString().matches("^(.+)@(.+)$");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        findViewElements(view);
        errorMsg.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        Login.setOnClickListener(loginView -> {
            if(!isAllFieldsFulfilled()){
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText("Please enter email and password");
                return;
            }
            if(!isEmailValid()){
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText("Please enter valid email");
                return;
            }


            usersModel.login(email.getText().toString(), password.getText().toString(), isSuccess -> {
                if(isSuccess) {
                    NavController navigation = Navigation.findNavController(loginView);
                    navigation.navigateUp();
                } else {
                    errorMsg.setText("Failed to login");
                    errorMsg.setVisibility(View.VISIBLE);
                }
            });
        });
        Register.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment));
        return view;
    }
}