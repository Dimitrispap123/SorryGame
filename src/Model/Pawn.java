package Model;

import java.awt.*;

/**
 * In this class we are declaring the elements of a pawn
 * @author Dimitris Papadopoulos csd4976
 */
public  class Pawn {
    /**
     * The color of the pawn
     */
    private final Color color;
    /**
     * The X position of the pawn
     */
    private int posX;
    /**
     * The Y position of the pawn
     */
    private int posY;
    /**
     * The number of the pawn
     */
    private final int num;
    /**
     * The home status of the pawn
     */
    private boolean homeStatus;
    /**
     * The status of the pawn
     */
    private boolean pawnOn;
    /**
     * The start status of the pawn
     */
    private boolean sendStart;
    /**
     * Constructs a new Pawn and sets its color and position
     * @pre The color and the integers must be valid
     * @param color is the color of the pawn
     * @param posX is the position of the pawn
     * @param posY is the position of the pawn
     * @param num is the number of the pawn
     * @post Constructs a new Pawn setting its color, position and number
     */
    public Pawn(Color color,int posX,int posY,int num) {
        this.color = color;
        this.posX= posX;
        this.posY=posY;
        this.num=num;
        pawnOn=false;
        homeStatus=false;
        sendStart=false;
    }

    /**
     * This method sets the start status of pawn if true then it has to go to the start
     * @post if true pawn has to go the start
     * @param sendStart the start status of the apwn
     */
    public void setSendStart(boolean sendStart){
        this.sendStart=sendStart;
    }

    /**
     *This method returns the start status of pawn if true then it has to go to the start
     * @pre sendStart must be set
     * @return the start status of pawn if true then it has to go to the start
     */
    public boolean getSendStart(){
        return sendStart;
    }

    /**
     * this method returns the number of the pawn
     * @pre the number must be set
     * @return the number of the pawn
     */
    public int getNum(){
        return num;
    }

    /**
     *This method sets true if the pawn is enabled or false otherwise
     * @pre A pawn status(true/false) must be given
     * @post if it is true is enabled, if it is false is disabled
     * @param pawnOn show us if the pawn is enabled
     */
    public void setPawnOn(boolean pawnOn){
        this.pawnOn=pawnOn;
    }

    /**
     *This method returns the status of the pawn whether it is enabled or not
     * @pre A pawn status(true/false) must be set
     * @return the status of the pawn whether it is enabled or not
     */
    public boolean getPawnOn(){
        return pawnOn;
    }
    /**
     * This method sets the position X of the pawn
     * @pre A position X must be given
     * @param posX is the color of the piece
     */

    public void setPosX(int posX) {
        this.posX = posX;
    }
    /**
     * This method sets the position Y of the pawn
     * @pre A position Y must be given
     * @param posY is the color of the piece
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    /**
     * This method returns the X position of the pawn
     * @pre The position X must be set
     * @return the X position of the pawn
     */
    public int getPosX() {
        return posX;
    }
    /**
     * This method returns the Y position of the pawn
     * @pre The position Y must be set
     * @return the Y position of the pawn
     */
    public int getPosY() {
        return posY;
    }
    /**
     * This method sets the home status of the pawn(true if it is at home/false if it is not)
     * @pre A home status(true/false) must be given
     * @param homeStatus is the home status of the pawn
     */
    public void setHomeStatus(boolean homeStatus){
        this.homeStatus=homeStatus;

    }
    /**
     * This method returns the Y position of the pawn
     * @pre The home status must be set
     * @return the home status of the pawn(true if it is at home false otherwise
     */
    public boolean getHomeStatus(){
        return homeStatus;
    }


    /**
     * This method returns the color of the pawn
     * @pre The color must be sest
     * @return the color of the pawn
     */
    public Color getColor() {
        return color;
    }

}
