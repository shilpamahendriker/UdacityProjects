package com.example.avni.inventoryapp;

import android.content.Intent;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.avni.inventoryapp.data.BookContract.BookEntry;

import com.example.avni.inventoryapp.data.BookContract;

public class SearchInventory extends AppCompatActivity {

    private Spinner categorySpinner;

    private String titleText = null;

    private Integer categorySearch = null;

    private String priceGreaterThan = null;

    private String priceLessThan = null;

    private String quantityGreaterThan = null;

    private String quantityLessThan = null;


    private int category = BookEntry.CATEGORY_UNCLASSIFIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_inventory);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        categorySpinner = findViewById(R.id.spinner_category);
        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        categorySpinner.setAdapter(categorySpinnerAdapter);

        // Set the integer mSelected to the constant values
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.unclassified))) {
                        category = BookEntry.CATEGORY_UNCLASSIFIED; // Unclassified
                    } else if (selection.equals(getString(R.string.fantasy))) {
                        category = BookEntry.CATEGORY_FANTASY; // FANTASY
                    } else if (selection.equals(getString(R.string.science))) {
                        category = BookEntry.CATEGORY_SCIENCE; // SCIENCE
                    } else if (selection.equals(getString(R.string.fiction))) {
                        category = BookEntry.CATEGORY_FICTION;// FICTION
                    } else if (selection.equals(getString(R.string.horror))) {
                        category = BookEntry.CATEGORY_HORROR; // HORROR
                    } else if (selection.equals(getString(R.string.history))) {
                        category = BookEntry.CATEGORY_HISTORY; // HISTORY
                    } else if (selection.equals(getString(R.string.autobiography))) {
                        category = BookEntry.CATEGORY_AUTOBIOGRAPHY; // AUTOBIOGRAPHY
                    }
                }
            }

            // assign default value when nothing is selected on the spinner
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = 0; // Unclassified
            }
        });

        //Create onclicklistner on the search button , create intent to Main activity and pass values
        // of title text keyed in ,category selected and new string variable fromSearch to check the
        // main activity is being loaded as a result of search Inventory intent
        final EditText editTitleText = findViewById(R.id.edit_search_book_title);
        Button searchButton = findViewById(R.id.button_search_book);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titleText = editTitleText.getText().toString().trim();
                categorySearch = category;

                Intent searchInventoryIntent = new Intent(SearchInventory.this, MainActivity.class);
                searchInventoryIntent.putExtra("titleSearchText", titleText);
                searchInventoryIntent.putExtra("categorySearch", Integer.toString(categorySearch));
                searchInventoryIntent.putExtra("fromSearch", "true");
                startActivity(searchInventoryIntent);
            }
        });
    }
}
