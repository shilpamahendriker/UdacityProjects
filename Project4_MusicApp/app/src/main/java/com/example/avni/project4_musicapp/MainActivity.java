package com.example.avni.project4_musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the View that shows the numbers category
        TextView artist = (TextView) findViewById(R.id.text_artist);

        // Set a click listener on that View
        artist.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                String artist = "Artist";
                Intent artistIntent = new Intent(MainActivity.this, FullPlaylist.class);
                artistIntent.putExtra("key", artist);
                // Start the new activity
                startActivity(artistIntent);
            }
        });
        TextView album = (TextView) findViewById(R.id.text_album);

        // Set a click listener on that View
        album.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                String album = "Album";
                Intent albumIntent = new Intent(MainActivity.this, FullPlaylist.class);
                albumIntent.putExtra("key", album);
                // Start the new activity
                startActivity(albumIntent);
            }
        });
        TextView genre = (TextView) findViewById(R.id.text_genre);
        genre.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                String genre = "Genre";
                Intent genreIntent = new Intent(MainActivity.this, FullPlaylist.class);
                genreIntent.putExtra("key", genre);
                // Start the new activity
                startActivity(genreIntent);
            }
        });
        TextView topCharts = (TextView) findViewById(R.id.text_topCharts);
        topCharts.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                String topcharts = "TopCharts";
                Intent topChartsIntent = new Intent(MainActivity.this, FullPlaylist.class);
                topChartsIntent.putExtra("key", topcharts);
                // Start the new activity
                startActivity(topChartsIntent);
            }
        });


        }

    }
