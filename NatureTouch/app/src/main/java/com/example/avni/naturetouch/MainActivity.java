package com.example.avni.naturetouch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.layout_land);
        }

        /** Making facebook icon clickable and redirecting to facebook page on browser*/
        ImageView fbImageView = (ImageView)findViewById(R.id.image_fb);
        fbImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.facebook.com"));
                startActivity(intent);
            }

        });
        /** Making twitter icon clickable and redirecting to twitter page on default browser*/
        ImageView twiImageView = (ImageView)findViewById(R.id.image_twitter);
        twiImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.twitter.com"));
                startActivity(intent);
            }

        });
        /** Making phone icon clickable and onclick opens phone dialer app*/
        ImageView phoneimageView = (ImageView)findViewById(R.id.image_phone);
        phoneimageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:614 000 0000"));
                startActivity(intent);
            }

        });
        /** Making web icon clickable and onclick opens nature toouch website on the browser*/
        ImageView websiteimageView = (ImageView)findViewById(R.id.image_website);
        websiteimageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.NatureTouchMedicines.com"));
                startActivity(intent);
            }

        });
        /** Making map icon clickable and onclick opens googlemaps app with the specified address*/
        ImageView mapimageView = (ImageView)findViewById(R.id.image_location);
        mapimageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String map = "http://maps.google.co.in/maps?q=" + "1080 Obetz Rd, Columbus, OH 43207" ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(intent);
            }

        });
        /** Making email icon clickable and onclick opens email app with the specified email address*/
        ImageView emailimageView = (ImageView)findViewById(R.id.image_email);
        emailimageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:contactus@naturetouch.com")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Please enter your message...");
                if (intent.resolveActivity(getPackageManager()) != null) {
                     startActivity(intent);
                }
            }

        });


    }
    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
       // Inflate the menu items for use in the action bar
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main_activity_actions,menu);
       return super.onCreateOptionsMenu(menu);
   }



}
