package com.example.avni.tourguide;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationDetailsActivity extends AppCompatActivity {
    String name,address,hours,phoneno;
    int imageid,descId;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        setTitle("Explore Columbus Ohio");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("NAME");
             address = extras.getString("ADDRESS");
             hours = extras.getString("HOURS");
             phoneno = extras.getString("PHONE");
             descId = extras.getInt("DESC");
            imageid = extras.getInt("IMAGEID");
        }

        TextView nameTextView  = findViewById(R.id.textViewName);
        nameTextView.setText(name);
        TextView addressTextView = findViewById(R.id.textViewAddress);
        addressTextView.setText(address);
        TextView hoursTextView = findViewById(R.id.textViewHours);
        hoursTextView.setText(hours);
        TextView phoneTextView = findViewById(R.id.textViewPhone);
        phoneTextView.setText(phoneno);

        TextView descTextView = findViewById(R.id.textViewDesc);
        descTextView.setText(descId);

        ImageView image = findViewById(R.id.imageView);
        if (imageid == 0){
            image.setVisibility(View.GONE);
        }else {
            image.setImageResource(imageid);
        }

        ImageView phoneimageView = findViewById(R.id.imagePhone);
        phoneimageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + phoneno));
                startActivity(intent);
            }

        });

        ImageView mapIconimageView = findViewById(R.id.imageMapIcon);
        mapIconimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmapIntentUri = Uri.parse("geo:0,0?q="+ address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmapIntentUri);
                //set package to "com.google.android.apps.maps" so that only google maps is opened.
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }


}

