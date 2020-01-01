package com.example.homework1;

import android.os.Build;

import com.google.gson.Gson;

import java.util.ArrayList;

public class History implements Comparable{
    private ArrayList<Scores> allScores;


    public History(String str) {
        if (str.equals("NULL"))
            allScores = new ArrayList<Scores>();
        else
            allScores = (new Gson().fromJson(str, ArrayList.class));

    }

    public void addToAllScores(Scores score) {
        allScores.add(score);
    }

    public ArrayList<Scores> getAllScores() {
        return allScores;
    }

    public void sortTheScores(ArrayList<Scores> arrayList) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            arrayList.sort(null);
    }

    public void setAllScores(ArrayList<Scores> allScores) {
        this.allScores = allScores;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}