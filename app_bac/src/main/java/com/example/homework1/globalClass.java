package com.example.homework1;

import android.app.Application;
import android.media.MediaPlayer;

public class globalClass extends Application {

    private int gameSpeed;
    private MediaPlayer ring;

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public MediaPlayer getRing() {
        return ring;
    }

    public void setRing(MediaPlayer ring) {
        this.ring = ring;
    }
}
