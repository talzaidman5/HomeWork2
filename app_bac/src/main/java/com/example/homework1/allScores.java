package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class allScores extends AppCompatActivity {

    private Button ScoreTable_BTN_back;
    private EditText allScore;
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

        allScore = findViewById(R.id.allScore);
    }

    public void backToMain(){
        Intent intent = new Intent(this, MainApp.class);
        startActivity(intent);
    }
}
