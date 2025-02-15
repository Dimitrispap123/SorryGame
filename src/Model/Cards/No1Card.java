package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no1
 *
 * @author Dimitris Papadopoulos csd4976
 */
public class No1Card extends Cards {
    /**
     *Constructs no1 card setting its image and number
     */
    public No1Card() {
        setNumber(1);
        setImage("src/images/cards/card1.png");
    }

    @Override
    public void movePawn(Pawn p1, Board board) {

        if ((p1.getColor() == Color.red)) {
            if (p1.getPosX() >= 0 && (p1.getPosX() < 6) && p1.getPosY() == 2) {
                if (p1.getPosX() + 1 == 6) {
                    p1.setHomeStatus(true);
                    System.out.println(p1.getPawnOn());
                } else {
                    p1.setPosX(p1.getPosX() + 1);
                    p1.setPosY(2);
                }
            }
        } else {
            if (p1.getPosX() <= 15 && (p1.getPosX() > 9) && p1.getPosY() == 13) {
                if (p1.getPosX() - 1 == 9) {
                    p1.setHomeStatus(true);
                } else {
                    p1.setPosX(p1.getPosX() - 1);
                    p1.setPosY(13);
                }

            }
        }
        if (((p1.getPosY() + 1) < 16) && (p1.getPosX() == 0)) {//red row
            p1.setPosY(p1.getPosY() + 1);
            p1.setPosX(0);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 1) == 16) {//red row to blue column
            p1.setPosX(1);
            p1.setPosY(15);
        } else if ((p1.getPosY() == 15) && ((p1.getPosX() + 1) <= 15)) {//blue column
            p1.setPosX(p1.getPosX() + 1);
        } else if ((p1.getPosY() == 15) && ((p1.getPosX()) == 15)) {//blue column to yellow row
            p1.setPosX(15);
            p1.setPosY(14);
        } else if (p1.getPosX() == 15 && p1.getPosY() > 0) {//yellow row
            p1.setPosY(p1.getPosY() - 1);
            p1.setPosX(15);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() == 0)) {//yellow row to green column
            p1.setPosX(14);
            p1.setPosY(0);
        } else if ((p1.getPosX() > 0) && (p1.getPosY() == 0)) {//green column
            p1.setPosX(p1.getPosX() - 1);
            p1.setPosY(0);
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
                        System.out.println(p1.getPosX());
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
                        System.out.println(p1.getPosY());
                    }
                }

            }

        }
    }
}
