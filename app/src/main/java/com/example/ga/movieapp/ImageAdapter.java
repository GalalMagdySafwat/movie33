package com.example.ga.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class ImageAdapter extends ArrayAdapter<Movie> {
    private Context context;

    public ImageAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.movie_image, parent, false);
        }
        Movie currentMovie = getItem(position);
        ImageView iv =(ImageView) listItemView.findViewById(R.id.image_iv);
        Picasso.with(context)
                .load(currentMovie.getMoviePoster())
                .into(iv);
        return listItemView;
    }
}
