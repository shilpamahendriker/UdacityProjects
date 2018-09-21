package com.example.avni.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.avni.inventoryapp.data.BookContract.BookEntry;
import com.example.avni.inventoryapp.data.BookContract;


public class BookCursorAdapter extends CursorAdapter {

    private static String colonSpace = ": ";

    // Constructor
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    // Layout Inflator method
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    // Populating the textviews of the layout with the cursor

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        TextView titleTextView = view.findViewById(R.id.text_book_title);
        TextView priceTextView = view.findViewById(R.id.text_price);
        final TextView quantityTextView = view.findViewById(R.id.text_quatity);


        String titleText = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_TITLE));
        String priceText = context.getResources().getString(R.string.price) + colonSpace + "$ " + cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE));
        String quantityText = context.getResources().getString(R.string.current_quantity) + colonSpace + cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY));

        titleTextView.setText(titleText);
        priceTextView.setText(priceText);
        quantityTextView.setText(quantityText);

        Button saleButton = view.findViewById(R.id.sale_button);
        String columnId = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_ID));
        String quantity = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY));

        // Create a tags on the sale button to differentiate the column and quantity
        saleButton.setTag(R.id.sale_button_col_tag, columnId);
        saleButton.setTag(R.id.sale_button_qty_tag, quantity);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String columnTagID = (String) v.getTag(R.id.sale_button_col_tag);
                String quantityTag = (String) v.getTag(R.id.sale_button_qty_tag);

                if (Integer.parseInt(quantityTag) > 0) {
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_BOOK_QUANTITY, Integer.valueOf(quantityTag) - 1);

                    Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, Integer.valueOf(columnTagID));
                    int rowsAffected = context.getContentResolver().update(currentBookUri, values, null, null);

                    // Show a toast message depending on whether or not the update was successful.
                    if (rowsAffected == 0) {
                        // If no rows were affected, then there was an error with the update.
                        Toast.makeText(context, "update failed",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Otherwise, the update was successful and can display a toast.
                        Toast.makeText(context, "update successful",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {

                    //Change the color of the quantity text view to red alerting inventory is 0
                    quantityTextView.setTextColor(Color.RED);
                    Toast.makeText(context, "Quantity cannot be negitive", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
