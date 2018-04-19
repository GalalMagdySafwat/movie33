package com.example.ga.movieapp;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ga on 4/19/2018.
 */

public class MovieContract {

    private MovieContract() {
    }
    public static final String CONTENT_AUTHORITY = "com.example.ga.movieapp";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_MOVIES = "movies";
    public static final class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String TABLE_NAME = "movies";
        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";
        public static final String COLUMN_MOVIE_OVERVIEW = "overView";
        public static final String COLUMN_MOVIE_USER_RATING = "userRating";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "releaseDate";

    }
}
