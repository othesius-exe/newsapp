package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    private String NEWS_QUERY_URL = "https://content.guardianapis.com/search?";

    private String NEWS_API_KEY = "&pi-key=1808a9c1-1b7b-4fd7-b096-d46102ab8e91";

    public static final String LOG_TAG = MainActivity.class.getName();

    // Variable to hold the modified search query
    String mSearchFilter = "";

    // Users Search Query
    private String mUserQuery;

    // Full Url to use
    public String mFullUrl;

    public static final int ARTICLE_LOADER_ID = 1;

    public ProgressBar mProgressBar;

    private ArticleAdapter mArticleAdapter;

    private TextView mEmptyView;

    private LoaderManager mLoaderManager;

    private ArrayList<Article> mArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_list);

        // Find and hide the progress bar
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        // Set the Full url to to be a generic query so information loads on opening the application
        mFullUrl = "https://content.guardianapis.com/search?api-key=1808a9c1-1b7b-4fd7-b096-d46102ab8e91";

        // Set the adapter to the ListView
        ListView articleListView = (ListView) findViewById(R.id.article_list);
        mArticleAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        articleListView.setAdapter(mArticleAdapter);

        // Set an empty view on the list on startup
        mEmptyView = (TextView) findViewById(R.id.empty_view);
        articleListView.setEmptyView(mEmptyView);
        mEmptyView.setText(R.string.search);

        // Instantiate the loader
        mLoaderManager = getSupportLoaderManager();
        mLoaderManager.initLoader(ARTICLE_LOADER_ID, null, this);

        // Cast the Button to be used for submitting queries
        final EditText editText = (EditText) findViewById(R.id.search_bar);
        Button submitSearch = (Button) findViewById(R.id.submit_button);

        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for an internet connection
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (!isConnected) {
                    mEmptyView.setText(R.string.no_connection);
                }

                // Convert user input to string
                // Replace spaces with "+"
                // Create a new url to query the api
                mUserQuery = editText.getText().toString();
                mSearchFilter = mUserQuery.replace(" ", "%20").replace("and", "AND").replace("or", "OR").replace("not", "NOT");
                mFullUrl = NEWS_QUERY_URL + mUserQuery + NEWS_API_KEY;

                // Restart the loader on click
                mLoaderManager.restartLoader(ARTICLE_LOADER_ID, null, MainActivity.this);
            }
        });
        Log.e(LOG_TAG, "Url being sent " + mFullUrl);
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
        return new ArticleLoader(this, mFullUrl);
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
