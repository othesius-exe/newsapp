package com.example.android.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String NEWS_QUERY_URL = "https://newsapi.org/v1/articles?=";

    private String NEWS_API_KEY = "d2436b21794e4407a28d2fe761a91e9c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
