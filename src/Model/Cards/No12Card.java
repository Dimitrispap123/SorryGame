package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Safe_zone;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no12
 * @author Dimitris Papadopoulos csd4976
 */
public class No12Card extends Cards {
    /**
     * Constructs no12 card setting its image and number
     */
    public No12Card(){
        setNumber(12);
        setImage("src/images/cards/card12.png");
    }

    @Override
    public void movePawn(Pawn p1, Board board) {
        if (p1.getPosX() == 4 && p1.getPosY() == 0 && (p1.getColor() == Color.red)) {//red pawn is home
            p1.setHomeStatus(true);

        } else if ((board.getSquare(p1.getPosX(),p1.getPosY())instanceof Safe_zone)||((p1.getColor()==Color.red)&&((p1.getPosX()==0&&(p1.getPosY()<3)||(p1.getPosX()<4&&p1.getPosY()==0))))) {
            p1.setPosX(9);
            p1.setPosY(9);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() < 10) && (p1.getPosX() > 0) && (p1.getColor() == Color.red)) {//green column to red safe zone
            offset = 12 - p1.getPosX() - 2;
            p1.setPosX(offset);
            p1.setPosY(2);
        } else if (p1.getPosX() == 11 && p1.getPosY() == 15 && (p1.getColor() == Color.yellow)) {//yellow pawn home
            p1.setHomeStatus(true);
        } else if ((board.getSquare(p1.getPosX(),p1.getPosY())instanceof Safe_zone)||((p1.getColor()==Color.yellow)&&((p1.getPosX()==15&&(p1.getPosY()>12)||(p1.getPosX()>11&&p1.getPosY()==15))))) {
            p1.setPosX(9);
            p1.setPosY(9);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() > 5) && (p1.getColor() == Color.yellow)) {//blue column to yellow safe zone
            offset = 12 - (15 - p1.getPosX()) - 2;
            p1.setPosX(15 - offset);
            p1.setPosY(13);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 12) < 16) {//red row
            p1.setPosX(0);
            p1.setPosY(p1.getPosY() + 12);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 12 > 15)) {//red row to blue column
            int offset = p1.getPosY() + 12 - 15;
            p1.setPosX(offset);
            p1.setPosY(15);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 12 < 16)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() + 12);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 12 > 15)) {//blue column to yellow row
            offset = p1.getPosX() + 12 - 15;
            p1.setPosX(15);
            p1.setPosY(15 - offset);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 12 > -1)) {//yellow row
            p1.setPosY(p1.getPosY() - 12);
            p1.setPosX(15);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 12 < 0)) {//yellow row to green column
            offset = 12 - p1.getPosY();
            p1.setPosX(15 - offset);
            p1.setPosY(0);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 12 > -1)) {//green column
            p1.setPosY(0);
            p1.setPosX(p1.getPosX() - 12);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 12 < 0)) {//green column to red row
            offset = 12 - p1.getPosX();
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
}
