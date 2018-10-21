package com.popularmovies.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.popularmovies.Database.AppDatabase;
import com.popularmovies.popularmovies.Model.Movie;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieServiceAdapter movieServiceAdapter;
    private MovieListViewModel viewModel;
    private TextView generalTextview;
    private boolean optionsSelected = false;

    private ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            AppDatabase appDatabase = AppDatabase.getDatabase(MoviesApplication.instance);
            return (T) new MovieListViewModel(appDatabase);
        }
    };

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        viewModel = ViewModelProviders.of(this, factory).get(MovieListViewModel.class);

        movieServiceAdapter = new MovieServiceAdapter(viewModel, this );

        recyclerView = rootView.findViewById(R.id.service_data_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanCount(2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieServiceAdapter);
        viewModel.selectedMovie.setValue(null);
        generalTextview = rootView.findViewById(R.id.general_text);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        if (!optionsSelected){
            generalTextview.setVisibility(View.VISIBLE);
        }
        else {
            generalTextview.setVisibility(View.INVISIBLE);
        }
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.most_popular:
                viewModel.movieUrlLiveData.postValue(NetworkUtils.buildUrl(SearchType.MOST_POPULAR));
                optionsSelected = true;
                return true;
            case R.id.top_rated:
                viewModel.movieUrlLiveData.postValue(NetworkUtils.buildUrl(SearchType.TOP_RATED));
                optionsSelected = true;
                return true;
            default:
                optionsSelected = false;
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public void onResume() {
        super.onResume();

        viewModel.selectedMovie.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieDetails.newInstance(movie)).addToBackStack("movie_details").commit();
                }
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
