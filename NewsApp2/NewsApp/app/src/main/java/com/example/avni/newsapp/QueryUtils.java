package com.example.avni.newsapp;

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

public class QueryUtils {
    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final int READ_TIME_OUT = 12000;
    private static final int CONNECT_TIME_OUT = 15000;
    private static final int SUCCESS_RESPONSE_CODE = 200;


    /**
     * Create a private constructor so that no object is ever created since the class is only meant to hold variables and methods.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static URL createUrl(String stringUrl) {
        //Check for malformed url
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    public static List<NewsArticle> fetchNewsArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of article
        List<NewsArticle> articles = extractFeatureFromJson(jsonResponse);

        // Return the list of articles
        return articles;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(READ_TIME_OUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIME_OUT /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == SUCCESS_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the articles JSON results.", e);
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
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of NewsArticle objects that has been built up from
     * parsing a JSON response.
     */


    public static List<NewsArticle> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create Array list to collect all the news articles
        ArrayList<NewsArticle> newsArticles = new ArrayList<>();

        try {
            //Create Jsonoject for the root object "response" and iterate through the "results" array
            JSONObject jsonRootObject = new JSONObject(newsJSON);
            JSONObject response = jsonRootObject.getJSONObject("response");
            JSONArray jsonArray = response.getJSONArray("results");

            //Extract title, section , author and published date
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject results = jsonArray.getJSONObject(i);
                String title = results.getString("webTitle");
                String section = results.getString("sectionName");
                String publicationDate = results.getString("webPublicationDate");

                //Use Substring method on the timestamp to extract date only
                publicationDate = publicationDate.substring(0, 10);
                String url = results.getString("webUrl");
                JSONArray tags = results.getJSONArray("tags");
                String author = "";
                JSONObject authorTag = tags.optJSONObject(0);
                //Check if no Author name is provided for any of the articles and assign null to it
                if (authorTag != null) {
                    author = authorTag.optString("webTitle");
                }
                // Create a new article object with the title, section, publication date, url
                // and author from the JSON response.
                NewsArticle article = new NewsArticle(title, section, publicationDate, url, author);
                newsArticles.add(article);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }
        return newsArticles;
    }
}
