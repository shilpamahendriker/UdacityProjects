package com.example.avni.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int outTeamA = 0;
    int outTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForTeamA(scoreTeamA,outTeamA);
        displayForTeamB(scoreTeamB,outTeamB);
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score,int out) {
        TextView scoreView = (TextView) findViewById(R.id.scoreCountryA);
        scoreView.setText(String.valueOf(score)+ "/" + String.valueOf(out));
    }
    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score, int out) {
        TextView scoreView = (TextView) findViewById(R.id.scoreCountryB);
        scoreView.setText(String.valueOf(score) + "/" + String.valueOf(out) );
    }
    public void plusSixA(View view)
    {
        scoreTeamA = scoreTeamA + 6;
        displayForTeamA(scoreTeamA,outTeamA);
    }
    public void plusFourA(View view)
    {
        scoreTeamA = scoreTeamA + 4;
        displayForTeamA(scoreTeamA,outTeamA);
    }
    public void plusOneA(View view)
    {
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA,outTeamA);
    }
    public void outA(View view)
    {
        if (outTeamA >= 10) {
            TextView scoreView = (TextView) findViewById(R.id.scoreCountryA);
            scoreView.setText(String.valueOf(scoreTeamA));
        }else {
            outTeamA = outTeamA + 1;
            displayForTeamA(scoreTeamA, outTeamA);
        }
    }

    public void plusSixB(View view)
    {
        scoreTeamB = scoreTeamB + 6;
        displayForTeamB(scoreTeamB,outTeamB);
    }
    public void plusFourB(View view)
    {
        scoreTeamB = scoreTeamB + 4;
        displayForTeamB(scoreTeamB,outTeamB);
    }
    public void plusOneB(View view)
    {
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB,outTeamB);
    }
    public void outB(View view)
    {
        outTeamB = outTeamB + 1;
        if (outTeamB >= 10){
            TextView scoreView = (TextView) findViewById(R.id.scoreCountryB);
            scoreView.setText(String.valueOf(scoreTeamB) );

        }else {
            displayForTeamB(scoreTeamB, outTeamB);
        }
    }
    public void resetScores(View view)
    {
        scoreTeamA = 0;
        scoreTeamB = 0;
        outTeamB = 0;
        outTeamA=0;

        displayForTeamA(0,0);
        displayForTeamB(0,0);
    }

}
