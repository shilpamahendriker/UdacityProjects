package com.example.avni.project1_quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Find the View that shows the Try Again button
        TextView tryAgain = (TextView) findViewById(R.id.buttonTryAgain);
        // Set a click listener on that View

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                intent.putExtra("PLAYER_NAME",extras.getString("PLAYER_NAME"));
                // Start the new activity
                startActivity(intent);
            }
        });
        // Find the View that shows the Home button
        TextView home = (TextView) findViewById(R.id.buttonHome);
        // Set a click listener on that View

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Retrieving the array of correct answers and player name from the intent
         */
        Bundle extras = getIntent().getExtras();
        String playerName = extras.getString("PLAYER_NAME");
        int[] correctAnswers = extras.getIntArray("CORRECT_ANSWERS_ARRAY");

        /**
         * Textview for activity header
         */

        TextView headertextView = (TextView) findViewById(R.id.resultsHeaderTextView);
        headertextView.setText(playerName + " " + getResources().getString(R.string.resultsHeaderText));

        /**
         * Displaying if the player answers question no.1 right or wrong
         */

        ImageView imageViewQ1 = (ImageView) findViewById(R.id.imageQ1);
        if (correctAnswers[0] == 1) {
            imageViewQ1.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ1.setImageResource(R.drawable.ic_wrong);

        }
        /**
         * Displaying if the player answers question no.2 right or wrong
         */


        ImageView imageViewQ2 = (ImageView) findViewById(R.id.imageQ2);
        if (correctAnswers[1] == 1) {
            imageViewQ2.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ2.setImageResource(R.drawable.ic_wrong);

        }
        /**
         * Displaying if the player answers question no.3 right or wrong
         */
        ImageView imageViewQ3 = (ImageView) findViewById(R.id.imageQ3);
        if (correctAnswers[2] == 1) {
            imageViewQ3.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ3.setImageResource(R.drawable.ic_wrong);
        }
        /**
         * Displaying if the player answers question no.4 right or wrong
         */
        ImageView imageViewQ4 = (ImageView) findViewById(R.id.imageQ4);
        if (correctAnswers[3] == 1) {
            imageViewQ4.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ4.setImageResource(R.drawable.ic_wrong);
        }
        /**
         * Displaying if the player answers question no.5 right or wrong
         */
        ImageView imageViewQ5 = (ImageView) findViewById(R.id.imageQ5);
        if (correctAnswers[4] == 1) {
            imageViewQ5.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ5.setImageResource(R.drawable.ic_wrong);
        }
        /**
         * Displaying if the player answers question no.6 right or wrong
         */
        ImageView imageViewQ6 = (ImageView) findViewById(R.id.imageQ6);
        if (correctAnswers[5] == 1) {
            imageViewQ6.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ6.setImageResource(R.drawable.ic_wrong);
        }
        /**
         * Displaying if the player answers question no.7 right or wrong
         */
        ImageView imageViewQ7 = (ImageView) findViewById(R.id.imageQ7);
        if (correctAnswers[6] == 1) {
            imageViewQ7.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ7.setImageResource(R.drawable.ic_wrong);
        }
        /**
         * Displaying if the player answers question no.8 right or wrong
         */
        ImageView imageViewQ8 = (ImageView) findViewById(R.id.imageQ8);
        if (correctAnswers[7] == 1) {
            imageViewQ8.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ8.setImageResource(R.drawable.ic_wrong);
        }
        /**
         * Displaying if the player answers question no.9 right or wrong
         */
        ImageView imageViewQ9 = (ImageView) findViewById(R.id.imageQ9);
        if (correctAnswers[8] == 1) {
            imageViewQ9.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ9.setImageResource(R.drawable.ic_wrong);
        }
        /**
         * Displaying if the player answers question no.10 right or wrong
         */
        ImageView imageViewQ10 = (ImageView) findViewById(R.id.imageQ10);
        if (correctAnswers[9] == 1) {
            imageViewQ10.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ10.setImageResource(R.drawable.ic_wrong);
        }

    }



}
