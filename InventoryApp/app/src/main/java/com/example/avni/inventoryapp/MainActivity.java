package com.example.avni.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.avni.inventoryapp.data.BookContract.BookEntry;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the Book data loader
     */
    private static final int BOOK_LOADER = 0;
    ActionBarDrawerToggle drawerToggle;
    BookCursorAdapter cursorAdapter;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    // Variables to catch data passed from search intent
    private String titleTextSearch;
    private String fromSearchActivity;
    private String categorySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawer);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Setup FAB to open insert edit activity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertEditInventory.class);
                startActivity(intent);
            }
        });

        // Saving data fron the searh inventory activity intent to respective variables
        titleTextSearch = getIntent().getStringExtra("titleSearchText");
        categorySearch = getIntent().getStringExtra("categorySearch");
        fromSearchActivity = getIntent().getStringExtra("fromSearch");


        ListView booksListView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        if (fromSearchActivity != null) {

            // if the main activity is loading from search inventory intent which has no search results
            // then change the empty view with appropriate image and textviews with text
            ImageView emptyListViewImage = emptyView.findViewById(R.id.empty_listview_image);
            TextView emptyListViewTitleText = emptyView.findViewById(R.id.empty_title_text);
            TextView emptyListViewSubtitleText = emptyView.findViewById(R.id.empty_subtitle_text);

            // Making the work 'search' in the text to link to search Inventory activity
            // so that a new search can be done
            SpannableString ss = new SpannableString("Try another search");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(MainActivity.this, SearchInventory.class));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };
            ss.setSpan(clickableSpan, 12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            emptyListViewSubtitleText.setText(ss);
            emptyListViewSubtitleText.setMovementMethod(LinkMovementMethod.getInstance());
            emptyListViewSubtitleText.setHighlightColor(Color.TRANSPARENT);

            emptyListViewImage.setImageResource(R.drawable.no_books_found);
            emptyListViewTitleText.setText(R.string.no_books_found);
            booksListView.setEmptyView(emptyView);

        } else {
            // if the main activity does not load from search inventory activity the assign the emptyView
            // without changes
            booksListView.setEmptyView(emptyView);

        }
        // Setup an Adapter to create a list item for each row of book data in the Cursor.
        // There is no book data yet (until the loader finishes) so pass in null for the Cursor.
        cursorAdapter = new BookCursorAdapter(this, null);
        booksListView.setAdapter(cursorAdapter);

        /*fetch navigation drawer layout and listview*/
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);

        // Create an array for the navigation drawer list and set adapter to populate them
        List<String> values = new ArrayList<>();
        values.add("Home");
        values.add("Add books");
        values.add("Search");
        values.add("Delete all books");
        values.add("Preferences");
        values.add("Add Dummy Data");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, values);

        drawerList.setAdapter(arrayAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout.addDrawerListener(drawerToggle);
        setupDrawerToggle();

        // Setup the item click listener for Book list view
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link InsertEditInventory}
                Intent intent = new Intent(MainActivity.this, InsertEditInventory.class);

                // Form the content URI that represents the specific book that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link BookEntry#CONTENT_URI}.

                Uri currentPetUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current book.
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(BOOK_LOADER, null, MainActivity.this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        // Look for the share preferences and assign it to the variable to sort order of the books
        // in the list view
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        String sortOrder = null;
        if (orderBy.equals("title")) {
            sortOrder = BookEntry.COLUMN_BOOK_TITLE + " ASC";

        } else if (orderBy.equals("quantity")) {
            sortOrder = BookEntry.COLUMN_BOOK_QUANTITY + " ASC";
        } else if (orderBy.equals("price")) {
            sortOrder = BookEntry.COLUMN_BOOK_PRICE + " ASC";
        }


        // Define a projection that specifies the columns from the table needed.
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_TITLE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_CATEGORY
        };

        // check if the search inventory intent has passes a vaule and add the clause to the query to
        // retrieve the specific data
        if (fromSearchActivity != null) {
            if (categorySearch != null) {
                Log.v("Searchcategory", categorySearch);
            }
            String selection = BookEntry.COLUMN_BOOK_TITLE + " LIKE ? AND " + BookEntry.COLUMN_BOOK_CATEGORY + "=?";
            String selectionArgs[] = {"%" + titleTextSearch + "%", categorySearch};
            return new CursorLoader(this,   // Parent activity context
                    BookEntry.CONTENT_URI,   // Provider content URI to query
                    projection,             // Columns to include in the resulting Cursor
                    selection,                   // No selection clause
                    selectionArgs,                   // No selection arguments
                    sortOrder);


        } else {
            // if the main activity is not loading from search activity then fromSearchActivity is null
            // This loader will execute the ContentProvider's query method on a background thread
            return new CursorLoader(this,   // Parent activity context
                    BookEntry.CONTENT_URI,   // Provider content URI to query
                    projection,             // Columns to include in the resulting Cursor
                    null,                   // No selection clause
                    null,                   // No selection arguments
                    sortOrder);                  // Default sort order
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    /*Sync the drawer Toggle on post Create*/
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Load main activity when items are selected in the drawer menu with the related content using intents */
    private void selectItem(int position) {

        switch (position) {

            case 0:
                Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(homeIntent);
                break;
            case 1:
                Intent addBookIntent = new Intent(this, InsertEditInventory.class);
                startActivity(addBookIntent);

                break;
            case 2:
                Intent searchIntent = new Intent(this, SearchInventory.class);
                startActivity(searchIntent);

                break;
            case 3:
                showDeleteConfirmationDialog();
                break;
            case 4:
                Intent prefernceIntent = new Intent(this, Preference.class);
                startActivity(prefernceIntent);
                break;
            case 5:
                insertbooks();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            default:
                break;
        }

    }

    /**
     * Helper method to delete all books in the database.
     */
    private void deleteAllBooks() {
        int rowsDeleted = getContentResolver().delete(BookEntry.CONTENT_URI, null, null);
        Log.v("MainActivity", rowsDeleted + " rows deleted from books database");
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the book.
                deleteAllBooks();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the book.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // Method to Insert a new row as dummy data into the provider using the ContentResolver.
    private void insertbooks() {

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_TITLE, "Harry Potter and philosopher's stone");
        values.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_FANTASY);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, 20);
        values.put(BookEntry.COLUMN_BOOK_PRICE, 70);
        values.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "May 15,1999");
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, "J,K Rowling");
        values.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");


        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

        ContentValues values1 = new ContentValues();
        values1.put(BookEntry.COLUMN_BOOK_TITLE, "The Chamber of Secrets");
        values1.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_AUTOBIOGRAPHY);
        values1.put(BookEntry.COLUMN_BOOK_QUANTITY, 40);
        values1.put(BookEntry.COLUMN_BOOK_PRICE, 80);
        values1.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "May 15,1998");
        values1.put(BookEntry.COLUMN_SUPPLIER_NAME, "J,K Rowling");
        values1.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");

        Uri newUri1 = getContentResolver().insert(BookEntry.CONTENT_URI, values1);

        ContentValues values2 = new ContentValues();
        values2.put(BookEntry.COLUMN_BOOK_TITLE, "Midnight's Children");
        values2.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_FICTION);
        values2.put(BookEntry.COLUMN_BOOK_QUANTITY, 60);
        values2.put(BookEntry.COLUMN_BOOK_PRICE, 60);
        values2.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "Jun 16,1981");
        values2.put(BookEntry.COLUMN_SUPPLIER_NAME, "Jonathan Cape");
        values2.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");

        Uri newUri2 = getContentResolver().insert(BookEntry.CONTENT_URI, values2);

        ContentValues values3 = new ContentValues();
        values3.put(BookEntry.COLUMN_BOOK_TITLE, "The White Tiger");
        values3.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_HISTORY);
        values3.put(BookEntry.COLUMN_BOOK_QUANTITY, 65);
        values3.put(BookEntry.COLUMN_BOOK_PRICE, 23);
        values3.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "Jun 16,2008");
        values3.put(BookEntry.COLUMN_SUPPLIER_NAME, " Aravind Adiga");
        values3.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");

        Uri newUri3 = getContentResolver().insert(BookEntry.CONTENT_URI, values3);

        ContentValues values4 = new ContentValues();
        values4.put(BookEntry.COLUMN_BOOK_TITLE, "That frequent Visitor");
        values4.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_HORROR);
        values4.put(BookEntry.COLUMN_BOOK_QUANTITY, 65);
        values4.put(BookEntry.COLUMN_BOOK_PRICE, 23);
        values4.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "Jun 16,2008");
        values4.put(BookEntry.COLUMN_SUPPLIER_NAME, " Aravind Adiga");
        values4.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");

        Uri newUri4 = getContentResolver().insert(BookEntry.CONTENT_URI, values4);

        ContentValues values5 = new ContentValues();
        values5.put(BookEntry.COLUMN_BOOK_TITLE, "Big Chicken");
        values5.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_HORROR);
        values5.put(BookEntry.COLUMN_BOOK_QUANTITY, 78);
        values5.put(BookEntry.COLUMN_BOOK_PRICE, 90);
        values5.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "Jul 26,2018");
        values5.put(BookEntry.COLUMN_SUPPLIER_NAME, " National Geographic");
        values5.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");

        Uri newUri5 = getContentResolver().insert(BookEntry.CONTENT_URI, values5);

        ContentValues values6 = new ContentValues();
        values6.put(BookEntry.COLUMN_BOOK_TITLE, "Wings of fire");
        values6.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_AUTOBIOGRAPHY);
        values6.put(BookEntry.COLUMN_BOOK_QUANTITY, 102);
        values6.put(BookEntry.COLUMN_BOOK_PRICE, 45);
        values6.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "Mar 26,2010");
        values6.put(BookEntry.COLUMN_SUPPLIER_NAME, " A P J Abul Kalam");
        values6.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");

        Uri newUri6 = getContentResolver().insert(BookEntry.CONTENT_URI, values6);

        ContentValues values7 = new ContentValues();
        values7.put(BookEntry.COLUMN_BOOK_TITLE, "Security Mom");
        values7.put(BookEntry.COLUMN_BOOK_CATEGORY, BookEntry.CATEGORY_UNCLASSIFIED);
        values7.put(BookEntry.COLUMN_BOOK_QUANTITY, 84);
        values7.put(BookEntry.COLUMN_BOOK_PRICE, 36);
        values7.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, "May 15,2012");
        values7.put(BookEntry.COLUMN_SUPPLIER_NAME, " Alexis Schwarzenbach");
        values7.put(BookEntry.COLUMN_SUPPLIER_PHONENO, "001 001 0001");

        Uri newUri7 = getContentResolver().insert(BookEntry.CONTENT_URI, values7);
    }

    /*Create onClickListner for the drawer items*/
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
