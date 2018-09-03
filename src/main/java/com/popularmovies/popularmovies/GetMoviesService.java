package com.popularmovies.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.popularmovies.popularmovies.Database.AppDatabase;
import com.popularmovies.popularmovies.Model.Movie;
import com.popularmovies.popularmovies.Model.MovieCollection;

import java.io.IOException;
import java.net.URL;

public class GetMoviesService extends AsyncTask<URL, Void, String> {

    ICallback<MovieCollection, Exception> callback;

    AppDatabase appDatabase;

    public GetMoviesService(Context context) {
        appDatabase = AppDatabase.getDatabase(context.getApplicationContext());
    }

    public  void getMovies(URL searchUrl) {
        String movieResults = null;
        try {
            movieResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            MovieCollection movieCollection = gson.fromJson(movieResults, MovieCollection.class);
            for (Movie movie : movieCollection.getResults()) {
                appDatabase.movieDAO().addMovie(movie);
            }
//            callback.onResponse(movieCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String movieResults = null;
        try {
            movieResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(e);
        }
        return movieResults;
    }

    @Override
    protected void onPostExecute(String movieResults) {
        if (movieResults != null && !movieResults.equals("")) {
            try {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MovieCollection movieCollection = gson.fromJson(movieResults, MovieCollection.class);
                for (Movie movie : movieCollection.getResults()) {
                    appDatabase.movieDAO().addMovie(movie);
                }
                callback.onResponse(movieCollection);
            } catch (Exception e) {
                callback.onError(e);
            }

        }
    }
}
