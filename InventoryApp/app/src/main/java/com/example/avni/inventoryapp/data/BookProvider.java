package com.example.avni.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.avni.inventoryapp.data.BookContract.BookEntry;

public class BookProvider extends ContentProvider {
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = BookProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the Books table
     */
    private static final int BOOKS = 100;

    /**
     * URI matcher code for the content URI for a single book in the bookss table
     */
    private static final int BOOK_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     */

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        /*The calls to addURI() go here, for all of the content URI patterns that the provider
         *should recognize. All paths added to the UriMatcher have a corresponding code to return
         * when a match is found.
         * The content URI of the form "content://com.example.android.books/books" will map to the
         * integer code {@link #PETS}. This URI is used to provide access to MULTIPLE rows
         * of the books table.*/

        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS, BOOKS);

       /* The content URI of the form "content://com.example.android.books/books/#" will map to the
          integer code {@link #PET_ID}. This URI is used to provide access to ONE single row
          of the pets table.The "#" wildcard is used where "#" can be substituted for an integer.*/

        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS + "/#", BOOK_ID);
    }

    /**
     * Database helper object
     */
    private BookDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new BookDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        /*Get readable database*/
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        /*This cursor will hold the result of the query*/
        Cursor cursor;

        /*Check if the URI matcher can match the URI to a specific code*/
        int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                /*For the Books code, query the pets table directly with the given
                  projection, selection, selection arguments, and sort order. The cursor
                  could contain multiple rows of the Books table.*/

                cursor = database.query(BookEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BOOK_ID:
                /*Case when a clause for the query id present*/
                selection = BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(BookEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        /* Set notification URI on the Cursor,
           so we know what content URI the Cursor was created for.
           If the data at this URI changes, then we know we need to update the Cursor.*/

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        /* Return the cursor*/
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return insertBook(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a book into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertBook(Uri uri, ContentValues values) {
        // Check that the title is not null
        String name = values.getAsString(BookEntry.COLUMN_BOOK_TITLE);
        if (name == null) {
            throw new IllegalArgumentException("Book requires a title");
        }

        // Check that the category is valid
        Integer category = values.getAsInteger(BookEntry.COLUMN_BOOK_CATEGORY);
        if (category == null || !BookEntry.isValidCategory(category)) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        // Check Published date is not null
        String date = values.getAsString(BookEntry.COLUMN_BOOK_PUBLICATION_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Book requires publication date");
        }

        // Check Quantity Cannot be null or negative value
        Integer quantity = values.getAsInteger(BookEntry.COLUMN_BOOK_QUANTITY);
        if (quantity == null && quantity < 0) {
            throw new IllegalArgumentException("Quantity is invalid");
        }


        // Get writable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Insert the new book with the given values
        long id = database.insert(BookEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the book content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return updateBook(uri, contentValues, selection, selectionArgs);
            case BOOK_ID:
                // For the BOOK_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateBook(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update books in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more books).
     * Return the number of rows that were successfully updated.
     */
    private int updateBook(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link BookEntry#COLUMN_BOOK_TITLE} key is present,
        // check that the title value is not null.
        if (values.containsKey(BookEntry.COLUMN_BOOK_TITLE)) {
            String name = values.getAsString(BookEntry.COLUMN_BOOK_TITLE);
            if (name == null) {
                throw new IllegalArgumentException("Book needs a title");
            }
        }

        // If the {@link BookEntry#COLUMN_BOOK_CATEGORY} key is present,
        // check that the category value is valid.
        if (values.containsKey(BookEntry.COLUMN_BOOK_CATEGORY)) {
            Integer category = values.getAsInteger(BookEntry.COLUMN_BOOK_CATEGORY);
            if (category == null || !BookEntry.isValidCategory(category)) {
                throw new IllegalArgumentException("Category cannot be null");
            }
        }

        // If the {@link BookEntry#COLUMN_BOOK_PUBLICATION_DATE} key is present,

        if (values.containsKey(BookEntry.COLUMN_BOOK_PUBLICATION_DATE)) {
            String date = values.getAsString(BookEntry.COLUMN_BOOK_PUBLICATION_DATE);
            if (date == null) {
                throw new IllegalArgumentException("Publication date cannot be null");
            }
        }

        // If the {@link BookEntry#COLUMN_BOOK_QUANTITY} key is present,
        //Check that quantity is not null and less than 0
        Integer quantity = values.getAsInteger(BookEntry.COLUMN_BOOK_QUANTITY);
        if (quantity == null && quantity < 0) {
            throw new IllegalArgumentException("Quantity is invalid");
        }

        // If there are no values to update, then no updates the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(BookEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOK_ID:
                // Delete a single row given by the ID in the URI
                selection = BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return BookEntry.CONTENT_LIST_TYPE;
            case BOOK_ID:
                return BookEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
