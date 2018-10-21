package com.popularmovies.popularmovies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.popularmovies.popularmovies.Database.AppDatabase;
import com.popularmovies.popularmovies.Model.Movie;
import com.popularmovies.popularmovies.Model.MovieCollection;

import java.io.IOException;
import java.net.URL;

public class GetMoviesService  {

    AppDatabase appDatabase;

    public GetMoviesService(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public  void getMovies(URL searchUrl) {
        String movieResults = null;
        try {
            appDatabase.movieDAO().removeAllMovies();
            movieResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            MovieCollection movieCollection = gson.fromJson(movieResults, MovieCollection.class);
            for (Movie movie : movieCollection.getResults()) {
                appDatabase.movieDAO().addMovie(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
