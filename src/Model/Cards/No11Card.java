package Model.Cards;

import Model.Board;
import Model.Pawn;
import Model.Square.End_slide;
import Model.Square.Safe_zone;
import Model.Square.Start_slide;

import java.awt.*;

/**
 * In this class we are declaring the attributes of Card no11
 * @author Dimitris Papadopoulos csd4976
 */
public class No11Card extends Cards{
    /**
     * If isSlide==true then the pawn that is swapping was in the start of its slide else if isSlide==false it wasn't
     */
    public boolean isSlide=false;
    /**
     * Constructs no11 card setting its image and number
     */
    public No11Card(){
        setNumber(11);
        setImage("src/images/cards/card11.png");
    }

    /**
     * In this method we are executing the function of swapping two pawns for card 11
     * @param p1 the pawn of player with the card 11
     * @param p2 the enemy's pawn that we are swapping with the other pawn
     * @param board the board of the game
     */
    public void changePawn(Pawn p1,Pawn p2,Board board){
        if(!(board.getSquare(p2.getPosX(),p2.getPosY()) instanceof Safe_zone)&&!(board.getSquare(p1.getPosX(),p1.getPosY()) instanceof Safe_zone)){
            int p1X=p1.getPosX();
            int p1Y=p1.getPosY();
            p1.setPosX(p2.getPosX());
            p1.setPosY(p2.getPosY());
            p2.setPosX(p1X);
            p2.setPosY(p1Y);
            if (board.getSquare(p1.getPosX(), p1.getPosY()) instanceof Start_slide) {
                isSlide=true;
                if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() != p1.getColor()) {
                    System.out.println("tsoulithraaaa");
                    System.out.println(board.getSquare(p1.getPosX(), p1.getPosY()).getColor());
                    if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.blue) {//blue slides
                        System.out.println("mpike");
                        int i=0;
                        while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                                    board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p1.setPosX(p1.getPosX() + 1);
                            System.out.println(p1.getPosX());
                        }
                    } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.yellow) {//yellow slides
                        System.out.println("mpike");
                        int i=0;
                        while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                                    board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p1.setPosY(p1.getPosY() - 1);
                            System.out.println(p1.getPosX());
                        }
                    } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.green) {//green slides
                        System.out.println("mpike gia green");
                        int i=0;
                        while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                                    board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p1.setPosX(p1.getPosX() - 1);
                            System.out.println(p1.getPosX());
                        }
                        System.out.println("bghke apo green");
                    } else if (board.getSquare(p1.getPosX(), p1.getPosY()).getColor() == Color.red) {//red slides
                        System.out.println("mpike gia red");
                        int i=0;
                        while (!(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn() && board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() != p1.getColor()) {
                                    board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p1.setPosY(p1.getPosY() + 1);
                            System.out.println(p1.getPosY());
                        }
                        System.out.println("bghke apo red");
                    }

                }
            }
            if (board.getSquare(p2.getPosX(), p2.getPosY()) instanceof Start_slide) {
                isSlide=true;
                if (board.getSquare(p2.getPosX(), p2.getPosY()).getColor() != p2.getColor()) {
                    System.out.println("tsoulithraaaa");
                    System.out.println(board.getSquare(p2.getPosX(), p2.getPosY()).getColor());
                    if (board.getSquare(p2.getPosX(), p2.getPosY()).getColor() == Color.blue) {//blue slides
                        System.out.println("mpike");
                        int i=0;
                        while (!(board.getSquare(p2.getPosX(), p2.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p2.getPosX(), p2.getPosY()).getHasPawn() && board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().getColor() != p2.getColor()) {
                                    board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p2.setPosX(p2.getPosX() + 1);
                            System.out.println(p2.getPosX());
                        }
                    } else if (board.getSquare(p2.getPosX(), p2.getPosY()).getColor() == Color.yellow) {//yellow slides
                        System.out.println("mpike");
                        int i=0;
                        while (!(board.getSquare(p2.getPosX(), p2.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p2.getPosX(), p2.getPosY()).getHasPawn() && board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().getColor() != p2.getColor()) {
                                    board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p2.setPosY(p2.getPosY() - 1);
                            System.out.println(p2.getPosX());
                        }
                    } else if (board.getSquare(p2.getPosX(), p2.getPosY()).getColor() == Color.green) {//green slides
                        System.out.println("mpike gia green");
                        int i=0;
                        while (!(board.getSquare(p2.getPosX(), p2.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p2.getPosX(), p2.getPosY()).getHasPawn() && board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().getColor() != p2.getColor()) {
                                    board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p2.setPosX(p2.getPosX() - 1);
                            System.out.println(p2.getPosX());
                        }
                        System.out.println("bghke apo green");
                    } else if (board.getSquare(p2.getPosX(), p2.getPosY()).getColor() == Color.red) {//red slides
                        System.out.println("mpike gia red");
                        int i=0;
                        while (!(board.getSquare(p2.getPosX(), p2.getPosY()) instanceof End_slide)) {
                            System.out.println("mpike stin for");
                            if(i!=0) {
                                if (board.getSquare(p2.getPosX(), p2.getPosY()).getHasPawn() && board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().getColor() != p2.getColor()) {
                                    board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().setSendStart(true);
                                }
                            }
                            i++;
                            p2.setPosY(p2.getPosY() + 1);
                            System.out.println(p2.getPosY());
                        }
                        System.out.println("bghke apo red");
                    }

                }
            }

        }

    }

    /**
     * In this method we are checking if we can actually use the card 11
     * @param p1 the pawn of player with the card 11
     * @param p2 the enemy's pawn that we are swapping with the other pawn
     * @param board the board of the game
     * @return true if we can use thecard and false if we cant
     */
    public boolean moveCheck11(Pawn p1,Pawn p2,Board board){
        if(p1.getHomeStatus()||p2.getHomeStatus()){
            return false;
        }
        if(!p1.getPawnOn()||!p2.getPawnOn()){
            return false;
        }
        if(board.getSquare(p2.getPosX(), p2.getPosY()) instanceof Safe_zone){
            return false;
        }if(board.getSquare(p1.getPosX(), p1.getPosY()) instanceof Safe_zone){
            return false;
        }
        if(((p1.getPosX()==9)&&(p1.getPosY()==9))||((p2.getPosX()==9)&&(p2.getPosY()==9))){
            return false;
        }
        if(isSlide) {
            if (board.getSquare(p1.getPosX(), p1.getPosY()).getHasPawn()) {
                if ((board.getSquare(p1.getPosX(), p1.getPosY()).getPawn().getColor() == p1.getColor())) {
                    return false;
                }
            }else if(board.getSquare(p2.getPosX(), p2.getPosY()).getHasPawn()){
                    if(board.getSquare(p2.getPosX(), p2.getPosY()).getPawn().getColor() == p2.getColor()){
                        return false;
                    }
            }
        }
        return true;
    }
    @Override
    public void movePawn(Pawn p1, Board board) {
        if (p1.getPosX() == 3 && p1.getPosY() == 0 && (p1.getColor() == Color.red)) {//red pawn is home
            p1.setHomeStatus(true);

        } else if ((board.getSquare(p1.getPosX(),p1.getPosY())instanceof Safe_zone)||((p1.getColor()==Color.red)&&((p1.getPosX()==0&&(p1.getPosY()<3)||(p1.getPosX()<4&&p1.getPosY()==0))))) {
            p1.setPosX(9);
            p1.setPosY(9);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() < 11) && (p1.getPosX() > 0) && (p1.getColor() == Color.red)) {//green column to red safe zone
            offset = 11 - p1.getPosX() - 2;
            p1.setPosX(offset);
            p1.setPosY(2);
        } else if (p1.getPosX() == 12 && p1.getPosY() == 15 && (p1.getColor() == Color.yellow)) {//yellow pawn home
            p1.setHomeStatus(true);
        } else if ((board.getSquare(p1.getPosX(),p1.getPosY())instanceof Safe_zone)||((p1.getColor()==Color.yellow)&&((p1.getPosX()==15&&(p1.getPosY()>12)||(p1.getPosX()>12&&p1.getPosY()==15))))) {
            p1.setPosX(9);
            p1.setPosY(9);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() > 6) && (p1.getColor() == Color.yellow)) {//blue column to yellow safe zone
            offset = 11 - (15 - p1.getPosX()) - 2;
            p1.setPosX(15 - offset);
            p1.setPosY(13);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 11) < 16) {//red row
            p1.setPosX(0);
            p1.setPosY(p1.getPosY() + 11);
        } else if ((p1.getPosX() == 0) && (p1.getPosY() + 11 > 15)) {//red row to blue column
            int offset = p1.getPosY() + 11 - 15;
            p1.setPosX(offset);
            p1.setPosY(15);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 11 < 16)) {//blue column
            p1.setPosY(15);
            p1.setPosX(p1.getPosX() + 11);
        } else if ((p1.getPosY() == 15) && (p1.getPosX() + 11 > 15)) {//blue column to yellow row
            offset = p1.getPosX() + 11 - 15;
            p1.setPosX(15);
            p1.setPosY(15 - offset);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 11 > -1)) {//yellow row
            p1.setPosY(p1.getPosY() - 11);
            p1.setPosX(15);
        } else if ((p1.getPosX() == 15) && (p1.getPosY() - 11 < 0)) {//yellow row to green column
            offset = 11 - p1.getPosY();
            p1.setPosX(15 - offset);
            p1.setPosY(0);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 11 > -1)) {//green column
            p1.setPosY(0);
            p1.setPosX(p1.getPosX() - 11);
        } else if ((p1.getPosY() == 0) && (p1.getPosX() - 11 < 0)) {//green column to red row
            offset = 11 - p1.getPosX();
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
