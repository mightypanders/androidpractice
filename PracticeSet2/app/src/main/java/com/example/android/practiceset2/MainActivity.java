package com.example.android.practiceset2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int THREE_POINTER = 3;
    final int TWO_POINTER = 2;
    final int FREE_THROW = 1;

    public int scoreTeamA = 0;
    public int scoreTeamB = 0;

    public TextView ScoreViewA;
    public TextView ScoreViewB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScoreViewA = (TextView) findViewById(R.id.score_a);
        ScoreViewB = (TextView) findViewById(R.id.score_b);
    }

    public void displayScoreA() {
        ScoreViewA.setText(String.valueOf(scoreTeamA));
    }

    public void displayScoreB() {
        ScoreViewB.setText(String.valueOf(scoreTeamB));
    }

    public void pressButtonB(View view) {
        if (view == findViewById(R.id.score_b_plus_three))
            scoreTeamB += THREE_POINTER;
        if (view == findViewById(R.id.score_b_plus_one))
            scoreTeamB += TWO_POINTER;
        if (view == findViewById(R.id.score_b_freethrow))
            scoreTeamB += FREE_THROW;
        displayScoreB();
    }

    public void pressButtonA(View view) {
        if (view == findViewById(R.id.score_a_plus_three))
            scoreTeamA += THREE_POINTER;
        if (view == findViewById(R.id.score_a_plus_one))
            scoreTeamA += TWO_POINTER;
        if (view == findViewById(R.id.score_a_freethrow))
            scoreTeamA += FREE_THROW;
        displayScoreA();
    }

    public void resetScores(View view){
        this.scoreTeamA=0;
        this.scoreTeamB=0;
        displayScoreA();
        displayScoreB();
    }
}

