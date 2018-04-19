package com.example.ga.movieapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MovieImage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    private static final String LOG_TAG = MovieImage.class.getName();
    private static final String TMDB_REQUEST_URL =
            "http://api.themoviedb.org/3/movie/popular?api_key=********";
    private static final String TMDB_USER_RATING =
            "http://api.themoviedb.org/3/movie/top_rated?api_key=********";
    private static final int Movie_LOADER_ID = 1;
    private ImageAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_image);
        GridView MovieListView = (GridView) findViewById(R.id.list);

        mAdapter = new ImageAdapter(this, new ArrayList<Movie>());


        MovieListView.setAdapter(mAdapter);
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(Movie_LOADER_ID,null,this);
        MovieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie currentEarthquake = mAdapter.getItem(i);

                String title = currentEarthquake.getOriginalTitle();
                String title1 = currentEarthquake.getOverView();
                String title2 = currentEarthquake.getUserRating();
                String title3 = currentEarthquake.getReleaseDate();
                String title4 = currentEarthquake.getMoviePoster();
                String movieid = currentEarthquake.getMovieID();

                launchDetailActivity(title,title1,title2,title3,title4,movieid);

            }
        });

    }
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean b = pref.getBoolean("SortBy",false);
        if (b==true) {
            MovieLoader movieLoader = new MovieLoader(this, TMDB_REQUEST_URL);
            return movieLoader;
        }else {
            MovieLoader movieLoader = new MovieLoader(this, TMDB_USER_RATING);
            return movieLoader;
        }
    }

    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mAdapter.clear();
        if (movies != null && !movies.isEmpty()) {
            mAdapter.addAll(movies);
        }
    }

    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.clear();

    }
    private void launchDetailActivity(String title,String title1,String title2,String title3,String title4,String movieid) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);

        intent.putExtra("title",title);
        intent.putExtra("overView",title1);
        intent.putExtra("UserRating",title2);
        intent.putExtra("ReleaseDate",title3);
        intent.putExtra("MoviePoster",title4);
        intent.putExtra("MovieID",movieid);


        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.sortby_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent startSettingActivity = new Intent(this,SettingsActivity.class);
            startActivity(startSettingActivity);
            return true;
        }
        if (id==R.id.favouriteButton){
            Intent startSettingActivity = new Intent(this,FavouriteActivity.class);
            startActivity(startSettingActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}