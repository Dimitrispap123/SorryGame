package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no4
 *
 * @author Dimitris Papadopoulos csd4976
 */
public class No4Card extends Cards {
    /**
     *Constructs no4 card setting its image and number
     */
    public No4Card() {
        setNumber(4);
        setImage("src/images/cards/card4.png");
    }

    @Override
    public void movePawn(Pawn p1, Board board) {

        if ((p1.getPosY() == 2) && (p1.getPosX() - 4 >= 0) && (p1.getPosX() < 6)) {//red safe zone back
            p1.setPosY(2);
            p1.setPosX(p1.getPosX() - 4);
        } else if ((p1.getPosY() == 2) && (p1.getPosX() - 4 < 0) && (p1.getPosX() > 0)) {//red safe zone to red row
            offset = 4 - p1.getPosX();
            if (offset != 3) {
                p1.setPosY(p1.getPosY() - offset);
                p1.setPosX(0);
            } else {
                p1.setPosY(0);
                p1.setPosX(1);
            }
        } else if ((p1.getPosY() == 13) && (p1.getPosX() + 4 <= 15) && (p1.getPosX() > 9)) {//yellow safe zone back
            p1.setPosX(p1.getPosX() + 4);
            p1.setPosY(13);

        } else if ((p1.getPosY() == 13) && (p1.getPosX() + 4 > 15)) {//yellow safe zone to yellow row
            offset = 4 - (15 - p1.getPosX());
            if (offset < 3) {
                p1.setPosY(13 + offset);
                p1.setPosX(15);
            } else {
                p1.setPosY(15);
                p1.setPosX(15 - (offset - 2));
            }
        } else if (((p1.getPosX() - 4) < 0) && (p1.getPosY() == 15)) {//blue column to red row
            offset = 4 - p1.getPosX();
            p1.setPosY(15 - offset);
            p1.setPosX(0);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() - 4 >= 0)) {//red row
            p1.setPosX(0);
            p1.setPosY(p1.getPosY() - 4);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() - 4 < 0)) {//red row to green slide
            offset = 4 - p1.getPosY();
            p1.setPosY(0);
            p1.setPosX(offset);

        } else if ((p1.getPosY() == 0) && (p1.getPosX() + 4 < 16)) {//green column
            p1.setPosY(0);
            p1.setPosX(p1.getPosX() + 4);

        } else if ((p1.getPosY() == 0) && (p1.getPosX() + 4 > 15)) {//green column to yellow row
            offset = p1.getPosX() + 4 - 15;
            p1.setPosX(15);
            p1.setPosY(offset);

        } else if (p1.getPosX() == 15 && p1.getPosY() + 4 <= 15) {//yellow row
            p1.setPosX(15);
            p1.setPosY(p1.getPosY() + 4);
        } else if (p1.getPosX() == 15 && p1.getPosY() + 4 > 15) {//yellow row to blue column
            offset = p1.getPosY() + 4 - 15;
            p1.setPosY(15);
            p1.setPosX(15 - offset);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() - 4 > -1)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() - 4);
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
                        System.out.println(p1.getPosX());
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
