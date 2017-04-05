package com.example.android.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to build a news article object
 */

public class Article implements Parcelable {

    // Class Variables
    private int mImage;

    private String mTitle;

    private String mPublisher;

    private long mDate;

    // Class constructor
    private void Article(int image, String title, String publisher, long date) {
        mImage = image;
        mTitle = title;
        mPublisher = publisher;
        mDate = date;
    }

    // Class getter methods for individual attributes

    public int getImage() {
        return mImage;
    }
    public String getTitle() {
        return mTitle;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public long getDate() {
        return mDate;
    }

    // Formatted string that returns the title, date and publisher
    @Override
    public String toString() {
        return "Title: " + mTitle +
                "Published on: " + mDate +
                "For: " + mPublisher;
    }

    //TODO Implement Parcelable
    public Article(Parcel in) {
        mPublisher = in.readString();
        mDate = in.readLong();
        mPublisher = in.readString();
        mImage = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPublisher);
        dest.writeString(mTitle);
        dest.writeLong(mDate);
        dest.writeInt(mImage);
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

