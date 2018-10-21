package com.popularmovies.popularmovies;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.popularmovies.Model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetails extends Fragment {

    private View root;
    private Movie selectedMovie;
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieDescription;
    private TextView userRating;
    private TextView releaseDate;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.movie_details, container, false);
        moviePoster = root.findViewById(R.id.movie_poster);
        movieTitle = root.findViewById(R.id.movie_title);
        movieDescription = root.findViewById(R.id.description);
        userRating = root.findViewById(R.id.rating);
        releaseDate = root.findViewById(R.id.year);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    getFragmentManager().popBackStack("movie_details", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        Picasso.with(moviePoster.getContext()).load("http://image.tmdb.org/t/p/w185/" + selectedMovie.getPosterPath()).fit().into(moviePoster);
        movieTitle.setText(selectedMovie.getTitle());
        movieDescription.setText(selectedMovie.getOverview());
        userRating.setText(selectedMovie.getVoteAverage().toString() + "/10");
        releaseDate.setText(selectedMovie.getReleaseDate().substring(0,4));

        super.onStart();
    }

    public static MovieDetails newInstance(Movie movie) {
        MovieDetails fragment = new MovieDetails();
        fragment.selectedMovie = movie;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
