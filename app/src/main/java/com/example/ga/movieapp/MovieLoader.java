package com.example.ga.movieapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;



public class MovieLoader extends AsyncTaskLoader {
    private static final String  LOG_TAG = MovieLoader.class.getName();
    private  String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }
    protected void onStartLoading(){forceLoad();}

    @Override
    public List<Movie> loadInBackground() {

        if (mUrl==null) {

            return null;

        }

        List<Movie> movies= MovieUtils.fetchMovieData(mUrl);

        return movies;

    }
}
