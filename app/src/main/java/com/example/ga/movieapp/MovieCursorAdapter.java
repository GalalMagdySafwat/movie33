package com.example.ga.movieapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import com.example.ga.movieapp.MovieContract.MovieEntry;


import com.squareup.picasso.Picasso;

/**
 * Created by Ga on 4/19/2018.
 */

public class MovieCursorAdapter extends CursorAdapter {
    public MovieCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.favourite_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView)view.findViewById(R.id.image_f);
        Picasso.with(context)
                .load(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_POSTER))
                .into(imageView);

    }
}
