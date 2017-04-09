package com.example.android.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to build a news article object
 */

public class Article implements Parcelable {

    // Class Variables
    private String mImageUrl;

    private String mTitle;

    private String mPublisher;

    private String mDate;

    // Class constructor
    public Article(String imageUrl, String title, String publisher, String date) {
        mImageUrl = imageUrl;
        mTitle = title;
        mPublisher = publisher;
        mDate = date;
    }

    // Class getter methods for individual attributes
    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getDate() {
        return mDate;
    }

    // Formatted string that returns the title, date and publisher
    @Override
    public String toString() {
        return "Title: " + mTitle +
                "Published on: " + mDate +
                "For: " + mPublisher;
    }

    protected Article(Parcel in) {
        mImageUrl = in.readString();
        mTitle = in.readString();
        mPublisher = in.readString();
        mDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageUrl);
        dest.writeString(mTitle);
        dest.writeString(mPublisher);
        dest.writeString(mDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}

