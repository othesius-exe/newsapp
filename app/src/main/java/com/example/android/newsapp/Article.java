package com.example.android.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to build a news article object
 */

public class Article implements Parcelable {

    // Class Variables
    private String mTitle;

    private String mCategory;

    private String mDate;

    private String mUrl;

    private String mThumbnail;

    // Class constructor
    public Article(String title, String category, String date, String thumbnail, String url) {
        mTitle = title;
        mCategory = category;
        mDate = date;
        mUrl = url;
        mThumbnail = thumbnail;
    }

    // Class getter methods for individual attributes
    public String getTitle() {
        return mTitle;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    // Formatted string that returns the title, date and publisher
    @Override
    public String toString() {
        return "Title: " + mTitle +
                "Published on: " + mDate +
                "For: " + mCategory;
    }

    protected Article(Parcel in) {
        mTitle = in.readString();
        mCategory = in.readString();
        mDate = in.readString();
        mUrl = in.readString();
        mThumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mCategory);
        dest.writeString(mDate);
        dest.writeString(mUrl);
        dest.writeString(mThumbnail);
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

