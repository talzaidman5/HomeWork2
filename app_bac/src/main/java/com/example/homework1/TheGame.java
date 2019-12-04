package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.view.View.GONE;
import static com.example.homework1.R.drawable.play;

public class TheGame extends AppCompatActivity {

//    globalClass globalClass =  getApplicationContext()
    private final int NUMBER_OF_ROW = 5, NUM_OF_COL = 3;
    private int curLife = 3, positionPlayer = 1, positionSecondPlayer = -1,curCol1,countClick=0,score=1 ;
    private static int speed=1000;
    public static int cheoosenSpeed;
    private Button theGame_BTN_right, theGame_BTN_left,theGame_BTN_pause,theGame_BTN_exit;
    private ImageView[] gameLifes, playreMoves;
    private ImageView[][] matrixGame;
    private EditText theGame_ETD_score;
    final Handler handler = new Handler();
    private Runnable runnable;
    public static MediaPlayer ring = null;
    public static final String END_SCORE = "com.example.HomeWork1.TheGame.END_SCORE";
    private Boolean check = true;
    Animation animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_game);
        if(cheoosenSpeed != 0)
            speed = cheoosenSpeed;

        theGame_BTN_left = findViewById(R.id.theGame_BTN_left);
        theGame_BTN_right = findViewById(R.id.theGame_BTN_right);
        theGame_ETD_score = findViewById(R.id.theGame_ETD_score);
        theGame_BTN_pause = findViewById(R.id.theGame_BTN_pause);
        theGame_BTN_exit = findViewById(R.id.theGame_BTN_exit);
        playreMoves = new ImageView[]{
                findViewById(R.id.theGame_PIC_player1),
                findViewById(R.id.theGame_PIC_player2),
                findViewById(R.id.theGame_PIC_player3)
        };
        gameLifes = new ImageView[]{
                findViewById(R.id.like1),
                findViewById(R.id.like2),
                findViewById(R.id.like3)
        };
        matrixGame = new ImageView[][]{{
                findViewById(R.id.theGame_PIC_dog1), findViewById(R.id.theGame_PIC_dog2), findViewById(R.id.theGame_PIC_dog3)},
                {findViewById(R.id.theGame_PIC_dog4), findViewById(R.id.theGame_PIC_dog5), findViewById(R.id.theGame_PIC_dog6)},
                {findViewById(R.id.theGame_PIC_dog7), findViewById(R.id.theGame_PIC_dog8), findViewById(R.id.theGame_PIC_dog9)},
                {findViewById(R.id.theGame_PIC_dog10), findViewById(R.id.theGame_PIC_dog11), findViewById(R.id.theGame_PIC_dog12)},
                {findViewById(R.id.theGame_PIC_dog13), findViewById(R.id.theGame_PIC_dog14), findViewById(R.id.theGame_PIC_dog15)},
        };
        animator = AnimationUtils.loadAnimation(this, R.anim.anim_popup);

        startGame();
        start();
        theGame_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0 );
            }
        });
        theGame_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRight();
            }
        });
        theGame_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveLeft();
            }
        });
        theGame_BTN_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick++;
                if (countClick % 2 == 1) {
                    handler.removeCallbacksAndMessages(null);
                    theGame_BTN_pause.setBackgroundResource(play);
                    theGame_BTN_right.setClickable(false);
                    theGame_BTN_left.setClickable(false);
                    Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
                    ring.pause();
                }
                else {
                    handler.postDelayed(runnable,500);
                    Toast.makeText(getApplicationContext(), "Continue", Toast.LENGTH_SHORT).show();
                    theGame_BTN_right.setClickable(true);
                    theGame_BTN_left.setClickable(true);
                    ring.start();
                }
            }
        });
        ring = MediaPlayer.create(this,R.raw.game_song);
        ring.start();
    }
    public void start() {
        if (check == true) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    score += 10;
                    theGame_ETD_score.setText("Score: " + score);

                    if (positionSecondPlayer < NUMBER_OF_ROW - 1) {
                        positionSecondPlayer++;
                        matrixGame[positionSecondPlayer][curCol1].setVisibility(View.VISIBLE);

                        if (positionSecondPlayer != 0)
                            matrixGame[positionSecondPlayer - 1][curCol1].setVisibility(View.INVISIBLE);
                    } else
                        curCol1 = checkCol(curCol1, positionSecondPlayer);
                    start();
                };
            };
            if(speed > 300)
                speed-=20;
            handler.postDelayed(runnable, speed);}
    }


    public void startGame() {

        curCol1 = (int) (Math.random() * (3));
        for (int i = 0; i < matrixGame.length; i++) {
            for (int j = 0; j < matrixGame[i].length; j++) {
                matrixGame[i][j].setVisibility(View.INVISIBLE);
            }
        }
        matrixGame[0][curCol1].setVisibility(View.VISIBLE);
        playreMoves[0].setVisibility(View.INVISIBLE);
        playreMoves[1].setVisibility(View.VISIBLE);
        playreMoves[2].setVisibility(View.INVISIBLE);
        for (int i = 0; i < gameLifes.length; i++) {
            gameLifes[i].setVisibility(View.VISIBLE);
        }
    }

    public void moveRight() {

        positionPlayer++;
        if (positionPlayer > NUM_OF_COL - 1) {
            positionPlayer = 0;
            playreMoves[positionPlayer].setVisibility(View.VISIBLE);
            playreMoves[positionPlayer + NUM_OF_COL - 1].setVisibility(View.INVISIBLE);

        } else if (positionPlayer < matrixGame.length) {
            playreMoves[positionPlayer].setVisibility(View.VISIBLE);
            playreMoves[positionPlayer - 1].setVisibility(View.INVISIBLE);
        }
    }

    public void moveLeft() {

        positionPlayer--;
        if (positionPlayer < 0) {
            positionPlayer = NUM_OF_COL - 1;
            playreMoves[positionPlayer].setVisibility(View.VISIBLE);
            playreMoves[0].setVisibility(View.INVISIBLE);
        } else {
            playreMoves[positionPlayer].setVisibility(View.VISIBLE);
            playreMoves[positionPlayer + 1].setVisibility(View.INVISIBLE);
        }
    }

    public int checkCol(int colNum, int pos) {
        int rand;

        if (pos == NUMBER_OF_ROW-1) {
            do {
                rand = (int) (Math.random() * (3));
            } while (rand == colNum);

            checkIfLose();
            matrixGame[0][rand].setVisibility(View.VISIBLE);
            matrixGame[NUMBER_OF_ROW - 1][colNum].setVisibility(View.INVISIBLE);
            positionSecondPlayer = 0;
            return rand;
        }
        return colNum;
    }

    public void checkIfLose() {
        if (curCol1 == positionPlayer)
            if (curLife > 1) {
                gameLifes[curLife - 1].startAnimation(animator);
                gameLifes[curLife - 1].setVisibility(View.GONE);
                curLife--;
                MySignal.vibrate(this,1000);
            }
            else{
                finish();
                check=false;
                Intent intent = new Intent(this, finishGame.class);
                intent.putExtra(END_SCORE, score + "");
                startActivity(intent);
                ring.stop();
            }
    }
}




