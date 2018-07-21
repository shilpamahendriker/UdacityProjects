package com.example.avni.newsapp;

import java.util.List;
import android.content.AsyncTaskLoader;
import android.content.Context;

public class NewsLoader extends AsyncTaskLoader<List<NewsArticle>> {
    public NewsLoader(Context context) {
        super(context);
    }

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Create a new constructor for NewsLoader
     **/
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsArticle> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of articles.
        List<NewsArticle> articles = QueryUtils.fetchNewsArticleData(mUrl);
        return articles;
    }
}
