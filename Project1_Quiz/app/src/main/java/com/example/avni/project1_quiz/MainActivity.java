package com.example.avni.project1_quiz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main_horizontal);
        }

    }
    public void startButton(View view) {


        EditText editText = (EditText)findViewById(R.id.editTextName);
        if (editText.getText().toString().equals("")){
            Toast toast = Toast.makeText(this,"Please enter your name",Toast.LENGTH_LONG);
            toast.show();
        } else {
            Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
            quizIntent.putExtra("PLAYER_NAME", editText.getText().toString());
            startActivity(quizIntent);
        }
    }

}
