package com.popularmovies.popularmovies;

import android.app.Application;

public class MoviesApplication extends Application{

    public static MoviesApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
