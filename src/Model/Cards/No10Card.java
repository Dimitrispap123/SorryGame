package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Safe_zone;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no10
 * @author Dimitris Papadopoulos csd4976
 */
public class No10Card extends Cards{
    /**
     * Constructs no10 card setting its image and number
     */
    public No10Card(){
        setNumber(10);
        setImage("src/images/cards/card10.png");
    }

    @Override
    public void movePawn(Pawn p1, Board board) {
        if (p1.getPosX() == 2 && p1.getPosY() == 0 && (p1.getColor() == Color.red)) {//red pawn is home
            p1.setHomeStatus(true);

        } else if ((board.getSquare(p1.getPosX(),p1.getPosY())instanceof Safe_zone)||((p1.getColor()==Color.red)&&((p1.getPosX()==0&&(p1.getPosY()<3)||(p1.getPosX()==1&&p1.getPosY()==0))))) {
            p1.setPosX(9);
            p1.setPosY(9);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() < 8) && (p1.getPosX() > 0) && (p1.getColor() == Color.red)) {//green column to red safe zone
            offset = 10 - p1.getPosX() - 2;
            p1.setPosX(offset);
            p1.setPosY(2);
        } else if (p1.getPosX() == 13 && p1.getPosY() == 15 && (p1.getColor() == Color.yellow)) {//yellow pawn home
            p1.setHomeStatus(true);
        } else if  ((board.getSquare(p1.getPosX(),p1.getPosY())instanceof Safe_zone)||(((p1.getColor()==Color.yellow))&&((p1.getPosX()==15&&(p1.getPosY()>12)||(p1.getPosX()==14&&p1.getPosY()==15))))) {
            p1.setPosX(9);
            p1.setPosY(9);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() > 7) && (p1.getColor() == Color.yellow)) {//blue column to yellow safe zone
            offset = 10 - (15 - p1.getPosX()) - 2;
            p1.setPosX(15 - offset);
            p1.setPosY(13);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 10) < 16) {//red row
            p1.setPosX(0);
            p1.setPosY(p1.getPosY() + 10);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 10 > 15)) {//red row to blue column
            int offset = p1.getPosY() + 10 - 15;
            p1.setPosX(offset);
            p1.setPosY(15);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 10 < 16)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() + 10);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 10 > 15)) {//blue column to yellow row
            offset = p1.getPosX() + 10 - 15;
            p1.setPosX(15);
            p1.setPosY(15 - offset);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 10 > -1)) {//yellow row
            p1.setPosY(p1.getPosY() - 10);
            p1.setPosX(15);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 10 < 0)) {//yellow row to green column
            offset = 10 - p1.getPosY();
            p1.setPosX(15 - offset);
            p1.setPosY(0);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 10 > -1)) {//green column
            p1.setPosY(0);
            p1.setPosX(p1.getPosX() - 10);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 10 < 0)) {//green column to red row
            offset = 10 - p1.getPosX();
            p1.setPosX(0);
            p1.setPosY(offset);
        }
        if (board.getSquare(p1.getPosX(), p1.getPosY()) instanceof Start_slide) {
            if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() != p1.getColor()) {
                if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.blue) {//blue slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosX(p1.getPosX() + 1);
                    }
                } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.yellow) {//yellow slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosY(p1.getPosY() - 1);
                    }
                } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.green) {//green slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosX(p1.getPosX() - 1);
                    }
                } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.red) {//red slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosY(p1.getPosY() + 1);
                    }
                }

            }

        }
    }

    /**
     * In this method we are executing the option of card 10 to go 1 square backwards
     * @param p1 the pawn of the player with card 10
     * @param board the board of the game
     */

    public void movePawn1Back(Pawn p1,Board board){
        if ((p1.getPosY() == 2) && (p1.getPosX() - 1 >= 0) && (p1.getPosX() < 6)) {//red safe zone back
            p1.setPosY(2);
            p1.setPosX(p1.getPosX() - 1);
        } else if ((p1.getPosY() == 13) && (p1.getPosX() + 1 <= 15) && (p1.getPosX() > 9)) {//yellow safe zone back
            p1.setPosX(p1.getPosX() + 1);
            p1.setPosY(13);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() - 1 >= 0)) {//red row
            p1.setPosX(0);
            p1.setPosY(p1.getPosY() - 1);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() + 1 < 16)) {//green column
            p1.setPosY(0);
            p1.setPosX(p1.getPosX() + 1);
        } else if (p1.getPosX() == 15 && p1.getPosY() + 1 <= 15) {//yellow row
            p1.setPosX(15);
            p1.setPosY(p1.getPosY() + 1);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() - 1 > -1)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() - 1);
        }
        if (board.getSquare(p1.getPosX(), p1.getPosY()) instanceof Start_slide) {
            if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() != p1.getColor()) {
                if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.blue) {//blue slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosX(p1.getPosX() + 1);
                    }
                } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.yellow) {//yellow slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosY(p1.getPosY() - 1);
                    }
                } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.green) {//green slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosX(p1.getPosX() - 1);
                    }
                } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.red) {//red slides
                    while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                        if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                            board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                        }
                        p1.setPosY(p1.getPosY() + 1);
                    }
                }

            }

        }
    }
}
