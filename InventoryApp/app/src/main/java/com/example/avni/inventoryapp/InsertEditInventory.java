package com.example.avni.inventoryapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.avni.inventoryapp.data.BookContract;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;

import com.example.avni.inventoryapp.data.BookContract.BookEntry;


public class InsertEditInventory extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the book data loader
     */
    private static final int EXISTING_BOOK_LOADER = 0;

    /**
     * Content URI for the existing book (null if it's a new book)
     */
    private Uri currentBookUri;

    /**
     * EditText field to enter book title name
     */
    private EditText bookTitleEditText;

    /**
     * EditText field to enter the book's price
     */
    private EditText bookPriceEditText;

    /**
     * EditText field to enter the quantity of books
     */
    private TextView bookQuantityTextView;

    /**
     * EditText field to enter the publication date
     */
    private EditText bookPublicationDateEditText;

    /**
     * EditText field to enter the book supplier name
     */
    private EditText supplierNameEditText;

    /**
     * EditText field to enter the book supplier phone no.
     */
    private EditText supplierPhoneNoEditText;

    /**
     * ImageView field to show book image
     */
    private ImageView bookImageView;

    /**
     * Spinner to select category of the book
     */
    private Spinner categorySpinner;

    /**
     * Button to increase quantity of the book
     */

    private Button buttonQtyIncrease;

    /**
     * Button to decrease quantity of the book
     */
    private Button buttonQtyDecrease;

    /**
     * Button redirect the dialer app for calling supplier
     */

    private ImageButton buttonPhone;

    private Integer bookQuantity = 0;
    /**
     * Initializing Category
     **/
    private int category = BookEntry.CATEGORY_UNCLASSIFIED;

    /**
     * Boolean flag that keeps track of whether the book has been edited (true) or not (false)
     */
    private boolean bookHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the bookHasChanged boolean to true.
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            bookHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_edit_inventory);


        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new book or editing an existing one.
        Intent intent = getIntent();
        currentBookUri = intent.getData();

        // If the intent DOES NOT contain a book content URI, then its a new book

        if (currentBookUri == null) {
            // This is a new book, so change the app bar to say "Add a book"
            setTitle(getString(R.string.InsertEdit_activity_title_new_book));

        } else {
            // Otherwise this is an existing book, so change app bar to say "Edit Book"
            setTitle(getString(R.string.InsertEdit_activity_title_Edit_book));

            // Initialize a loader to read the book data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_BOOK_LOADER, null, this);
        }


        //Create calender instance to assign initial date to the datepicker
        final Calendar calendar = Calendar.getInstance();

        bookTitleEditText = findViewById(R.id.edit_book_title);
        bookPriceEditText = findViewById(R.id.edit_book_price);
        bookQuantityTextView = findViewById(R.id.text_view_quantity);
        buttonQtyIncrease = findViewById(R.id.button_qty_increase);
        buttonQtyDecrease = findViewById(R.id.button_qty_dec);
        bookPublicationDateEditText = findViewById(R.id.edit_publication);
        supplierNameEditText = findViewById(R.id.edit_supplier_name);
        supplierPhoneNoEditText = findViewById(R.id.edit_supplier_phone);
        supplierPhoneNoEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        buttonPhone = findViewById(R.id.imagebtn_phone);
        categorySpinner = findViewById(R.id.spinner_category);

        //Create onDatesetListener to pick the date selected and assign to the edit text field

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MMM dd, yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                bookPublicationDateEditText.setText(sdf.format(calendar.getTime()));
            }
        };

        bookPublicationDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InsertEditInventory.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonQtyIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyIncrement();
            }
        });
        buttonQtyDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyDecrement();
            }
        });

        bookTitleEditText.setOnTouchListener(touchListener);
        bookPriceEditText.setOnTouchListener(touchListener);
        //bookQuatityEditText.setOnTouchListener(touchListener);
        bookPublicationDateEditText.setOnTouchListener(touchListener);
        supplierNameEditText.setOnTouchListener(touchListener);
        supplierPhoneNoEditText.setOnTouchListener(touchListener);
        categorySpinner.setOnTouchListener(touchListener);

        //Create OnClickListner to the phone button and intent to dialer app
        buttonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(supplierPhoneNoEditText.getText().toString())) {
                    Toast.makeText(InsertEditInventory.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + supplierPhoneNoEditText.getText().toString().trim()));
                    startActivity(intent);
                }
            }
        });

        //Call method to set up spinner
        setupSpinner();

    }

    // Method definition for setting up spinner
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        categorySpinner.setAdapter(categorySpinnerAdapter);

        // Set the integer category to the constant values
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.unclassified))) {
                        category = BookEntry.CATEGORY_UNCLASSIFIED; // Unclassified
                    } else if (selection.equals(getString(R.string.fantasy))) {
                        category = BookContract.BookEntry.CATEGORY_FANTASY; // FANTASY
                    } else if (selection.equals(getString(R.string.science))) {
                        category = BookContract.BookEntry.CATEGORY_SCIENCE; // SCIENCE
                    } else if (selection.equals(getString(R.string.fiction))) {
                        category = BookContract.BookEntry.CATEGORY_FICTION;// FICTION
                    } else if (selection.equals(getString(R.string.horror))) {
                        category = BookContract.BookEntry.CATEGORY_HORROR; // HORROR
                    } else if (selection.equals(getString(R.string.history))) {
                        category = BookContract.BookEntry.CATEGORY_HISTORY; // HISTORY
                    } else if (selection.equals(getString(R.string.autobiography))) {
                        category = BookContract.BookEntry.CATEGORY_AUTOBIOGRAPHY; // AUTOBIOGRAPHY
                    }

                }
            }

            // assign default value when nothing is selected on the spinner
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = 0; // Unclassified
            }
        });

        // Button to insert the data into the table
        Button saveBookButton = findViewById(R.id.button_save_book);
        saveBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBook();

            }
        });

        // Button to delete data from the table
        Button deleteBookButton = findViewById(R.id.button_delete_book);
        deleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDeleteConfirmationDialog();
            }
        });

        if (currentBookUri == null) {
            deleteBookButton.setVisibility(View.INVISIBLE);
        }
    }

    private void saveBook() {

        //Check if any of the edittext fields are empty
        if (bookTitleEditText.getText().toString().isEmpty() || bookPriceEditText.getText().toString().isEmpty() || bookQuantityTextView.getText().toString().isEmpty()
                || bookPublicationDateEditText.getText().toString().isEmpty() || supplierNameEditText.getText().toString().isEmpty() ||
                supplierPhoneNoEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter values for all the fields", Toast.LENGTH_SHORT).show();

        } else {
            // Check if any of the edit text fields are modified or not
            if (currentBookUri == null &&
                    TextUtils.isEmpty(bookTitleEditText.getText().toString().trim()) && TextUtils.isEmpty(bookPriceEditText.getText().toString().trim()) &&
                    bookQuantityTextView.getText() == "0" && TextUtils.isEmpty(bookPublicationDateEditText.getText().toString()) &&
                    TextUtils.isEmpty(supplierNameEditText.getText().toString().trim()) && TextUtils.isEmpty(supplierPhoneNoEditText.getText().toString().trim())
                    && category == BookEntry.CATEGORY_UNCLASSIFIED) {
                // Since no fields were modified, we can return early without creating a new book.
                // No need to create ContentValues and no need to do any ContentProvider operations.
                return;
            }

            // create instance of the bookhelper and open the database in writable mode

            ContentValues values = new ContentValues();
            values.put(BookEntry.COLUMN_BOOK_TITLE, bookTitleEditText.getText().toString().trim());
            values.put(BookEntry.COLUMN_BOOK_PRICE, bookPriceEditText.getText().toString().trim());
            values.put(BookEntry.COLUMN_BOOK_QUANTITY, bookQuantityTextView.getText().toString().trim());
            values.put(BookEntry.COLUMN_BOOK_CATEGORY, category);
            values.put(BookEntry.COLUMN_BOOK_PUBLICATION_DATE, bookPublicationDateEditText.getText().toString());
            values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplierNameEditText.getText().toString().trim());
            values.put(BookEntry.COLUMN_SUPPLIER_PHONENO, supplierPhoneNoEditText.getText().toString().trim());


            // Determine if this is a new or existing book by checking if currentBookUri is null or not
            if (currentBookUri == null) {
                // This is a NEW book, so insert a new book into the provider,
                // returning the content URI for the new book.
                Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

                // Show a toast message depending on whether or not the insertion was successful.
                if (newUri == null) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(this, getString(R.string.InserEdit_activity_insert_book_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.InserEdit_activity_insert_book_successful),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
                // If this a existing book simply update. Already the row yo update is identified
                // so pass null values to selection and selection args

                int rowsAffected = getContentResolver().update(currentBookUri, values, null, null);

                // Show a toast message depending on whether or not the update was successful.
                if (rowsAffected == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(this, getString(R.string.InserEdit_activity_update_book_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.InserEdit_activity_update_book_successful),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the book hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!bookHasChanged) {
                    NavUtils.navigateUpFromSameTask(InsertEditInventory.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(InsertEditInventory.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // If the book hasn't changed, continue with handling back button press
        if (!bookHasChanged) {
            super.onBackPressed();
            return;
        }
        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        // Since the editor shows all book attributes, define a projection that contains
        // all columns from the book table
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_TITLE,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_PUBLICATION_DATE,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONENO,
                BookEntry.COLUMN_BOOK_CATEGORY

        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                currentBookUri,         // Query the content URI for the current book
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {

            int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_TITLE);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int publishdateColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PUBLICATION_DATE);
            int categoryColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_CATEGORY);
            int quantityCOlumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhNoColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONENO);


            // Extract out the value from the Cursor for the given column index
            String title = cursor.getString(titleColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            int category = cursor.getInt(categoryColumnIndex);
            int quantity = cursor.getInt(quantityCOlumnIndex);
            String publishDate = cursor.getString(publishdateColumnIndex);
            String supplierName = cursor.getString(supplierNameColumnIndex);
            String supplierPhone = cursor.getString(supplierPhNoColumnIndex);


            // Update the views on the screen with the values from the database
            bookTitleEditText.setText(title);
            bookPriceEditText.setText(Integer.toString(price));
            bookQuantityTextView.setText(Integer.toString(quantity));
            bookQuantity = quantity;
            bookPublicationDateEditText.setText(publishDate);
            supplierNameEditText.setText(supplierName);
            supplierPhoneNoEditText.setText(supplierPhone);

            // Category is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 through 6).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (category) {
                case BookEntry.CATEGORY_FANTASY:
                    categorySpinner.setSelection(1);
                    break;
                case BookEntry.CATEGORY_SCIENCE:
                    categorySpinner.setSelection(2);
                    break;
                case BookEntry.CATEGORY_FICTION:
                    categorySpinner.setSelection(3);
                    break;
                case BookEntry.CATEGORY_HORROR:
                    categorySpinner.setSelection(4);
                    break;
                case BookEntry.CATEGORY_HISTORY:
                    categorySpinner.setSelection(5);
                    break;
                case BookEntry.CATEGORY_AUTOBIOGRAPHY:
                    categorySpinner.setSelection(6);
                    break;
                default:
                    categorySpinner.setSelection(0);
                    break;
            }
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        // If the loader is invalidated, clear out all the data from the input fields.
        bookTitleEditText.setText("");
        bookPriceEditText.setText("");
        bookQuantityTextView.setText("0");
        bookPublicationDateEditText.setText("");
        supplierNameEditText.setText("");
        supplierPhoneNoEditText.setText("");
        categorySpinner.setSelection(0); // Select "UNCLASSIFIED"
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
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

    private void deleteBook() {
        // Only perform the delete if this is an existing book.
        if (currentBookUri != null) {
            // Call the ContentResolver to delete the book at the given content URI.
            // Pass in null for the selection and selection args because the currentBookUri
            // content URI already identifies the book that is required
            int rowsDeleted = getContentResolver().delete(currentBookUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.InsertEdit_activity_delete_book_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.InsertEdit_activity_delete_book_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    /**
     * Prompt the user to confirm that they want to delete this book.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the book.
                deleteBook();
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

    //Method to increase quantity by one and check its not more than 500
    public void qtyIncrement() {

        bookQuantity = bookQuantity + 1;
        if (bookQuantity < 0) {
            bookQuantity = 0;
            Toast toast = Toast.makeText(this, "Quantity Cannot be less than 0", Toast.LENGTH_LONG);
            toast.show();
        }
        if (bookQuantity > 500) {
            bookQuantity = 500;
            Toast toast = Toast.makeText(this, "Quantity Cannot be more than 500", Toast.LENGTH_LONG);
            toast.show();
        }
        bookQuantityTextView.setText(String.valueOf(bookQuantity));
    }

    //Method to decrease the quantity and check it not less than zero
    public void qtyDecrement() {

        bookQuantity = bookQuantity - 1;
        if (bookQuantity <= 0) {
            bookQuantity = 1;
            Toast toast = Toast.makeText(getApplicationContext(), "Quantity Cannot be less than 1", Toast.LENGTH_LONG);
            toast.show();
        }
        if (bookQuantity > 500) {
            bookQuantity = 500;
            Toast toast = Toast.makeText(getApplicationContext(), "Quantity Cannot be more than 500", Toast.LENGTH_LONG);
            toast.show();
        }
        bookQuantityTextView.setText(String.valueOf(bookQuantity));
    }
}
