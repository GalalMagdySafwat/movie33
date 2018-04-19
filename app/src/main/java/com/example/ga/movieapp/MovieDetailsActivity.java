package com.example.ga.movieapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {


    private static final int Details_LOADER_ID = 2;
    VideosAdapter vAdapter;
    ReviewsAdapter rAdapter;


    private String movieId;
    private String title;
    private String overView;
    private String userRating;
    private String releaseDate;
    private String poster;


    private String MOVIE_DETAILS_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_d);
        Intent intent=getIntent();
        String ReceiveTitle =intent.getStringExtra("title");
        String ReceiveOverView =intent.getStringExtra("overView");
        String ReceiveRating =intent.getStringExtra("UserRating");
        String ReceiveDate =intent.getStringExtra("ReleaseDate");
        String ReceivePoster =intent.getStringExtra("MoviePoster");
        String ReceiveMovieID = intent.getStringExtra("MovieID");


        title=ReceiveTitle;
        overView=ReceiveOverView;
        userRating=ReceiveRating;
        releaseDate=ReceiveDate;
        poster=ReceivePoster;
        movieId = ReceiveMovieID;

        MOVIE_DETAILS_URL=
                "https://api.themoviedb.org/3/movie/"+ReceiveMovieID+"?api_key=*********&append_to_response=videos,reviews";

        ListView videoList = (ListView)findViewById(R.id.list_video);
        vAdapter = new VideosAdapter(this,new ArrayList<Videos>());
        videoList.setAdapter(vAdapter);

        ListView ReviewList = (ListView)findViewById(R.id.list_review);
        rAdapter = new ReviewsAdapter(this,new ArrayList<Reviews>());
        ReviewList.setAdapter(rAdapter);

        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(Details_LOADER_ID,null,this);


        TextView MovieName=(TextView)findViewById(R.id.MovieName);
        MovieName.setText(ReceiveTitle);
        TextView MovieOverView=(TextView)findViewById(R.id.overview);
        MovieOverView.setText(ReceiveOverView);
        TextView MovieRating=(TextView)findViewById(R.id.rating);
        MovieRating.setText(ReceiveRating);
        TextView MovieDate=(TextView)findViewById(R.id.date);
        MovieDate.setText(ReceiveDate);
        TextView MovieID =(TextView)findViewById(R.id.movieID);
        MovieID.setText(ReceiveMovieID);
        ImageView MoviePoster =(ImageView)findViewById(R.id.poster);
        Picasso.with(this)
                .load(ReceivePoster)
                .into(MoviePoster);
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Videos currentVideo = vAdapter.getItem(position);
                String videoURL = "http://www.youtube.com/watch?v=" + currentVideo.getKey();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL)));

            }
        });
        final FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor movieCursor = getContentResolver().query(
                        MovieContract.MovieEntry.CONTENT_URI,
                        new String[]{MovieContract.MovieEntry.COLUMN_MOVIE_ID},
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId,
                        null,
                        null);
                if (movieCursor != null && movieCursor.moveToFirst()) {
                    getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                            MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId, null);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    movieCursor.close();

                }else {
                    ContentValues movieValues = new ContentValues();
                    movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE,
                            title);
                    movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                            overView);
                    movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_USER_RATING,
                            userRating);
                    movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                            releaseDate);
                    movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER,
                            poster);
                    movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                            movieId);
                    getContentResolver().insert(
                            MovieContract.MovieEntry.CONTENT_URI,
                            movieValues
                    );
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);

                }
            }
        });

    }

    @Override
    public Loader<Movie> onCreateLoader(int i, Bundle bundle) {
        ReviewsAndVideosLoader reviewsAndVideosLoader = new ReviewsAndVideosLoader(this,MOVIE_DETAILS_URL );
        return reviewsAndVideosLoader;
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie movie) {
        vAdapter.addAll(movie.getVideos());
        rAdapter.addAll(movie.getReviews());
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {
        vAdapter.clear();
        rAdapter.clear();

    }

}
