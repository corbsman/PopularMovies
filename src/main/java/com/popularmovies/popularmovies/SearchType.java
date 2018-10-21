package com.popularmovies.popularmovies;

public enum SearchType {
    TOP_RATED("movie/popular?api_key="),
    MOST_POPULAR("movie/popular?api_key=");

    private String url;

    SearchType(String url) {

        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
