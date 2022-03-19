package com.example.recomovie;

import android.app.Application;
import android.content.Context;

public class RecomovieApplication extends Application {

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
