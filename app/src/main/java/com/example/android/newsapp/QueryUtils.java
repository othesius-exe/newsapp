package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Query Helper Class
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Query the News API
     */
    public static List<Article> fetchArticleData(String requestUrl) {
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
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("Get");
            urlConnection.connect();

            // If the connection was successful, (response code 200)
            // Read the input stream and parse the response

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error Response Code" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving json object");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the InputStream into a string
     * containing the entire json Response
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Extract the relevant information from the JSON object
     * and build an Article Object with it
     */
    public static List<Article> extractArticleFromJson(String articleJSON) {

        // Tests the Article JSON for an empty string
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an ArrayList to store Article objects
        ArrayList<Article> articleList = new ArrayList<>();

        // Parse the JSON response using the proper key value pairs
        // Build an Article object from the data
        try {
            // Create a JSONObject from the jsonResponse
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            // Create a JSONArray from the response
            JSONArray articleArray = baseJsonResponse.getJSONArray("results");

            // Check for results in the ArticleArray
            for (int i = 0; i < articleArray.length(); i ++) {
                // Get article at the current index
                // Create a JSON object from it
                JSONObject thisArticle = articleArray.getJSONObject(i);

                // Extract the article info
                JSONObject properties = thisArticle.getJSONObject("results");

                // Get the title of the article
                String title = properties.getString("webTitle");

                // Extract the source and store it as the publisher
                String category = properties.getString("sectionName");

                // Extract the date on which the article was published
                String date = properties.getString("webPublicationDate");

                String webUrl = properties.getString("webUrl");

                // Add the data to the Article object
                Article article = new Article(title, category, date, webUrl);
                articleList.add(article);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Trouble parsing JSON");
        }

        return articleList;
    }
}
