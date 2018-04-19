package com.example.ga.movieapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.example.ga.movieapp.MovieContract.MovieEntry;


/**
 * Created by Ga on 4/19/2018.
 */

public class MovieProvider extends ContentProvider {
    private static final int Movies = 100;
    private static final int Movies_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, Movies);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", Movies_ID);
    }


    public static final String LOG_TAG = MovieProvider.class.getSimpleName();
    private MovieHelper mHelper;


    @Override
    public boolean onCreate() {
        mHelper = new MovieHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case Movies:
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case Movies_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("can't query unkonwn URI" + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Movies:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    private Uri insertPet(Uri uri, ContentValues values) {

        String originalTitle = values.getAsString(MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE);
        if (originalTitle == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }


        String poster = values.getAsString(MovieEntry.COLUMN_MOVIE_POSTER);
        if (poster == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }


        String overView = values.getAsString(MovieEntry.COLUMN_MOVIE_OVERVIEW);
        if (overView == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }
        String userRating = values.getAsString(MovieEntry.COLUMN_MOVIE_USER_RATING);
        if (userRating == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }
        String ReleaseDate = values.getAsString(MovieEntry.COLUMN_MOVIE_RELEASE_DATE);
        if (ReleaseDate == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }
        String movieID = values.getAsString(MovieEntry.COLUMN_MOVIE_ID);
        if (movieID == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        SQLiteDatabase database = mHelper.getWritableDatabase();
        long id = database.insert(MovieEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Movies:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case Movies_ID:

                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE)) {
            String originalTitle = values.getAsString(MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE);
            if (originalTitle == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }
        if (values.containsKey(MovieEntry.COLUMN_MOVIE_POSTER)) {
            String moviePoster = values.getAsString(MovieEntry.COLUMN_MOVIE_POSTER);
            if (moviePoster == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }if (values.containsKey(MovieEntry.COLUMN_MOVIE_OVERVIEW)) {
            String overView = values.getAsString(MovieEntry.COLUMN_MOVIE_OVERVIEW);
            if (overView == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }if (values.containsKey(MovieEntry.COLUMN_MOVIE_USER_RATING)) {
            String userRating = values.getAsString(MovieEntry.COLUMN_MOVIE_USER_RATING);
            if (userRating == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }if (values.containsKey(MovieEntry.COLUMN_MOVIE_RELEASE_DATE)) {
            String releaseDate = values.getAsString(MovieEntry.COLUMN_MOVIE_RELEASE_DATE);
            if (releaseDate == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }if (values.containsKey(MovieEntry.COLUMN_MOVIE_ID)) {
            String movieID = values.getAsString(MovieEntry.COLUMN_MOVIE_ID);
            if (movieID == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }



        if (values.size() == 0) {
            return 0;
        }


        SQLiteDatabase database = mHelper.getWritableDatabase();

        return database.update(MovieEntry.TABLE_NAME, values, selection, selectionArgs);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Movies:
                // Delete all rows that match the selection and selection args
                return database.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
            case Movies_ID:
                // Delete a single row given by the ID in the URI
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Movies:
                return MovieEntry.CONTENT_LIST_TYPE;
            case Movies_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
