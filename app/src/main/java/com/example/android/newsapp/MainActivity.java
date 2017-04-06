package com.example.android.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {

    private String NEWS_QUERY_URL = "https://newsapi.org/v1/articles?=";

    private String NEWS_API_KEY = "d2436b21794e4407a28d2fe761a91e9c";

    public static final String LOG_TAG = MainActivity.class.getName();

    private String mSearchFilter = "";

    private int ARTICLE_LOADER_ID = 1;

    private ProgressBar mProgressBar;

    private String mUserQuery;

    private ArticleAdapter mArticleAdapter;

    private TextView mEmptyView;

    private LoaderManager mLoaderManager;

    private ArrayList<Article> mArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        // Convert user input to string
        // Replace spaces with "+"
        EditText editText = (EditText) findViewById(R.id.search_bar);
        mUserQuery = editText.getText().toString();
        mSearchFilter = mUserQuery.replace(" ", "+");

        // Set the adapter to the listview
        ListView articleList = (ListView) findViewById(R.id.article_list);
        mArticleAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        articleList.setAdapter(mArticleAdapter);

        // Instantiate the loader
        mLoaderManager = getSupportLoaderManager();
        mLoaderManager.initLoader(ARTICLE_LOADER_ID, null, this);

        // Cast the Button to be used for submitting queries
        Button submitSearch = (Button) findViewById(R.id.submit_button);

        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check for an internet connection
            }
        });
    }

    // Inflate the view for the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // If the menu button is clicked, open the menu
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    // When a loader is created, show progress bar
    // send the modified url to the loader class
    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "Creating loader");
        mProgressBar.setVisibility(View.VISIBLE);
        return new ArticleLoader(this, NEWS_QUERY_URL + mSearchFilter + NEWS_API_KEY);
    }

    // When the load completes, set the data in the adapter
    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        Log.i(LOG_TAG, "Load finished");
        mArticleAdapter.clear();
        mProgressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            mArticleAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        Log.i(LOG_TAG, "Reset the loader");
        mArticleAdapter.clear();
        mLoaderManager.restartLoader(ARTICLE_LOADER_ID, null, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(mUserQuery, mUserQuery);
        savedInstanceState.putParcelableArrayList("articleList", mArticleList);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUserQuery = savedInstanceState.getString(mUserQuery);
        mArticleList = savedInstanceState.getParcelableArrayList("articleList");
    }
}
