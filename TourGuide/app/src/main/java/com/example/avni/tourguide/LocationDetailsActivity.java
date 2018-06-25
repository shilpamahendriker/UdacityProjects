package com.example.avni.tourguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationDetailsActivity extends AppCompatActivity {
    String name,address,hours;
    int imageid,descId;

    @Override public void onBackPressed()
    {

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
             descId = extras.getInt("DESC");
             //pageId = extras.getInt("PAGEID");

            imageid = extras.getInt("IMAGEID");
        }

        TextView nameTextView  = findViewById(R.id.textViewName);
        nameTextView.setText(name);
        TextView addressTextView = findViewById(R.id.textViewAddress);
        addressTextView.setText(address);
        TextView hoursTextView = findViewById(R.id.textViewHours);
        hoursTextView.setText(hours);

        TextView descTextView = findViewById(R.id.textViewDesc);
        descTextView.setText(descId);

        ImageView image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.ic_launcher_background);


    }


}

