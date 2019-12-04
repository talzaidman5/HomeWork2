package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainApp extends AppCompatActivity {


    private Button main_BTN_NewGame;
    private Button main_BTN_settings;
    private Button main_BTN_scoreTable;
    private Button main_BTN_exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer ring= MediaPlayer.create(this,R.raw.start_song);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        ring.start();

        main_BTN_NewGame=findViewById(R.id.main_BTN_NewGame);
        main_BTN_NewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ring.stop();
                openNewGame();
            }
        });

        main_BTN_settings=findViewById(R.id.main_BTN_settings);
        main_BTN_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        main_BTN_exit = findViewById(R.id.main_BTN_exit);
        main_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        main_BTN_scoreTable=findViewById(R.id.main_BTN_scoreTable);
        main_BTN_scoreTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoreTable();
            }
        });
        Log.d("","");

    }
   public void openNewGame(){
       Intent intent = new Intent(this, TheGame.class);
       startActivity(intent);
   }
    public void openSettings(){
        Intent intent1 = new Intent(this, Settings.class);
        startActivity(intent1);
    }
    public void openScoreTable(){
        Intent intent = new Intent(this, allScores.class);
        startActivity(intent);


    }

}
