package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no2
 * @author Dimitris Papadopoulos csd4976
 */
public class No2Card extends Cards {

    /**
     * Constructs no2 card setting its image and number
     */
    public No2Card() {
        setNumber(2);
        setImage("src/images/cards/card2.png");
    }

    @Override
    public void movePawn(Pawn p1, Board board) {
        if ((p1.getColor() == Color.red)) {
            if (p1.getPosX() == 0 && p1.getPosY() + 2 > 2 && p1.getPosY() <= 2) {//red row to red safe zone
                p1.setPosX(p1.getPosY());
                p1.setPosY(2);

            } else if ((p1.getPosY() == 2) && (p1.getPosX() < 6)) {
                if ((p1.getPosX() + 2 < 6)) {
                    p1.setPosX(p1.getPosX() + 2);
                    p1.setPosY(2);

                } else if (p1.getPosX() + 2 == 6) {
                    p1.setHomeStatus(true);
                } else {
                    p1.setPosX(9);
                    p1.setPosY(9);
                }
            }
        } else {
            if (p1.getPosX() == 15 && p1.getPosY() - 2 < 13 && p1.getPosY() >= 13) {//yellow row to yellow safe zone
                offset = 15 - p1.getPosY();
                p1.setPosX(15 - offset);
                p1.setPosY(13);

            } else if ((p1.getPosY() == 13) && (p1.getPosX() > 9)) {
                if ((p1.getPosX() - 2 > 9)) {
                    p1.setPosX(p1.getPosX() - 2);
                    p1.setPosY(13);

                } else if (p1.getPosX() - 2 == 9) {
                    p1.setHomeStatus(true);
                } else {
                    p1.setPosX(9);
                    p1.setPosY(9);
                }
            }

        }
        if (((p1.getPosY() + 2) < 16) && (p1.getPosX() == 0)) {//red row
            p1.setPosY(p1.getPosY() + 2);
            p1.setPosX(0);
        } else if ((p1.getPosY() + 2 > 15) && (p1.getPosX() == 0)) {//red row to blue column
            offset = p1.getPosY() + 2 - 15;
            p1.setPosX(offset);
            p1.setPosY(15);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 2 < 16)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() + 2);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 2 > 15)) {//blue column to yellow row
            offset = p1.getPosX() + 2 - 15;
            p1.setPosX(15);
            p1.setPosY(15 - offset);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 2 > -1)) {//yellow row
            p1.setPosX(15);
            p1.setPosY(p1.getPosY() - 2);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 2 < 0)) {//yellow row to green column
            offset = 2 - p1.getPosY();
            p1.setPosX(15 - offset);
            p1.setPosY(0);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 2 > -1)) {//green column
            p1.setPosX(p1.getPosX() - 2);
            p1.setPosY(0);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 2 < 0)) {//green column to red row
            offset = 2 - p1.getPosX();
            p1.setPosY(offset);
            p1.setPosX(0);
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
