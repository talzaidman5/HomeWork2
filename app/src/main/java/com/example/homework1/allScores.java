package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class allScores extends AppCompatActivity {

    private Button ScoreTable_BTN_back,allScore_BTN_tableRowScore1_1,allScore_BTN_tableRowScore2_1,
            allScore_BTN_tableRowScore3_1,allScore_BTN_tableRowScore4_1,allScore_BTN_tableRowScore5_1;
    private TextView[][] all;
    private MySharedReference msr;
    private History history;
    private final int MAX_SCORES = 5;
    private String JSON_KEY ="JSON_19";
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_scores);
        allScore_BTN_tableRowScore1_1 = findViewById(R.id.allScore_BTN_tableRowScore1_1);
        allScore_BTN_tableRowScore1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocation(1);
            }
        });
        allScore_BTN_tableRowScore2_1 = findViewById(R.id.allScore_BTN_tableRowScore2_1);
        allScore_BTN_tableRowScore2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocation(2);
            }
        });
        allScore_BTN_tableRowScore3_1 = findViewById(R.id.allScore_BTN_tableRowScore3_1);
        allScore_BTN_tableRowScore3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocation(3);
            }
        });
        allScore_BTN_tableRowScore4_1 = findViewById(R.id.allScore_BTN_tableRowScore4_1);
        allScore_BTN_tableRowScore4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocation(4);
            }
        });
        allScore_BTN_tableRowScore5_1 = findViewById(R.id.allScore_BTN_tableRowScore5_1);
        allScore_BTN_tableRowScore5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocation(5);
            }
        });
        ScoreTable_BTN_back = findViewById(R.id.ScoreTable_BTN_back);
        ScoreTable_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        all=new TextView[][] {
                {findViewById(R.id.allScore_TBL_tableRowScore1),findViewById(R.id.allScore_TBL_tableRowScore1_1), allScore_BTN_tableRowScore1_1},
                {findViewById(R.id.allScore_TBL_tableRowScore2),findViewById(R.id.allScore_TBL_tableRowScore2_1),allScore_BTN_tableRowScore2_1},
                {findViewById(R.id.allScore_TBL_tableRowScore3),findViewById(R.id.allScore_TBL_tableRowScore3_1),allScore_BTN_tableRowScore3_1},
                {findViewById(R.id.allScore_TBL_tableRowScore4),findViewById(R.id.allScore_TBL_tableRowScore4_1),allScore_BTN_tableRowScore4_1},
                {findViewById(R.id.allScore_TBL_tableRowScore5),findViewById(R.id.allScore_TBL_tableRowScore5_1),allScore_BTN_tableRowScore5_1}};

         mapFragment = new MapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.allScore_FRL_mapFragment, mapFragment);
        transaction.commit();
        Intent intent = getIntent();
        String score = intent.getStringExtra(TheGame.END_SCORE);
       addToTheTableScores();
    }

    private void openMapLocation(int index) {
        mapFragment.update(history.getAllScores().get(index-1).getVectorLocation()[0],history.getAllScores().get(index-1).getVectorLocation()[1]);
    }

    private void addToTheTableScores() {
        msr = new MySharedReference(this);
        String result = msr.getString(JSON_KEY, "NULL");
        history = new History(result);
        for (int i = 0; i <all.length ; i++) {
            all[i][2].setVisibility(View.INVISIBLE);
        }
        if(result != "NULL") {
            Type typeMyType = new TypeToken<ArrayList<Scores>>() {}.getType();
            ArrayList<Scores> myObject = new Gson().fromJson(result, typeMyType);
            history.setAllScores(myObject);
            history.sortTheScores(myObject);
            for (int i = 0; i < myObject.size() && i<MAX_SCORES; i++) {
                    all[i][0].setText(myObject.get(i).getName());
                    all[i][1].setText(myObject.get(i).getEndScore()+"");
                    all[i][2].setVisibility(View.VISIBLE);
                }
            }
    }

    public void backToMain(){
        Intent intent = new Intent(this, MainApp.class);
        finish();
        startActivity(intent);
    }
}
