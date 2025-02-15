package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no5
 *
 * @author Dimitris Papadopoulos csd4976
 */
public class No5Card extends Cards {
    /**
     * Constructs no5 card setting its image and number
     */
    public No5Card() {
        setNumber(5);
        setImage("src/images/cards/card5.png");
    }

    @Override
    public void movePawn(Pawn p1, Board board) {
        if ((p1.getColor() == Color.red)) {
            if (p1.getPosX() == 0 && p1.getPosY() + 5 > 2 && p1.getPosY() <= 2) {//red row to red safe zone
                offset = (5 + p1.getPosY()) - 2;
                p1.setPosX(offset);
                p1.setPosY(2);

            } else if ((p1.getPosY() == 2) && (p1.getPosX() < 6)) {
                if ((p1.getPosX() + 5 < 6)) {
                    p1.setPosX(p1.getPosX() + 5);
                    p1.setPosY(2);

                } else if (p1.getPosX() + 5 == 6) {
                    p1.setHomeStatus(true);
                } else {
                    p1.setPosX(9);
                    p1.setPosY(9);
                }
            } else if ((p1.getPosY() == 0) && (p1.getPosX() < 3)) {//green column to red safe zone
                offset = 5 - p1.getPosX() - 2;
                p1.setPosX(offset);
                p1.setPosY(2);
            }
        } else {
            if (p1.getPosX() == 15 && p1.getPosY() - 5 < 13 && p1.getPosY() > 12) {//yellow row to yellow safe zone
                offset = 13 - (p1.getPosY() - 5);
                p1.setPosX(15 - offset);
                p1.setPosY(13);

            } else if ((p1.getPosY() == 13) && (p1.getPosX() > 9)) {
                if ((p1.getPosX() - 5 > 9)) {
                    p1.setPosX(p1.getPosX() - 5);
                    p1.setPosY(13);

                } else if (p1.getPosX() - 5 == 9) {
                    p1.setHomeStatus(true);
                } else {
                    p1.setPosX(9);
                    p1.setPosY(9);
                }
            } else if ((p1.getPosY() == 15) && (p1.getPosX() > 12)) {//blue column to yellow safe zone
                offset = 5 - (15 - p1.getPosX()) - 2;
                p1.setPosX(15 - offset);
                p1.setPosY(13);
            }

        }
        int x = p1.getPosX();
        int y = p1.getPosY();
        board.getSquare(x, y).setHasPawn(false);
        if ((p1.getPosX() == 0) && (p1.getPosY() + 5) < 16) {//red row
            p1.setPosX(0);
            p1.setPosY(p1.getPosY() + 5);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 5 > 15)) {//red row to blue column
            int offset = p1.getPosY() + 5 - 15;
            p1.setPosX(offset);
            p1.setPosY(15);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 5 < 16)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() + 5);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 5 > 15)) {//blue column to yellow row
            offset = p1.getPosX() + 5 - 15;
            p1.setPosX(15);
            p1.setPosY(15 - offset);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 5 > -1)) {//yellow row
            p1.setPosY(p1.getPosY() - 5);
            p1.setPosX(15);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 5 < 0)) {//yellow row to green column
            offset = 5 - p1.getPosY();
            p1.setPosX(15 - offset);
            p1.setPosY(0);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 5 > -1)) {//green column
            p1.setPosY(0);
            p1.setPosX(p1.getPosX() - 5);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 5 < 0)) {//green column to red row
            offset = 5 - p1.getPosX();
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

