package com.example.android.newsapp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {

    private String NEWS_QUERY_URL = "https://newsapi.org/v1/articles?=";

    private String NEWS_API_KEY = "d2436b21794e4407a28d2fe761a91e9c";

    private String mSearchFilter = "";

    private int ARTICLE_LOADER_ID = 1;

    private ProgressBar mProgressBar;

    private ArticleAdapter mArticleAdapter;

    private TextView mEmptyView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
