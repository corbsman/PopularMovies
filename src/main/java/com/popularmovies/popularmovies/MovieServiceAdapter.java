package com.popularmovies.popularmovies;

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
    private IMovieSelected movieSelected;

    public MovieServiceAdapter(IMovieSelected iMovieSelected) {
        movieSelected = iMovieSelected;
    }

    public void setData(List<Movie> newData) {
        movieData.clear();
        movieData.addAll(newData);
//        notifyDataSetChanged();
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
//                    .resize(185, 277)
                    .centerInside()

//                    .transform(new CircleTransform(50,0))
                    .fit()
                    .into(imageViewPoster);

            imageViewPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieSelected.movieSelected(movie);
                }
            });



        }
    }
}
