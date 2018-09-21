package com.example.avni.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class BookContract {
    public BookContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.avni.inventoryapp";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH_BOOKS = "books";

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     * */

    public static final class BookEntry implements BaseColumns {

        /** The content URI to access the book data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);


        /**
         * The MIME type of the {@link #//CONTENT_URI} for a list of bookss.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * The MIME type of the {@link #//CONTENT_URI} for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        // Table name
        public static final String TABLE_NAME = "books";

        // Column names
        public static final String COLUMN_BOOK_TITLE = "title";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_PUBLICATION_DATE = "publication_date";
        public static final String COLUMN_BOOK_CATEGORY = "category";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONENO = "supplier_phoneno";
        public static final String COLUMN_BOOK_ID = BaseColumns._ID;

        /*Possible values for the column category*/
        public static final int CATEGORY_UNCLASSIFIED = 0;
        public static final int CATEGORY_FANTASY = 1;
        public static final int CATEGORY_SCIENCE = 2;
        public static final int CATEGORY_FICTION = 3;
        public static final int CATEGORY_HORROR = 4;
        public static final int CATEGORY_HISTORY = 5;
        public static final int CATEGORY_AUTOBIOGRAPHY = 6;

        public static boolean isValidCategory(int category) {
            if (category == CATEGORY_UNCLASSIFIED || category == CATEGORY_FANTASY || category == CATEGORY_SCIENCE || category == CATEGORY_FICTION || category == CATEGORY_HORROR || category == CATEGORY_HISTORY || category == CATEGORY_AUTOBIOGRAPHY) {
                return true;
            }
            return false;
        }
    }
}
