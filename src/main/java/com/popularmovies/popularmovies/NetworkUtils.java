package com.popularmovies.popularmovies;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {


    final static String THE_MOVIE_DB_URL = "http://api.themoviedb.org/3";

    final static String MOST_POPULAR_PARAM_QUERY = "/movie/popular?api_key=";
    final static String TOP_RATED_PARAM_QUERY = "/movie/top_rated?api_key=";

    final static String API_KEY = "";

    /**
     * Builds the URL for the Movie Database
     *
     *  EXAMPLE: http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
     *
     * @return The URL to use to query to the Movie Database.
     */
    public static URL buildUrl(SearchType searchType) {

        Uri builtUri;
        if (searchType.equals(SearchType.TOP_RATED)) {
            builtUri = Uri.parse(THE_MOVIE_DB_URL + TOP_RATED_PARAM_QUERY + API_KEY).buildUpon().build();
        }
        else {
            builtUri = Uri.parse(THE_MOVIE_DB_URL + MOST_POPULAR_PARAM_QUERY + API_KEY).buildUpon().build();
        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
