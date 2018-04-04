package com.example.avni.project1_quiz;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle extras = getIntent().getExtras();
        String playerName = extras.getString("PLAYER_NAME");
        int[] correctAnswers = extras.getIntArray("CORRECT_ANSWERS_ARRAY");

        TextView headertextView = (TextView) findViewById(R.id.resultsHeaderTextView);
        headertextView.setText(playerName + " " + getResources().getString(R.string.resultsHeaderText));

        ImageView imageViewQ1 = (ImageView) findViewById(R.id.imageQ1);
        if (correctAnswers[0] == 1) {
            imageViewQ1.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ1.setImageResource(R.drawable.ic_wrong);

        }

        ImageView imageViewQ2 = (ImageView) findViewById(R.id.imageQ2);
        if (correctAnswers[1] == 1) {
            imageViewQ2.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ2.setImageResource(R.drawable.ic_wrong);

        }

        ImageView imageViewQ3 = (ImageView) findViewById(R.id.imageQ3);
        if (correctAnswers[2] == 1) {
            imageViewQ3.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ3.setImageResource(R.drawable.ic_wrong);
        }

        ImageView imageViewQ4 = (ImageView) findViewById(R.id.imageQ4);
        if (correctAnswers[3] == 1) {
            imageViewQ4.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ4.setImageResource(R.drawable.ic_wrong);
        }

        ImageView imageViewQ5 = (ImageView) findViewById(R.id.imageQ5);
        if (correctAnswers[4] == 1) {
            imageViewQ5.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ5.setImageResource(R.drawable.ic_wrong);
        }

        ImageView imageViewQ6 = (ImageView) findViewById(R.id.imageQ6);
        if (correctAnswers[5] == 1) {
            imageViewQ6.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ6.setImageResource(R.drawable.ic_wrong);
        }

        ImageView imageViewQ7 = (ImageView) findViewById(R.id.imageQ7);
        if (correctAnswers[6] == 1) {
            imageViewQ7.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ7.setImageResource(R.drawable.ic_wrong);
        }

        ImageView imageViewQ8 = (ImageView) findViewById(R.id.imageQ8);
        if (correctAnswers[7] == 1) {
            imageViewQ8.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ8.setImageResource(R.drawable.ic_wrong);
        }

        ImageView imageViewQ9 = (ImageView) findViewById(R.id.imageQ9);
        if (correctAnswers[8] == 1) {
            imageViewQ9.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ9.setImageResource(R.drawable.ic_wrong);
        }

        ImageView imageViewQ10 = (ImageView) findViewById(R.id.imageQ10);
        if (correctAnswers[9] == 1) {
            imageViewQ10.setImageResource(R.drawable.ic_right);
        } else {
            imageViewQ10.setImageResource(R.drawable.ic_wrong);
        }
       /* String questionText;
        LinearLayout MainLL = (LinearLayout)findViewById(R.id.mainLinearLayout);
        questionText = "(getResources().getString(R.string.Q";
        for(int x = 0; x < 10; x++){

            LinearLayout childLL= new LinearLayout(this);
            childLL.setOrientation(LinearLayout.VERTICAL);


            TextView text = new TextView(this);
            text.setText("hi");
            //text.setLayoutParams(new LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            text.setTextSize(16);
            childLL.addView(text);
            MainLL.addView(childLL);

            ImageView imageView = new ImageView(this);
            if (correctAnswers[x] == 1) {
                imageView.setImageResource(R.drawable.ic_right);
            } else {
                imageView.setImageResource(R.drawable.ic_wrong);

            }
            childLL.addView(imageView);
            MainLL.addView(childLL);

        }*/

    }
      public void goHome (View view){

          Intent intent = new Intent(Result.this, MainActivity.class);
          startActivity(intent);
        }

        public void tryAgain (View view){
            Bundle extras = getIntent().getExtras();
            Intent intent = new Intent(Result.this, QuizActivity.class);
            intent.putExtra("PLAYER_NAME",extras.getString("PLAYER_NAME"));
            startActivity(intent);

        }


}
