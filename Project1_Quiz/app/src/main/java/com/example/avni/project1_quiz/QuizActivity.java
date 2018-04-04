package com.example.avni.project1_quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class QuizActivity extends AppCompatActivity {
    int[] correctAnswers = new int[10];
   // Bundle extras = getIntent().getExtras();
    int rightAnswers = 0;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private CheckBox Amazon, Nile, Yangtze, Yellow, China, India, Singapore, Brazil;
    private EditText editTextWater, editTextAfrica;
    private DatePicker datePicker;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        /**
         * Assigning Minimum and Maximum value for number picker
         */

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(11);
        numberPicker.setMinValue(1);

        /**
         * Retriving name entered in activity_main into this view
         */

        Bundle extras = getIntent().getExtras();
        TextView textView = (TextView) findViewById(R.id.welcomeText);
        textView.setText(getResources().getString( R.string.hiGreeting) + " " + extras.getString("PLAYER_NAME") + "," + getResources().getString(R.string.welcomeGreetings));



    }



    public void submitAnswers(View view) {
        /**
         * Initialing an integer array which catches the correctly answered questions and
         * its then passed to next activity through an intent to display quiz results.
         */

        int correctAnswers[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        /**
         * Checking the answer for question No. 1
         */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupQ1);
        radioButton = (RadioButton) findViewById(R.id.radioVenus);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == radioButton.getId()) {
            rightAnswers++;
            correctAnswers[0] = 1;
        }

        /**
         * Checking the answer for question No. 2
         */
        Amazon = (CheckBox) findViewById(R.id.checkBoxAmazon);
        Nile = (CheckBox) findViewById(R.id.checkBoxNile);
        Yangtze = (CheckBox) findViewById(R.id.checkBoxYangtze);
        Yellow = (CheckBox) findViewById(R.id.checkBoxYellow);

        if ((Amazon.isChecked() && Nile.isChecked()) && (!Yangtze.isChecked() && !Yellow.isChecked())) {
            rightAnswers++;
            correctAnswers[1] = 1;
        }
        /**
         * Checking the answer for question No. 8.Discarding any spaces entered and converting to lowecase
         */

        editTextWater = (EditText) findViewById(R.id.editTextWater);
        if (editTextWater.getText().toString().trim().toLowerCase().equals("water")) {
            rightAnswers++;
            correctAnswers[2] = 1;
        }
        /**
         * Checking the answer for question No. 4
         */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupQ4);
        radioButton = (RadioButton) findViewById(R.id.radioUniversalSB);
        selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == radioButton.getId()) {
            rightAnswers++;
            correctAnswers[3] = 1;
        }
        /**
         * Checking the answer for question No. 5
         */
        datePicker = (DatePicker) findViewById(R.id.DatePicker);
        if ((datePicker.getMonth() == 2) && (datePicker.getDayOfMonth() == 8)) {
            rightAnswers++;
            correctAnswers[4] = 1;
        }
        /**
         * Checking the answer for question No. 6
         */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupQ6);
        radioButton = (RadioButton) findViewById(R.id.radioTrue);
        selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == radioButton.getId()) {
            rightAnswers++;
            correctAnswers[5] = 1;
        }

        /**
         * Checking the answer for question No. 7
         */
        if (numberPicker.getValue() == 2) {
            rightAnswers++;
            correctAnswers[6] = 1;
        }

        /**
         * Checking the answer for question No. 8.Discarding any spaces entered and converting to lowercase
         */
        
        editTextAfrica = (EditText) findViewById(R.id.editTextAfrica);
        if (editTextAfrica.getText().toString().trim().toLowerCase().equals("africa")) {
            rightAnswers++;
            correctAnswers[7] = 1;
        }
        /**
         * Checking the answer for question No. 9
         */
        China = (CheckBox) findViewById(R.id.checkBoxChina);
        India = (CheckBox) findViewById(R.id.checkBoxIndia);
        Singapore = (CheckBox) findViewById(R.id.checkBoxSingapore);
        Brazil = (CheckBox) findViewById(R.id.checkBoxBrazil);

        if ((China.isChecked() && India.isChecked() && Singapore.isChecked()) && (!Brazil.isChecked())) {
            rightAnswers++;
            correctAnswers[8] = 1;
        }
        /**
         * Checking the answer for question No. 10
         */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupQ10);
        radioButton = (RadioButton) findViewById(R.id.radioNelson);
        selectedId = radioGroup.getCheckedRadioButtonId();
        /**
         * Checking the answer for question No. 1
         */
        if (selectedId == radioButton.getId()) {
            rightAnswers++;
            correctAnswers[9] = 1;
        }

        /**
         * Displaying in toast the result of the quiz
         */
        Toast toast = Toast.makeText(this, "You got " + rightAnswers + " right out of 10 Congratulations!!!", Toast.LENGTH_LONG);
        toast.show();
        rightAnswers = 0;

        Bundle extras = getIntent().getExtras();
        Intent resultIntent = new Intent(QuizActivity.this, Result.class);
        resultIntent.putExtra("PLAYER_NAME", extras.getString("PLAYER_NAME"));
        resultIntent.putExtra("CORRECT_ANSWERS_ARRAY", correctAnswers);
        startActivity(resultIntent);

    }
}
