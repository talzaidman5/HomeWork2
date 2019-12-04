package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class allScores extends AppCompatActivity {

    private Button ScoreTable_BTN_back;
    public static int [] allTheScores = new int[5] ;
    private TextView[] all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_scores);

        ScoreTable_BTN_back = findViewById(R.id.ScoreTable_BTN_back);
        ScoreTable_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
        all=new TextView[] {
                findViewById(R.id.allScore_TBL_tableRowScore1),
                findViewById(R.id.allScore_TBL_tableRowScore2),
                findViewById(R.id.allScore_TBL_tableRowScore3),
                findViewById(R.id.allScore_TBL_tableRowScore4),
                findViewById(R.id.allScore_TBL_tableRowScore5)
        };
        Intent intent = getIntent();
        String score = intent.getStringExtra(TheGame.END_SCORE);
        addToTheTableScores();
    }

    private void addToTheTableScores() {
        if (finishGame.numOfGames != 0) {

            for (int i = 0; i <allTheScores.length; i++)
                if(allTheScores[i] != 0)
                   all[i].setText(allTheScores[i] + "");
        }
        if(finishGame.numOfGames >= 5){
            findPlaceToTheScore(finishGame.curScore);
            Arrays.sort(allTheScores);
        }
    }

    private void findPlaceToTheScore(int curScore) {
        int min = Integer.MAX_VALUE, minIndex=0;

        for (int i = 0; i <allTheScores.length ; i++) {
            if(allTheScores[i] == curScore)
                return;
            if(allTheScores[i]<min) {
                min = allTheScores[i];
                minIndex = i;
            }
        }
        allTheScores[minIndex]=min;
    }

    public void backToMain(){
        Intent intent = new Intent(this, MainApp.class);
        startActivity(intent);
    }
}
