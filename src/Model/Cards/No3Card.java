package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no3
 *
 * @author Dimitris Papadopoulos csd4976
 */
public class No3Card extends Cards {
    /**
     * Constructs no3 card setting its image and number
     */
    public No3Card() {
        setNumber(3);
        setImage("src/images/cards/card3.png");
    }

    @Override
    public void movePawn(Pawn p1, Board board) {

        if (p1.getPosX() == 0 && p1.getPosY() + 3 > 2 && (p1.getPosY() <= 2) && (p1.getColor() == Color.red)) {//red row to red safe zone
            offset = (3 + p1.getPosY()) - 2;
            p1.setPosX(offset);
            p1.setPosY(2);

        } else if ((p1.getPosY() == 2) && (p1.getPosX() < 6)&&(p1.getColor() == Color.red)) {
            if ((p1.getPosX() + 3 < 6)) {
                p1.setPosX(p1.getPosX() + 3);
                p1.setPosY(2);

            } else if (p1.getPosX() + 3 == 6) {
                p1.setHomeStatus(true);
            } else {
                p1.setPosX(9);
                p1.setPosY(9);
            }
        } else if (p1.getPosX() == 15 && p1.getPosY() - 3 < 13 && p1.getPosY() >= 13 && p1.getColor() == Color.yellow) {//yellow row to yellow safe zone
            offset = 13 - (p1.getPosY() - 3);
            System.out.println(offset);
            p1.setPosX(15 - offset);
            p1.setPosY(13);

        } else if ((p1.getPosY() == 13) && (p1.getPosX() > 9) && p1.getColor() == Color.yellow) {
            if ((p1.getPosX() - 3 > 9)) {
                p1.setPosX(p1.getPosX() - 3);
                p1.setPosY(13);

            } else if (p1.getPosX() - 3 == 9) {
                p1.setHomeStatus(true);
                System.out.println(p1.getPawnOn());
            } else {
                p1.setPosX(9);
                p1.setPosY(9);
            }
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 3) < 16) {//red row
            p1.setPosX(0);
            p1.setPosY(p1.getPosY() + 3);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 3 > 15)) {//red row to blue column
            int offset = p1.getPosY() + 3 - 15;
            p1.setPosX(offset);
            p1.setPosY(15);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 3 < 16)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() + 3);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 3 > 15)) {//blue column to yellow row
            offset = p1.getPosX() + 3 - 15;
            p1.setPosX(15);
            p1.setPosY(15 - offset);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 3 > -1)) {//yellow row
            p1.setPosY(p1.getPosY() - 3);
            p1.setPosX(15);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 3 < 0)) {//yellow row to green column
            offset = 3 - p1.getPosY();
            p1.setPosX(15 - offset);
            p1.setPosY(0);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 3 > -1)) {//green column
            p1.setPosY(0);
            p1.setPosX(p1.getPosX() - 3);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 3 < 0)) {//green column to red row
            offset = 3 - p1.getPosX();
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
