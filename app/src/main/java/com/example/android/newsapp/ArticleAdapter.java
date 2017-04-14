package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Custom adapter class to handle news articles
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    // Constructor for Article Adapter.
    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    // Method for finding a view to recycle.
    // If no view is found method inflates a new one.
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // If no view can be recycled, inflate a new one.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, parent, false);
        }

        // Gets the item's position in the list
        Article thisArticle = getItem(position);

        // Assign the views to be used in the layout
        TextView publisherView = (TextView) convertView.findViewById(R.id.publisher);
        TextView dateView = (TextView) convertView.findViewById(R.id.publish_date);
        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        ImageView articleImage = (ImageView) convertView.findViewById(R.id.article_image);

        // Call the class getter methods to set the relevant information on each view
        publisherView.setText(thisArticle.getCategory());
        dateView.setText(thisArticle.getDate());
        titleView.setText(thisArticle.getTitle());
        Picasso.with(getContext()).load(thisArticle.getThumbnail()).into(articleImage);
        return convertView;
    }

}
