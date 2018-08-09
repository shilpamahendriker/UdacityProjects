package com.example.avni.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
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
import android.support.v7.widget.SearchView;
import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;



public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsArticle>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String TG_REQUEST_URL = "https://content.guardianapis.com/search?";
    private static final int NEWS_LOADER_ID = 1;
    TextView emptyTextView;
    ProgressBar progressBar;
    Toolbar toolbar;
    android.support.v7.app.ActionBarDrawerToggle drawerToggle;
    private String section = "news";
    private String query;
    private NewsArticleAdapter adapter;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Get intent strings*/
        Intent intent = getIntent();
        section = intent.getStringExtra("section");
        query = intent.getStringExtra("query");

        /*fetch navigation drawer layout and listview*/
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);

        /*method to setup toolbar*/
        setupToolbar();

        /*populate drawer items array*/
        DrawerListItem[] drawerItem = new DrawerListItem[7];
        drawerItem[0] = new DrawerListItem(R.mipmap.ic_usa, R.string.drawer_item_country_label);
        drawerItem[1] = new DrawerListItem(R.mipmap.ic_world, R.string.drawer_item_world_label);
        drawerItem[2] = new DrawerListItem(R.mipmap.ic_politics, R.string.drawer_item_Politics_label);
        drawerItem[3] = new DrawerListItem(R.mipmap.ic_media, R.string.drawer_item_Media_label);
        drawerItem[4] = new DrawerListItem(R.mipmap.ic_sport, R.string.drawer_item_Sport_label);
        drawerItem[5] = new DrawerListItem(R.mipmap.ic_science, R.string.drawer_item_Science_label);
        drawerItem[6] = new DrawerListItem(R.mipmap.ic_tech, R.string.drawer_item_Technology_label);

        /*Bind navigation drawer with adapter*/
        DrawerListItemAdapter drawerListItemAdapter = new DrawerListItemAdapter(this, R.layout.nav_drawer_item_listview, drawerItem);
        drawerList.setAdapter(drawerListItemAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(drawerToggle);
        setupDrawerToggle();

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
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String fromDate = sharedPrefs.getString(
                getString(R.string.settings_from_date_key),
                getString(R.string.settings_from_date_default_value));

        String toDate = sharedPrefs.getString(
                getString(R.string.settings_to_date_key),
                getString(R.string.settings_to_date_default_value));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(TG_REQUEST_URL);

        // Start building the url using uriBuilder
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendQueryParameter("from-date", convertDateFormat(fromDate));
        uriBuilder.appendQueryParameter("to-date", convertDateFormat(toDate));
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("show-tags", "contributor");

        if (section != null && !section.isEmpty()) {
            uriBuilder.appendQueryParameter("section", section);
        }

        /*Check if a query is raised from the searchview and append the query*/
        if (query != null && !query.isEmpty()) {
            uriBuilder.appendQueryParameter("q", "\"" + query + "\"");
        }
        uriBuilder.appendQueryParameter("api-key", BuildConfig.GuardianSecAPIKEY);

        //return new NewsLoader(this, uriBuilder.toString());

        try {
            return new NewsLoader(this, uriBuilder.toString());
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

        /*Integrate search view*/
        MenuItem searchViewItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setIconified(true);
        searchView.setQueryHint("for best results order by relevance");

        /*Reload the activity with the query from the searchview*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                Log.v("inside", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    /*Method to load settings activity when settings is selected on the toolbar*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings: {
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivity(settingsIntent);
                break;
            }
            case R.id.reload: {
                //refresh the activity if clicked
                finish();
                startActivity(getIntent());
                break;
            }

        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Load main activity when items are selected in the drawer menu with the related content using intents */
    private void selectItem(int position) {

        switch (position) {
            case 0:
                Intent usIntent = new Intent(this, MainActivity.class);
                usIntent.putExtra("section", "us-news");
                startActivity(usIntent);
                break;
            case 1:
                Intent worldIntent = new Intent(this, MainActivity.class);
                worldIntent.putExtra("section", "world");
                startActivity(worldIntent);
                break;
            case 2:
                Intent polIntent = new Intent(this, MainActivity.class);
                polIntent.putExtra("section", "politics");
                startActivity(polIntent);
                break;
            case 3:
                Intent mediaIntent = new Intent(this, MainActivity.class);
                mediaIntent.putExtra("section", "media");
                startActivity(mediaIntent);
                break;
            case 4:
                Intent sportIntent = new Intent(this, MainActivity.class);
                sportIntent.putExtra("section", "sport");
                startActivity(sportIntent);
                break;
            case 5:
                Intent sciIntent = new Intent(this, MainActivity.class);
                sciIntent.putExtra("section", "science");
                startActivity(sciIntent);
                break;
            case 6:
                Intent techIntent = new Intent(this, MainActivity.class);
                techIntent.putExtra("section", "technology");
                startActivity(techIntent);
                break;
            default:
                break;
        }
    }

    /*Sync the drawer Toggle on post Create*/
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /*Setup toolbar*/
    void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        drawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

    /*Method to change the Date format*/
    private String convertDateFormat(String dateString) {
        DateFormat originalFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = originalFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);  // 20120821
        return formattedDate;
    }

    /*Create onClickListner for the drawer items*/
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
