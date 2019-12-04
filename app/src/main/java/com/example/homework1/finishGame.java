package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.homework1.TheGame.END_SCORE;

public class finishGame extends AppCompatActivity {

    private EditText gameOver_TXT_score;
    private Button gameOver_BTN_Exit,gameOver_BTN_playAgain,gameOver_BTN_allScore;
    private int score;
    public static int numOfGames=0,curScore=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);

        Intent intent = getIntent();
        score = intent.getIntExtra(TheGame.END_SCORE,0);
        gameOver_TXT_score = findViewById(R.id.gameOver_TXT_score);
        gameOver_TXT_score.setText("Your score: "+score);
        gameOver_BTN_playAgain = findViewById(R.id.gameOver_BTN_playAgain);
        gameOver_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });

        gameOver_BTN_Exit = findViewById(R.id.gameOver_BTN_Exit);
        gameOver_BTN_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitGame();
            }
        });

        gameOver_BTN_allScore = findViewById(R.id.gameOver_BTN_allScore);
        gameOver_BTN_allScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allScores();
            }
        });
        if(numOfGames<5) {
            allScores.allTheScores[finishGame.numOfGames] = score;
        }
        curScore=score;
        numOfGames++;

    }

    public void startNewGame(){
        Intent intent = new Intent(this, TheGame.class);
        startActivity(intent);
    }
    public void exitGame(){
        finish();
        System.exit(0);
    }
    public void allScores(){
        Intent intent = new Intent(this, allScores.class);
        startActivity(intent);

    }
}
