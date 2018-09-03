package com.popularmovies.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.popularmovies.Database.AppDatabase;
import com.popularmovies.popularmovies.Model.Movie;

import java.net.URL;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    private LiveData<List<com.popularmovies.popularmovies.Model.Movie>> moviesLiveData = new LiveData<List<Movie>>() {};
    private RecyclerView recyclerView;
    private MovieServiceAdapter movieServiceAdapter;
    private Movie movieDetail;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        movieServiceAdapter = new MovieServiceAdapter(new IMovieSelected() {
            @Override
            public void movieSelected(Movie movie) {
                movieDetail = movie;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovieDetails()).commit();
            }
        });
        recyclerView = rootView.findViewById(R.id.service_data_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanCount(2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieServiceAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        URL movieUrl = NetworkUtils.buildUrl();
        AppDatabase appDatabase = AppDatabase.getDatabase(getActivity());
        moviesLiveData = appDatabase.movieDAO().getAllMovies();


        Completable.fromRunnable(() -> {
            if (moviesLiveData.getValue().size() == 0){
                new GetMoviesService(getActivity()).getMovies(movieUrl);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    String test = "";
                    // onComplete maybe log it this is a success
                }, throwable -> {
                    String test = "";
                    //todo Error will come here maybe log it or something.
                }
        );
        moviesLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieServiceAdapter.setData(movies);
                movieServiceAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
