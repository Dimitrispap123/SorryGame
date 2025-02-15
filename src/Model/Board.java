package Model;

import Model.Square.*;


import java.awt.*;

/**
 * In this class we are declaring tha attributes of the board
 */

public class Board {
    Square[][] pos;

    /**
     * In this method we are returning the square in the row i and column j
     * @param i is the row of the board with the square is located
     * @param j is the column of the board with the square is located
     * @return the square in the row i and column j
     */
    public Square getSquare(int i,int j){
        return pos[i][j];
    }

    /**
     * In this method we are initializing the board's squares with color and what type each square is
     * @post board initialized
     */
    public void initBoard(){
        pos = new Square[16][16];
        for(int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                pos[i][j]=new Simple_square(i,j,Color.white);
                if(j==2 && i<6 && i>0){
                    pos[i][j]=new Safe_zone(i,j,Color.red);
                    pos[i][j].setPos( 80, 20+i*40);

                }
                if(j==13 && i>9 &&i<15){
                    pos[i][j]=new Safe_zone(i,j,Color.yellow);
                }

                if(j==0 || j==15 || i==0 ||i==15) {


                    if (j == 0) {
                        if (i == 2 || i == 11) {
                            pos[i][j]= new End_slide(i,j,Color.green);
                        }
                        if ((i > 2 && i < 6) || (i > 11 && i < 14)) {
                            pos[i][j]= new Inside_slide(i,j,Color.green);
                        }
                        if (i == 6 || i == 14) {
                            pos[i][j]=new Start_slide(i,j, Color.green);
                        }
                        pos[i][j].setPos(0, 20 + i * 40);

                    } else if (j == 15) {
                        if (i == 1 || i == 9) {
                            pos[i][j]=new Start_slide(i,j, Color.blue);
                        }
                        if ((i > 1 && i < 4) || (i > 9 && i < 13)) {
                            pos[i][j]= new Inside_slide(i,j,Color.blue);
                        }
                        if (i == 4 || i == 13) {
                            pos[i][j]= new End_slide(i,j,Color.blue);
                        }
                        pos[i][j].setPos(600, 20 + i * 40);


                    } else if (i == 0) {
                        if (j == 1 || j == 9) {
                            pos[i][j]=new Start_slide(i,j, Color.red);
                        }
                        if ((j > 1 && j < 4) || (j > 9 && j < 13)) {
                            pos[i][j]= new Inside_slide(i,j,Color.red);
                        }
                        if (j == 4 || j == 13) {
                            pos[i][j]= new End_slide(i,j,Color.red);
                        }
                        pos[i][j].setPos(j * 40, 20);
                    } else {
                        if (j == 6 || j == 14) {

                            pos[i][j]=new Start_slide(i,j, Color.yellow);
                        }
                        if ((j > 2 && j < 6) || (j > 11 && j < 14)) {
                            pos[i][j]= new Inside_slide(i,j,Color.yellow);
                        }
                        if (j == 2 || j == 11) {
                            pos[i][j]= new End_slide(i,j,Color.yellow);
                        }
                        pos[i][j].setPos(j * 40, 620);
                    }
                }

            }
        }
    }

}
