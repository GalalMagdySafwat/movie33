package com.example.ga.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ga on 4/15/2018.
 */

public class ReviewsAdapter extends ArrayAdapter<Reviews> {
    private Context context;

    public ReviewsAdapter(Context context, List<Reviews> Reviews) {
        super(context, 0, Reviews);
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.review_list_item, parent, false);
        }
        Reviews currentReview = getItem(position);
        TextView Author = (TextView)listItemView.findViewById(R.id.AuthorText);
        Author.setText(currentReview.getAuthor());

        TextView Content = (TextView)listItemView.findViewById(R.id.ContentText);
        Content.setText(currentReview.getContent());


        return listItemView;
    }
}