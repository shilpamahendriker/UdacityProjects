package com.example.avni.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.LoaderManager;
import android.content.Loader;

import java.util.ArrayList;
import java.util.List;

import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsArticle>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String TG_REQUEST_URL = "https://content.guardianapis.com/search?from-date=2017-07-19&show-tags=contributor&q=android&api-key=test";

    private NewsArticleAdapter adapter;
    private static final int NEWS_LOADER_ID = 1;

    TextView emptyTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enabling the action bar and adding image icon

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_android);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Checking internet connectivity and if connected load the listview else display the empty textview

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            ListView newsListView = findViewById(R.id.list);
            // Create a new {@link ArrayAdapter} of News articles
            adapter = new NewsArticleAdapter(this, new ArrayList<NewsArticle>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            newsListView.setAdapter(adapter);

            //Create onclickListner for the items in the listview and start a new intent with
            //corresponding URL
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NewsArticle selection = adapter.getItem(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selection.getUrl()));
                    startActivity(browserIntent);
                }
            });


            emptyTextView = findViewById(R.id.empty_view);
            ListView listView = findViewById(R.id.list);
            listView.setEmptyView(emptyTextView);

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            progressBar = findViewById(R.id.loading_spinner);
            emptyTextView = findViewById(R.id.empty_view);
            progressBar.setVisibility(View.INVISIBLE);
            emptyTextView.setText(R.string.no_network);
        }
    }

    @Override
    public android.content.Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        try {
            return new NewsLoader(this, TG_REQUEST_URL);
        } catch (Exception e) {
            Log.e(LOG_TAG, "IO exception", e);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticles) {
        // Clear the adapter of previous  data
        adapter.clear();
        // If there is a valid list of articles, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsArticles != null && !newsArticles.isEmpty()) {
            adapter.addAll(newsArticles);
        }
        progressBar = findViewById(R.id.loading_spinner);
        //If no data is fetched then display No data found
        emptyTextView.setText(R.string.no_data);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //refresh the activity if clicked
        finish();
        startActivity(getIntent());
        return super.onOptionsItemSelected(item);
    }

}
