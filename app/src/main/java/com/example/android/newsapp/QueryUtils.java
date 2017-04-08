package com.example.android.newsapp;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Query Helper Class
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Query the News API
     */
    public static List<Article> fetchBookData(String requestUrl) {
        // Create a url object
        URL url = createUrl(requestUrl);

        // Perform HTTP Request to url and receive JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_TAG, "error closing input stream", e);
        }

        // Extract relevant fields from JSON and create new {@Link article} object
        List<Article> articles = extractArticleFromJson(jsonResponse);

        // Return the {@Link article}
        return articles;
    }

    /**
     * Returns a Url
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // if URL is null, return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000);
    }
}
