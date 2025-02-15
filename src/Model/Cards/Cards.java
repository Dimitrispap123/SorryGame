package Model.Cards;

import Model.Board;
import Model.Pawn;
public abstract class Cards {
    /**
     * The number if the card
     */
    int number;
    /**
     * The image of the card
     */
    String image;
    /**
     * The offset for the transition from a column to a row and from a row to a column
     */
    int offset;
    /**
     * This method sets the image of the card
     * @pre An image must be given
     * @param image the image of the card
     */
    public void setImage(String image) {
        this.image=image;
    }
    /**
     * This method returns the image of the card
     * @pre An image must be set
     * @return the image of the card
     */
    public String getImage() { return image; }
    /**
     * This method sets the number of the card
     * @pre A number must be given
     * @param number the number of the card
     */
    public void setNumber(int number) {
        this.number = number;
    }
    /**
     * This method returns the number of the card
     * @pre a number must be set
     * @return the name of the card
     */
    public int getNumber() {
        return number;
    }

    /**
     * In this abstract method we are setting how a pawn will move for each card
     * @param p1 the pawn of the player that will move
     * @param board the board of the game
     */
    public abstract void movePawn(Pawn p1, Board board);

    /**
     * In this method we are checking if a pawn can move
     * @pre A valid Pawn and board must be given
     * @param p1 the pawn of the player that we want to move
     * @param board the board of the game
     * @return true if it can move otherwise false
     */
    public boolean moveCheck(Pawn p1,Board board){
        if(p1.getHomeStatus()){
            return false;
        }
        if(!p1.getPawnOn()){
            return false;
        }
        if((p1.getPosX()==9)&&(p1.getPosY()==9)){
            return false;
        }
        if (board.getSquare(p1.getPosX(),p1.getPosY()).getHasPawn()){
            if(board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor()==p1.getColor()) {
                return false;
            }else{
                board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setPosX(10);
                board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setPosY(10);
                return true;

            }
        }
        return true;

    }


}
