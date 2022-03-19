package com.example.recomovie.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.recomovie.R;
import com.example.recomovie.databinding.FragmentHomeBinding;
import com.example.recomovie.model.users.User;
import com.example.recomovie.model.users.UsersModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private UsersModel usersModel = UsersModel.instance;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button button = root.findViewById(R.id.home_button);
        final TextView title = root.findViewById(R.id.home_title);
        TextView subtitle = root.findViewById(R.id.home_subtitle);
        if(usersModel.getCurrentUser() != null){
            button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_create_review));
        }else {
            button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_login));
        }
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                title.setText(s);
                subtitle.setText("Here you can add reviews about your favorites movies. \n Please login first inorder to create your own review.");
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}