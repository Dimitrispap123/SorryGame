package Model.Square;

import Model.Board;
import Model.Pawn;

import java.awt.*;

/**
 * In this class we are setting the attributes of a square
 * @author Dimitris Papadopoulos csd4976
 */
public abstract class Square {

    /**
     * The color of the square
     */
    private Color color;
    /**
     * This saws us if a square has a pawn
     */
    boolean hasPawn;
    /**
     * The X position(row) of the square
     */
    private int posX;
    /**
     * The Y position(column) of the square
     */
    private  int posY;
    /**
     * The pawn
     */
    private Pawn pawn;
    /**
     *  In this method we are setting if a square has a pawn (true if it has, false if it doesn't)
     * @pre True or false must be given
     * @param flag true if a square has a pawn, false if it doesn't
     */

    public void setHasPawn(boolean flag){
        hasPawn = flag;
    }
    /**
     * In this method we are returning if the square has a pawn(true if it has, false if it doesn't)
     * @pre True or false must be set
     * @return  if the square has a pawn(true if it has, false if it doesn't)
     */
    public boolean getHasPawn(){
        return hasPawn;
    }

    /**
     *  In this method we are setting the position(X and Y) of the square
     * @pre A position(X and Y) must be given
     * @param posX is the color of the square
     */

    public void setPos(int posX,int posY){
        this.posX=posX;
        this.posY=posY;
    }
    /**
     * In this method we are returning the X position(row) of the square
     * @pre An X position must be set
     * @return the X position(row) of the square
     */

    public int getPosX() {
        return posX;
    }
    /**
     * In this method we are returning the Y position(column) of the square
     * @pre A Y position must be set
     * @return the Y position(column) of the square
     */

    public int getPosY() {
        return posY;
    }
    /**
     * In this method we are setting the Pawn on square
     * @post A Pawn must be given
     * @param p is the pawn of the square
     */
    public void setPawn(Pawn p){
        this.pawn=p;
    }
    /**
     * In this method we are returning the Pawn of a square
     * @pre A Pawn must be on the square
     * @return the Pawn of a square
     */
    public Pawn getPawn(){
        return pawn;
    }


    /**
     * In this method we are returning the color of the square
     * @pre A color must be set
     * @return the color of the square
     */

    public Color getColor() {
        return color;
    }
    /**
     * In this method we are setting the color of the square
     * @post A color must be given
     * @param color is the color of the square
     */

    public void setColor(Color color) {
        this.color = color;
    }

    
}
