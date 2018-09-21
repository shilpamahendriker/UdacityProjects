package com.example.avni.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;
    private static final String COMMA_SEP = ", ";

    // Build query to Create table
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME +
            " (" + BookContract.BookEntry.COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            BookContract.BookEntry.COLUMN_BOOK_TITLE + " TEXT NOT NULL" + COMMA_SEP +
            BookContract.BookEntry.COLUMN_BOOK_PRICE + " INTEGER" + COMMA_SEP +
            BookContract.BookEntry.COLUMN_BOOK_QUANTITY + " INTEGER NOT NULL" + COMMA_SEP +
            BookContract.BookEntry.COLUMN_BOOK_CATEGORY + " INTEGER NOT NULL" + COMMA_SEP +
            BookContract.BookEntry.COLUMN_BOOK_PUBLICATION_DATE + "  TEXT NOT NULL" + COMMA_SEP +
            BookContract.BookEntry.COLUMN_SUPPLIER_NAME + "  TEXT" + COMMA_SEP +
            BookContract.BookEntry.COLUMN_SUPPLIER_PHONENO + " TEXT);";


    public BookDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

}
