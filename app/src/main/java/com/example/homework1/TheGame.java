package com.example.homework1;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.homework1.R.drawable.img_play;

public class TheGame extends AppCompatActivity {

    public static final String END_SCORE = "com.example.HomeWork1.TheGame.END_SCORE";
    private Button theGame_BTN_right, theGame_BTN_left, theGame_BTN_pause, theGame_BTN_exit;
    private int curLife = 3, countClick = 0, score = 0;
    private final int NUM_OF_ROW = 7, NUM_OF_COL = 5, ENEMY = 1, BUTTERFLY = 2;
    private ImageView[] gameLife, playerMoves;
    public MatrixGame matrixGame1, playerMoves1;
    public static boolean checkIfPlay = true, checkIfButtonOrSensor = true;
    public static MediaPlayer ring, ring2 = null;
    final Handler handler = new Handler();
    private EditText theGame_ETD_score;
    private ImageView[][] matrixGame;
    private Boolean check = true;
    private Runnable runnable;
    private static int speed;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_game);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("SPEED_VALUE")) {
                speed = extras.getInt("SPEED_VALUE");
            }
        }
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }


        theGame_BTN_left = findViewById(R.id.theGame_BTN_left);
        theGame_BTN_right = findViewById(R.id.theGame_BTN_right);
        theGame_ETD_score = findViewById(R.id.theGame_ETD_score);
        theGame_BTN_pause = findViewById(R.id.theGame_BTN_pause);
        theGame_BTN_exit = findViewById(R.id.theGame_BTN_exit);
        playerMoves = new ImageView[]{
                findViewById(R.id.theGame_PIC_player1),
                findViewById(R.id.theGame_PIC_player2),
                findViewById(R.id.theGame_PIC_player3),
                findViewById(R.id.theGame_PIC_player4),
                findViewById(R.id.theGame_PIC_player5)
        };
        gameLife = new ImageView[]{
                findViewById(R.id.like1),
                findViewById(R.id.like2),
                findViewById(R.id.like3)
        };
        matrixGame = new ImageView[][]{{
                findViewById(R.id.theGame_PIC_dog1), findViewById(R.id.theGame_PIC_dog2), findViewById(R.id.theGame_PIC_dog3), findViewById(R.id.theGame_PIC_dog4), findViewById(R.id.theGame_PIC_dog5)},
                {findViewById(R.id.theGame_PIC_dog6), findViewById(R.id.theGame_PIC_dog7), findViewById(R.id.theGame_PIC_dog8), findViewById(R.id.theGame_PIC_dog9), findViewById(R.id.theGame_PIC_dog10)},
                {findViewById(R.id.theGame_PIC_dog11), findViewById(R.id.theGame_PIC_dog12), findViewById(R.id.theGame_PIC_dog13), findViewById(R.id.theGame_PIC_dog14), findViewById(R.id.theGame_PIC_dog15)},
                {findViewById(R.id.theGame_PIC_dog16), findViewById(R.id.theGame_PIC_dog17), findViewById(R.id.theGame_PIC_dog18), findViewById(R.id.theGame_PIC_dog19), findViewById(R.id.theGame_PIC_dog20)},
                {findViewById(R.id.theGame_PIC_dog21), findViewById(R.id.theGame_PIC_dog22), findViewById(R.id.theGame_PIC_dog23), findViewById(R.id.theGame_PIC_dog24), findViewById(R.id.theGame_PIC_dog25)},
                {findViewById(R.id.theGame_PIC_dog26), findViewById(R.id.theGame_PIC_dog27), findViewById(R.id.theGame_PIC_dog28), findViewById(R.id.theGame_PIC_dog29), findViewById(R.id.theGame_PIC_dog30)}};
        startGame();
        start();

        theGame_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
        theGame_BTN_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick++;
                if (countClick % 2 == 1) {
                    handler.removeCallbacksAndMessages(null);
                    theGame_BTN_pause.setBackgroundResource(img_play);
                    theGame_BTN_right.setClickable(false);
                    theGame_BTN_left.setClickable(false);
                    Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
                    if (checkIfPlay)
                        ring.pause();
                } else {
                    handler.postDelayed(runnable, 500);
                    Toast.makeText(getApplicationContext(), "Continue", Toast.LENGTH_SHORT).show();
                    theGame_BTN_right.setClickable(true);
                    theGame_BTN_left.setClickable(true);
                    ring.start();
                }
            }
        });
        if (checkIfPlay) {
            ring = MediaPlayer.create(this, R.raw.game_song);
            ring.start();
        }
        if (!checkIfButtonOrSensor) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                mSensorManager.registerListener(sensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
            theGame_BTN_right.setVisibility(View.INVISIBLE);
            theGame_BTN_left.setVisibility(View.INVISIBLE);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                mSensorManager.unregisterListener(sensorEventListener);
            }
            theGame_BTN_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveLeft();
                }
            });
            theGame_BTN_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveRight();
                }
            });
            theGame_BTN_right.setVisibility(View.VISIBLE);
            theGame_BTN_left.setVisibility(View.VISIBLE);
        }
    }





    public void start() {
        if (check == true) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    score += 5;
                    if ( score% 100 == 0){
                        matrixGame1.addButterfly();
                        refreshGame();
                    }
                    theGame_ETD_score.setText("Score: " + score);
                    int ifAdd = matrixGame1.moveTheEnemy();
                    if(ifAdd == 1) {
                        matrixGame1.addNewEnemy();
                        matrixGame1.numOfEnemy++;
                    }
                    refreshGame();
                    if(check() == true) {
                        checkIfLose();
                        if(matrixGame1.numOfEnemy < 2) {
                            matrixGame1.addNewEnemy();
                            matrixGame1.numOfEnemy++;
                        }
                    }
                    start();
                };
            };
            if(speed > 400)
                speed-=30;
            handler.postDelayed(runnable, speed);
        }
    }

    public void refreshGame(){

        int [][] math = matrixGame1.getMatrixGame();
        for (int i = 0; i < matrixGame.length; i++) {
            for (int j = 0; j < matrixGame[i].length; j++) {
                if(math[i][j] == 0)
                    matrixGame[i][j].setVisibility(View.INVISIBLE);
                else if(math[i][j] == ENEMY)
                    matrixGame[i][j].setVisibility(View.VISIBLE);
                else if (math[i][j] == BUTTERFLY){
                    matrixGame[i][j].setVisibility(View.VISIBLE);
                    matrixGame[i][j].setImageResource(R.drawable.img_butterflies);
                }
            }
        }
        int [] mat = playerMoves1.getPlayerMove();
        for (int i = 0; i < NUM_OF_COL; i++) {
            if(mat[i] == 0)
                playerMoves[i].setVisibility(View.INVISIBLE);
            else {
                playerMoves[i].setVisibility(View.VISIBLE);
                playerMoves1.setPositionPlayer(i);
            }
        }
    }
    public void startGame() {

        matrixGame1 = new MatrixGame(NUM_OF_COL,NUM_OF_ROW);
        matrixGame1.initMatrix(NUM_OF_COL,NUM_OF_ROW);
        playerMoves1 = new MatrixGame(NUM_OF_COL,1);
        playerMoves1.initPlayer(NUM_OF_COL);
        refreshGame();
    }
    public void moveRight() {
        playerMoves1.moveRight();
        refreshGame();
    }
    public void moveLeft() {
        playerMoves1.moveLeft();
        refreshGame();

    }
    public void checkIfLose() {

        if (matrixGame1.getMatrixGame()[NUM_OF_ROW-1][playerMoves1.getPositionPlayer()] == ENEMY) {
            if (curLife > 1) {
                curLife--;
                MySignal.vibrate(this, 300);
                MySignal.animateHearts(gameLife[curLife]);
            } else {
                gameLife[curLife - 1].setVisibility(View.INVISIBLE);
                finish();
                check = false;
                MySignal.vibrate(this, 300);
                Intent intent = new Intent(this, finishGame.class);
                intent.putExtra(END_SCORE, score);
                finish();
                startActivity(intent);
                if(checkIfPlay)
                    ring.stop();
            }
        }
        else if(matrixGame1.getMatrixGame()[NUM_OF_ROW-1][playerMoves1.getPositionPlayer()] == BUTTERFLY) {
            score += 30;
            Toast.makeText(getApplicationContext(), "Added 30 coin", Toast.LENGTH_SHORT).show();
            matrixGame1.getMatrixGame()[NUM_OF_ROW-1][playerMoves1.getPositionPlayer()] = 0;
            ring2 = MediaPlayer.create(this, R.raw.money);
            MySignal.vibrate(this, 200);
            if (checkIfPlay == true)
                ring2.start();

        }
        for (int i = 0; i <NUM_OF_COL ; i++)
            matrixGame1.getMatrixGame()[NUM_OF_ROW-1][i] = 0;

        MatrixGame.numOfEnemy --;
        refreshGame();
    }
    public void backToMain(){
        if (checkIfPlay == true)
            ring.stop();
        check =false;
        finish();
    }


    private  boolean check() {
        int mat[][]= matrixGame1.getMatrixGame();
        for (int i = 0; i <NUM_OF_COL ; i++) {
            if(mat[NUM_OF_ROW-1][i] == 1 )
                return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
        if(checkIfPlay)
            ring.stop();
        check= true;
    }
    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
                x = event.values[0];
            }

            if (x > -2 && x < 2) {
                playerMoves1.getPlayerMove()[playerMoves1.getPositionPlayer()] = 0;
                playerMoves1.getPlayerMove()[2] = 1;
            } else if (x >= 2 && x < 7) {
                playerMoves1.getPlayerMove()[playerMoves1.getPositionPlayer()] = 0;
                playerMoves1.getPlayerMove()[1] = 1;
            } else if (x >= 7 &&  x < 10) {
                playerMoves1.getPlayerMove()[playerMoves1.getPositionPlayer()] = 0;
                playerMoves1.getPlayerMove()[0] = 1;
            } else if (x >= -8 && x < -2) {
                playerMoves1.getPlayerMove()[playerMoves1.getPositionPlayer()] = 0;
                playerMoves1.getPlayerMove()[3] = 1;
            } else if (x >= -10 && x < -8) {
                playerMoves1.getPlayerMove()[playerMoves1.getPositionPlayer()] = 0;
                playerMoves1.getPlayerMove()[4] = 1;
            }
            refreshGame();
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}




