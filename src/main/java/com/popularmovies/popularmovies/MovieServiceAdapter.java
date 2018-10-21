package com.popularmovies.popularmovies;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.popularmovies.popularmovies.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieServiceAdapter extends RecyclerView.Adapter<MovieServiceAdapter.MovieServiceViewHolder> {

    private List<Movie> movieData = new ArrayList<>();
    private MovieListViewModel viewModel;

    public MovieServiceAdapter(MovieListViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.viewModel = viewModel;
        viewModel.movies.observe(lifecycleOwner, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieData.clear();
                movieData.addAll(movies);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public MovieServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieServiceViewHolder holder, int position) {
        Movie movie;
        movie = movieData.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    class MovieServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPoster;

        public MovieServiceViewHolder(View root) {
            super(root);
            imageViewPoster = (ImageView) root;

        }

        public void bind(Movie movie) {
            Picasso.with(imageViewPoster.getContext())
                    .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                    .centerInside()
                    .fit()
                    .into(imageViewPoster);

            imageViewPoster.setOnClickListener(v-> viewModel.selectedMovie.postValue(movie));
        }
    }
}
