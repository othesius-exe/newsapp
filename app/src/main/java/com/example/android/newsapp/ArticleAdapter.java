package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // If no view can be recycled, inflate a new one.
        if (convertView == null) {


            // Gets the item's position in the list
            Article thisArticle = getItem(position);

            // Assign the views to be used in the layout
            ImageView articleImageView = (ImageView) convertView.findViewById(R.id.article_image);
            TextView publisherView = (TextView) convertView.findViewById(R.id.publisher);
            TextView dateView = (TextView) convertView.findViewById(R.id.publish_date);
            TextView titleView = (TextView) convertView.findViewById(R.id.title);

            // Format the date for display
            Date dateObject = new Date(thisArticle.getDate());
            String formattedDate = formatDate(dateObject);


            // Call the class getter methods to set the relevant information on each view
            articleImageView.setImageResource(thisArticle.getImage());
            publisherView.setText(thisArticle.getPublisher());
            dateView.setText(formattedDate);
            titleView.setText(thisArticle.getTitle());

        }
        return convertView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);

    }
}
