package com.popularmovies.popularmovies;

import android.annotation.SuppressLint;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.popularmovies.popularmovies.Database.AppDatabase;
import com.popularmovies.popularmovies.Model.Movie;

import java.net.URL;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieListViewModel extends ViewModel {

    public MutableLiveData<URL> movieUrlLiveData = new MutableLiveData<>();
    public MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();
    private AppDatabase database;
    private GetMoviesService moviesService;


    public MovieListViewModel(AppDatabase database) {
        moviesService = new GetMoviesService(database);
        this.database = database;
    }

    public LiveData<List<Movie>> movies = Transformations.switchMap(movieUrlLiveData, new Function<URL, LiveData<List<Movie>>>() {
        @SuppressLint("CheckResult")
        @Override
        public LiveData<List<Movie>> apply(URL input) {
            Completable.fromRunnable(() -> {
                    moviesService.getMovies(input);
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    () -> {
                        Log.d("Live Data Success", "Live Data came back correctly");
                    }, throwable -> {
                        Log.d("Live Data Failure", "Live Data came back with an exception");
                    }
            );
            return database.movieDAO().getAllMovies();
        }
    });
}


