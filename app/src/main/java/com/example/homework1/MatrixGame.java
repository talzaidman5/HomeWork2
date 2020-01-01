package com.example.homework1;

public class MatrixGame {

    private int numberOfRow,numberOfCol;
    private  int [][] matrixGame;
    private  int [] playerMove;
    private  int positionPlayer;
    public static int numOfEnemy;

    public MatrixGame(int countCol, int countRow){
        this.numberOfCol = countCol;
        this.numberOfRow = countRow;
        this.matrixGame = new int[countRow][countCol];
        this.playerMove = new int [countCol];
        this.positionPlayer = countCol/2;
        this.numOfEnemy = 1;
    }
    public int[][] getMatrixGame() {
        return matrixGame;
    }


    public int[] getPlayerMove() {
        return playerMove;
    }

    public int getPositionPlayer() {
        return positionPlayer;
    }

    public void setPositionPlayer(int positionPlayer) {
        this.positionPlayer = positionPlayer;
    }

    public void initMatrix(int countCol, int countRow) {
        for (int i = 0; i < countRow; i++) {
            for (int j = 0; j < countCol; j++) {
                matrixGame[i][j] = 0;
            }
        }
        matrixGame[0][(int) (Math.random() * (countCol))] =1;
    }

    public void initPlayer(int countCol) {
        for (int i = 0; i <countCol ; i++) {
            playerMove[i] = 0;
        }
        playerMove[countCol/2] = 1;
        setPositionPlayer(countCol/2);
    }


    public void moveRight() {
        positionPlayer++;
        if (positionPlayer > numberOfCol - 1) {
            positionPlayer = 0;
            playerMove[positionPlayer]=1;
            playerMove[positionPlayer + numberOfCol - 1]=0;

        } else if (positionPlayer < numberOfCol) {
            playerMove[positionPlayer]=1;
            playerMove[positionPlayer - 1]=0;
        }
    }

    public void moveLeft(){
        positionPlayer--;
        if (positionPlayer < 0) {
            positionPlayer = numberOfCol - 1;
            playerMove[positionPlayer] = 1;
            playerMove[0]=0;
        } else {
            playerMove[positionPlayer] = 1;
            playerMove[positionPlayer + 1] = 0;
        }
    }

    public int moveTheEnemy(){
        int ifAdd=0;
        for (int i = numberOfRow -2; i >= 0 ; i--) {
            for (int j = numberOfCol -1; j >= 0 ; j--) {
                if(matrixGame[i][j]==1) {
                    if(i ==3 && numOfEnemy < 2) {
                        ifAdd =1;
                    }
                    matrixGame[i+1][j] = 1;
                    matrixGame[i][j] = 0;
                }
                else if( matrixGame[i][j] == 2){
                    matrixGame[i+1][j] = 2;
                    matrixGame[i][j] = 0;
                }
            }
        }
        return ifAdd;
    }



    private boolean checkIfHaveAnEnemyInCol(int j) {
        for (int i = 0; i < numberOfRow; i++) {
            if(matrixGame[i][j] == 1)
                return true;
        }

        return false;
    }

    public void moveToXPath(float x) {
        if( x > -2 && x < 2){
            playerMove[positionPlayer]=0;
            playerMove[2] =1;
        }
        else if( x > 2 && x < 6){
            playerMove[positionPlayer]=0;
            playerMove[1] =1;
        }
        else if( x > 6){
            playerMove[positionPlayer]=0;
            playerMove[0] =1;
        }

        else if( x > -4 && x < -2){
            playerMove[positionPlayer]=0;
            playerMove[3] =1;
        }
        else if( x > -10 && x < -4){
            playerMove[positionPlayer]=0;
            playerMove[4] =1;
        }

    }

    public void addNewEnemy() {
        int rand;
        do {
            rand =(int) (Math.random() * (numberOfCol));
            if(checkIfHaveAnEnemyInCol(rand) == false) {
                matrixGame[0][rand] = 1;
                return;
            }
        }while (checkIfHaveAnEnemyInCol(rand));

    }

    public void addButterfly() {
        int rand;
        do {
            rand = (int) (Math.random() * (numberOfCol));
            if(checkIfHaveAnEnemyInCol(rand) == false) {
                matrixGame[0][rand] = 2;
                return;
            }
        }while (checkIfHaveAnEnemyInCol(rand));

    }
}
