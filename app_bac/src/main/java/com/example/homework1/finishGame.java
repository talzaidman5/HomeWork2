package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class finishGame extends AppCompatActivity {

    private EditText gameOver_TXT_score;
    private Button gameOver_BTN_Exit,gameOver_BTN_playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);
        Intent intent = getIntent();
        String score = intent.getStringExtra(TheGame.END_SCORE);
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

    }

    public void startNewGame(){
        Intent intent = new Intent(this, TheGame.class);
        startActivity(intent);
    }
    public void exitGame(){
        finish();
        System.exit(0);
    }
}
