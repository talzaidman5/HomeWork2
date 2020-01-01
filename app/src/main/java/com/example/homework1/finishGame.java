package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class finishGame extends AppCompatActivity {

    private EditText gameOver_TXT_score;
    private Button gameOver_BTN_Exit, gameOver_BTN_playAgain, gameOver_BTN_allScore, b;
    private int score;
    private MySharedReference msr;
    private History history;
    private String JSON_KEY ="JSON_19";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);

        Intent intent = getIntent();
        score = intent.getIntExtra(TheGame.END_SCORE, 0);
        gameOver_TXT_score = findViewById(R.id.gameOver_TXT_score);
        gameOver_TXT_score.setText("Your score: " + score);
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

        msr = new MySharedReference(this);

        history = new History(msr.getString(JSON_KEY, "NULL"));
        history.addToAllScores(new Scores(Setting.name, score, MainApp.vetLocation));
        String json = new Gson().toJson(history.getAllScores());
        msr.putString(JSON_KEY, json);

    }


    public void startNewGame() {
        finish();
    }

    public void exitGame() {
        finish();
        System.exit(0);
    }

    public void allScores() {
        Intent intent = new Intent(this, allScores.class);
        finish();
        startActivity(intent);
    }


}







