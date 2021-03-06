package com.example.android.newsapp;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Class to load articles in the background
 */

public class ArticleLoader extends android.support.v4.content.AsyncTaskLoader<List<Article>> {

    private static final String LOG_TAG = ArticleLoader.class.getName();

    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        Log.v(LOG_TAG, "Url in Loader " + url);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i(LOG_TAG, "Searching for articles");
    }

    @Override
    public List<Article> loadInBackground() {
        Log.i(LOG_TAG, "Populating article list");
        if (mUrl == null) {
            return null;
        }

        List<Article> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }
}
