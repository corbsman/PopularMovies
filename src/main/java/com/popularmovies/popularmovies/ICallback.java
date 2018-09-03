package com.popularmovies.popularmovies;

public interface ICallback<T, E extends Throwable> {

    void onResponse(T data);
    void onError(E e);
}
