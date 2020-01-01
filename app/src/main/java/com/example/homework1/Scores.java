package com.example.homework1;

public class Scores  implements Comparable{
    private String name;
    private int endScore;
    private double[]vectorLocation;

    public Scores(String name, int endScore,double[]vectorLocation) {
        if(name == null) {
            this.name = "Player";
            this.endScore = endScore;
            this.vectorLocation = vectorLocation;
        }
        else {
            this.name = name;
            this.endScore = endScore;
            this.vectorLocation = vectorLocation;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEndScore() {
        return endScore;
    }



    public double[] getVectorLocation() {
        if(vectorLocation!=null)
        return vectorLocation;
        else{
            double[]  arr = new double[1];
            return arr;

        }
    }

    public void setVectorLocation(double[] vectorLocation) {
        this.vectorLocation = vectorLocation;
    }

    @Override
    public int compareTo(Object o) {
        Scores s = (Scores)o;
        if(this.endScore == s.endScore)
            return 0;
        if(this.endScore > s.endScore)
            return -1;
        else
            return 1;
    }
}
