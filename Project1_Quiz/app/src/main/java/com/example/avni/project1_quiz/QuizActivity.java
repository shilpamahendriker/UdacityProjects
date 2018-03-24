package com.example.avni.project1_quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class QuizActivity extends AppCompatActivity {
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
        String welcomeText = "Hi " + extras.getString("Name") + ", welcome to the quiz";
        textView.setText(welcomeText);


    }

    int rightAnswers = 0;

    /**
     * Checking the answer for question No. 1
     */

    public void submitAnswers(View view) {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupQ1);
        radioButton = (RadioButton) findViewById(R.id.radioVenus);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == radioButton.getId()) {
            rightAnswers++;
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
        }
        /**
         * Checking the answer for question No. 3
         */

        editTextWater = (EditText) findViewById(R.id.editTextWater);
        if (editTextWater.getText().toString().equals("Water")) {
            rightAnswers++;
        }
        /**
         * Checking the answer for question No. 4
         */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupQ4);
        radioButton = (RadioButton) findViewById(R.id.radioUniversalSB);
        selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == radioButton.getId()) {
            rightAnswers++;
        }
        /**
         * Checking the answer for question No. 5
         */
        datePicker = (DatePicker) findViewById(R.id.DatePicker);
        if ((datePicker.getMonth() == 2) && (datePicker.getDayOfMonth() == 8)) {
            rightAnswers++;
        }
        /**
         * Checking the answer for question No. 6
         */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupQ6);
        radioButton = (RadioButton) findViewById(R.id.radioTrue);
        selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == radioButton.getId()) {
            rightAnswers++;
        }

        /**
         * Checking the answer for question No. 7
         */
        if (numberPicker.getValue() == 2) {
            rightAnswers++;
        }

        /**
         * Checking the answer for question No. 8
         */
        editTextAfrica = (EditText) findViewById(R.id.editTextAfrica);
        if (editTextAfrica.getText().toString().equals("Africa")) {
            rightAnswers++;
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
        }

        /**
         * Displaying in toast the result of the quiz
         */
        Toast toast = Toast.makeText(this, "You got" + rightAnswers + " on 10 Congratulations!!!", Toast.LENGTH_LONG);
        toast.show();
        rightAnswers = 0;

    }
}
