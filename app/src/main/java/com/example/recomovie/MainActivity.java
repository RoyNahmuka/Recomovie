package com.example.recomovie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.recomovie.model.users.UsersModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recomovie.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private UsersModel usersModel = UsersModel.instance;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    TextView headerText;
    Button headerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_reviews, R.id.nav_user_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View view = navigationView.getHeaderView(0);
        headerText = view.findViewById(R.id.nav_header_text);
        headerButton = view.findViewById(R.id.nav_header_button);

        usersModel.onUserChange(data -> {
            if (data == null) {
                setNotLoggedIn(drawer, navController);
            } else {
                setLoggedIn(data.getDisplayName(), navController);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setLoggedIn(String currentUserName, NavController navController) {
        setCreateReport();
        if (currentUserName == null) {
            headerText.setText("Welcome!");
        } else {
            headerText.setText("Hello " + currentUserName);
        }
        setTabsVisibility(true);
        headerButton.setText("Logout");
        headerButton.setOnClickListener(buttonView -> {
            usersModel.logout();
            headerText.setText("");
            headerButton.setText("Login / Register");
            navController.navigate(R.id.nav_home);
        });
    }

    private void setNotLoggedIn(DrawerLayout drawer, NavController navController) {
        setCreateReport();
        headerText.setText("");
        headerButton.setText("Login / Register");

        headerButton.setOnClickListener(butt -> {
            navController.navigate(R.id.nav_login);
            drawer.closeDrawer(GravityCompat.START);
        });

        setTabsVisibility(false);
    }

    private void setTabsVisibility(boolean isVisible) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

//        nav_Menu.findItem(R.id.account_tab).setVisible(isVisible);
    }

    private void setCreateReport() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if(checkLocationEnabled() && usersModel.getCurrentUser() != null) {
//            nav_Menu.findItem(R.id.create_report).setVisible(true);
        } else {
//            nav_Menu.findItem(R.id.create_report).setVisible(false);
        }
    }
    private boolean checkLocationEnabled() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}