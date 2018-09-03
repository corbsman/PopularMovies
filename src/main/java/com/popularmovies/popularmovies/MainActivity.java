package com.popularmovies.popularmovies;

import android.annotation.SuppressLint;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.popularmovies.popularmovies.Model.Movie;
import com.popularmovies.popularmovies.Model.MovieCollection;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    MovieCollection movieCollection = null;
//    private final String MOVIE_COLLECTION = "movie_collection";  //todo remove this
    private LiveData<List<Movie>> moviesLiveData;


//OnSavedInstanceState put movie collection into the saved instance state to the bundle

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable(MOVIE_COLLECTION, movieCollection);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            MainFragment fragment = MainFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }


    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
