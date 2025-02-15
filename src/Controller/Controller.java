package Controller;

import Model.Board;
import Model.Cards.*;

import Model.Deck;
import Model.Pawn;
import Model.Player;
import Model.Square.Safe_zone;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Random;

import static java.lang.System.exit;


/**
 * In this class we are creating the controller of the game
 *
 * @author Dimitris Papadopoulos csd4976
 */
public class Controller {
    /**
     * for the game status,true if it has ended, false otherwise
     */
    boolean hasGameEnded;
    /**
     * for the card status,true if it has been played, false otherwise
     */
    boolean cardHasBeenPlayed;
    /**
     *temporary variable to store the previous X position of the pawn
     */
    int prevX;
    /**
     *temporary variable to store the previous Y position of the pawn
     */
    int prevY;
    /**
     * the graphics of the game
     */
    View view;
    /**
     * the deck of the game
     */
    Deck deck;
    /**
     * the board of the game
     */
    Board board;
    /**
     * the red buttons for the 2 red pawns
     */
    JButton[] redButtons;
    /**
     * the yellow buttons for the 2 yellow pawns
     */
    JButton[] yellowButtons;
    /**
     * the turn of the players
     */
    int turn;
    /**
     * this variable is true when a card has been received and false otherwise
     */
    boolean hasCard;
    /**
     * the red player
     */
    Player redPlayer;
    /**
     * the yellow player
     */
    Player yellowPlayer;
    /**
     * the player who it's his turn at this moment
     */
    Player currPlayer;
    /**
     * red pawn 1
     */
    Pawn redPawn1;
    /**
     * red pawn 2
     */
    Pawn redPawn2;
    /**
     * yellow pawn 1
     */
    Pawn yellowPawn1;
    /**
     * yellow pawn 2
     */
    Pawn yellowPawn2;


    /**
     * This method changes the status of the turn(true/false)
     * @post the turn gets changed
     */
    public void changeTurn() {
        if (currPlayer == redPlayer) {
            currPlayer = yellowPlayer;
        } else {
            currPlayer = redPlayer;
        }
        view.updateInfoBox("Info Box\n\nTurn: " + currPlayer.getName() + "\nCards left: " + deck.getCardSum());
    }

    /**
     * In this method we are initializing the attributes of the game(board,deck,players,pawns,the graphics,turn)
     */

    public void initialize() {
        deck = new Deck();
        deck.initDeck();
        board = new Board();
        board.initBoard();
        redPlayer = new Player("Player 1 (Red)", Color.red);
        yellowPlayer = new Player("Player 2 (Yellow)", Color.yellow);
        redPawn1 = new Pawn(Color.red, 10, 10, 1);
        redPawn2 = new Pawn(Color.red, 10, 10, 2);
        yellowPawn1 = new Pawn(Color.yellow, 10, 10, 1);
        yellowPawn2 = new Pawn(Color.yellow, 10, 10, 2);
        view = new View();
        hasCard = false;
        cardHasBeenPlayed = false;
        setListeners();
        turn = new Random().nextInt(2);
        if (turn == 0) {
            currPlayer = redPlayer;
        } else {
            currPlayer = yellowPlayer;
        }
        view.updateInfoBox("Info Box\n\nTurn: " + currPlayer.getName() + "\nCards left: " + deck.getCardSum());
    }

    /**
     * In this method we are setting the listeners for the buttons
     */
    public void setListeners() {
        view.getBackCard().addActionListener(new CardListener());
        view.getFoldButton().addActionListener(new FoldListener());
        view.getJMenuBar().getMenu(0).addActionListener(new MenuListener());
        view.getTwoPlayers().addActionListener(new MenuListener());
        view.getThreePlayers().addActionListener(new MenuListener());
        view.getFourPlayers().addActionListener(new MenuListener());
        view.getExit().addActionListener(new MenuListener());
        redButtons = view.getRedPawns();
        yellowButtons = view.getYellowPawns();
        for (int i = 0; i < 2; i++) {
            redButtons[i].addActionListener(new PawnListener());
            yellowButtons[i].addActionListener(new PawnListener());
        }


    }

    /**
     * In this class we are managing the functionality of the pawn, that pressed, for each card
     */

    public class PawnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getHasGameEnded()) {
                if (!cardHasBeenPlayed && hasCard) {
                    if (currPlayer == redPlayer) {

                        if (e.getSource() == redButtons[0]) {
                            if (!redPawn1.getPawnOn()) {
                                if ((deck.getReceivedCard() instanceof No12Card) || deck.getReceivedCard() instanceof No8Card) {
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        System.out.println("you cant move");
                                    } else if(option==1){
                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        view.removeCurrentCard();
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }else if (deck.getReceivedCard() instanceof SorryCard) {
                                    SorryCard c = new SorryCard();
                                    redPawn1.setPawnOn(true);
                                    String[] options = {"Change position with yellow pawn 1", "Change position with yellow pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0&&!yellowPawn1.getHomeStatus() && yellowPawn1.getPawnOn()&&!(board.getSquare(yellowPawn1.getPosX(),yellowPawn1.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(redPawn1, yellowPawn1, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(redPawn1, redButtons[0]);

                                        } else {
                                            redPawn1.setPawnOn(false);
                                            redPawn1.setPosX(10);
                                            redPawn1.setPosY(10);
                                        }


                                    } else if (option == 1 &&!yellowPawn2.getHomeStatus() && yellowPawn2.getPawnOn()&&!(board.getSquare(yellowPawn2.getPosX(),yellowPawn2.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(redPawn1, yellowPawn2, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                            redPawn1.setPawnOn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(redPawn1, redButtons[0]);

                                        }else {
                                            redPawn1.setPawnOn(false);
                                            redPawn1.setPosX(10);
                                            redPawn1.setPosY(10);
                                        }
                                    } else {
                                        redPawn1.setPawnOn(false);
                                        redPawn1.setPosX(10);
                                        redPawn1.setPosY(10);
                                    }


                                }else if ((deck.getReceivedCard() instanceof No1Card) || (deck.getReceivedCard() instanceof No2Card)) {

                                    if (board.getSquare(0, 4).getHasPawn()) {
                                        if (board.getSquare(0, 4).getPawn().getColor() != Color.red) {
                                            redPawn1.setPawnOn(true);
                                            board.getSquare(0, 4).getPawn().setSendStart(true);
                                            redPawn1.setPosX(0);
                                            redPawn1.setPosY(4);
                                            board.getSquare(0, 4).setPawn(redPawn1);
                                            board.getSquare(0, 4).setHasPawn(true);
                                            view.updateFirstMove(redPawn1, redButtons[0]);
                                            cardHasBeenPlayed = true;
                                        } else {
                                            cardHasBeenPlayed = false;
                                            redPawn1.setPawnOn(false);
                                        }
                                    } else {
                                        cardHasBeenPlayed = true;
                                        redPawn1.setPawnOn(true);
                                        redPawn1.setPosX(0);
                                        redPawn1.setPosY(4);
                                        board.getSquare(0, 4).setPawn(redPawn1);
                                        board.getSquare(0, 4).setHasPawn(true);
                                        view.updateFirstMove(redPawn1, redButtons[0]);
                                    }
                                }
                            } else {
                                board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                                board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                                prevX = redPawn1.getPosX();
                                prevY = redPawn1.getPosY();
                                if (deck.getReceivedCard() instanceof No1Card) {
                                    No1Card c = new No1Card();
                                    c.movePawn(redPawn1, board);
                                    if (c.moveCheck(redPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                    } else {
                                        redPawn1.setPosX(prevX);
                                        redPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(redPawn1, redButtons[0]);

                                } else if (deck.getReceivedCard() instanceof No2Card) {
                                    No2Card c = new No2Card();
                                    c.movePawn(redPawn1, board);
                                    if (c.moveCheck(redPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                    } else {
                                        cardHasBeenPlayed = false;

                                        redPawn1.setPosX(prevX);
                                        redPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(redPawn1, redButtons[0]);

                                } else if (deck.getReceivedCard() instanceof No3Card) {
                                    No3Card c = new No3Card();
                                    c.movePawn(redPawn1, board);
                                    if (c.moveCheck(redPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                    } else {

                                        redPawn1.setPosX(prevX);
                                        redPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(redPawn1, redButtons[0]);

                                    if (redPawn2.getPawnOn()) {
                                        prevX = redPawn2.getPosX();
                                        prevY = redPawn2.getPosY();
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);
                                        c.movePawn(redPawn2, board);
                                        if (c.moveCheck(redPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        } else {

                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        if (redPawn2.getHomeStatus()) {
                                            view.homeZone(redPawn2, redButtons[1]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);
                                            redPawn2.setPosY(10);
                                            redPawn2.setPosX(10);
                                        }else {
                                            view.updatePawn(redPawn2, redButtons[1]);
                                        }

                                    }


                                } else if (deck.getReceivedCard() instanceof No4Card) {
                                    No4Card c = new No4Card();
                                    c.movePawn(redPawn1, board);
                                    if (c.moveCheck(redPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                    } else {

                                        redPawn1.setPosX(prevX);
                                        redPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(redPawn1, redButtons[0]);


                                } else if (deck.getReceivedCard() instanceof No5Card) {
                                    No5Card c = new No5Card();
                                    c.movePawn(redPawn1, board);
                                    if (c.moveCheck(redPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                    } else {
                                        redPawn1.setPosX(prevX);
                                        redPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(redPawn1, redButtons[0]);

                                    if (redPawn2.getPawnOn()) {
                                        prevX = redPawn2.getPosX();
                                        prevY = redPawn2.getPosY();
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);
                                        c.movePawn(redPawn2, board);
                                        if (c.moveCheck(redPawn2, board)) {
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        if (redPawn2.getHomeStatus()) {
                                            view.homeZone(redPawn2, redButtons[1]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);
                                            redPawn2.setPosY(10);
                                            redPawn2.setPosX(10);
                                        }else {
                                            view.updatePawn(redPawn2, redButtons[1]);
                                        }

                                    }

                                } else if (deck.getReceivedCard() instanceof No7Card) {
                                    No7Card c = new No7Card();
                                    c.movePawn(redPawn1, board);
                                    if (c.moveCheck(redPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                    } else {
                                        redPawn1.setPosX(prevX);
                                        redPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }

                                    view.updatePawn(redPawn1, redButtons[0]);
                                } else if (deck.getReceivedCard() instanceof No8Card) {
                                    No8Card c = new No8Card();
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn1, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(redPawn1, redButtons[0]);

                                    } else if (option == 1){

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                } else if (deck.getReceivedCard() instanceof No10Card) {
                                    No10Card c = new No10Card();
                                    String[] options = {"move forward", "move 1 square backwards"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn1, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }



                                    } else if (option == 1){
                                        c.movePawn1Back(redPawn1,board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                    }
                                    view.updatePawn(redPawn1, redButtons[0]);

                                } else if (deck.getReceivedCard() instanceof No11Card) {
                                    No11Card c = new No11Card();
                                    String[] options = {"Move forward","Change position with yellow pawn 1", "Change position with yellow pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn1, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(redPawn1, redButtons[0]);

                                    } else if(option==1&&yellowPawn1.getPawnOn()&&redPawn1.getPawnOn()){
                                        int tmpX=yellowPawn1.getPosX();
                                        int tmpY=yellowPawn1.getPosY();
                                        c.changePawn(redPawn1,yellowPawn1,board);
                                        if (c.moveCheck11(redPawn1,yellowPawn1 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        }else{
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            yellowPawn1.setPosX(tmpX);
                                            yellowPawn1.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(yellowPawn1);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(redPawn1, redButtons[0]);
                                        view.updatePawn(yellowPawn1, yellowButtons[0]);

                                    }else if(option==2&&yellowPawn2.getPawnOn()&&redPawn1.getPawnOn()){
                                        int tmpX=yellowPawn2.getPosX();
                                        int tmpY=yellowPawn2.getPosY();
                                        c.changePawn(redPawn1,yellowPawn2,board);
                                        if (c.moveCheck11(redPawn1,yellowPawn2 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        }else{
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            yellowPawn2.setPosX(tmpX);
                                            yellowPawn2.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(yellowPawn2);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(redPawn1, redButtons[0]);
                                        view.updatePawn(yellowPawn2, yellowButtons[1]);

                                    }
                                } else if (deck.getReceivedCard() instanceof No12Card) {
                                    No12Card c = new No12Card();
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn1, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(redPawn1, redButtons[0]);

                                    } else if (option == 1){

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }
                                if (redPawn1.getHomeStatus()) {
                                    view.homeZone(redPawn1, redButtons[0]);
                                    cardHasBeenPlayed = true;
                                    board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                                    board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                                    redPawn1.setPosY(10);
                                    redPawn1.setPosX(10);
                                }
                            }


                            hasPlayerWon(redPlayer);


                        }

                        if (e.getSource() == redButtons[1]) {
                            if (!redPawn2.getPawnOn()) {
                                if ((deck.getReceivedCard() instanceof No12Card) || deck.getReceivedCard() instanceof No8Card) {
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        System.out.println("you cant move");
                                    } else if (option == 1){
                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        view.removeCurrentCard();
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }else if (deck.getReceivedCard() instanceof SorryCard) {
                                    SorryCard c = new SorryCard();
                                    redPawn2.setPawnOn(true);
                                    String[] options = {"Change position with yellow pawn 1", "Change position with yellow pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0 &&!yellowPawn1.getHomeStatus() && yellowPawn1.getPawnOn()&&!(board.getSquare(yellowPawn1.getPosX(),yellowPawn1.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(redPawn2, yellowPawn1, board);
                                        if (c.moveCheck(redPawn2, board)) {

                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(redPawn2, redButtons[1]);

                                        }else {
                                            redPawn2.setPawnOn(false);
                                            redPawn2.setPosX(10);
                                            redPawn2.setPosY(10);
                                        }

                                    } else if (option == 1 &&!yellowPawn2.getHomeStatus() && yellowPawn2.getPawnOn()&&!(board.getSquare(yellowPawn2.getPosX(),yellowPawn2.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(redPawn2, yellowPawn2, board);
                                        if (c.moveCheck(redPawn2,board)) {

                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                            redPawn2.setPawnOn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(redPawn2, redButtons[1]);

                                        }
                                        else {
                                            redPawn2.setPawnOn(false);
                                            redPawn2.setPosX(10);
                                            redPawn2.setPosY(10);
                                        }
                                    } else {
                                        redPawn2.setPawnOn(false);
                                        redPawn2.setPosX(10);
                                        redPawn2.setPosY(10);
                                    }


                                }else if ((deck.getReceivedCard() instanceof No1Card) || (deck.getReceivedCard() instanceof No2Card)) {

                                    if (board.getSquare(0, 4).getHasPawn()) {
                                        if (board.getSquare(0, 4).getPawn().getColor() != Color.red) {
                                            redPawn2.setPawnOn(true);
                                            board.getSquare(0, 4).getPawn().setSendStart(true);
                                            redPawn2.setPosX(0);
                                            redPawn2.setPosY(4);
                                            board.getSquare(0, 4).setPawn(redPawn2);
                                            board.getSquare(0, 4).setHasPawn(true);
                                            view.updateFirstMove(redPawn2, redButtons[1]);
                                            cardHasBeenPlayed = true;
                                        } else {
                                            cardHasBeenPlayed = false;
                                            redPawn2.setPawnOn(false);
                                        }
                                    } else {
                                        cardHasBeenPlayed = true;
                                        redPawn2.setPawnOn(true);
                                        redPawn2.setPosX(0);
                                        redPawn2.setPosY(4);
                                        board.getSquare(0, 4).setPawn(redPawn2);
                                        board.getSquare(0, 4).setHasPawn(true);
                                        view.updateFirstMove(redPawn2, redButtons[1]);
                                    }
                                }
                            } else {
                                board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);
                                board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                                prevX = redPawn2.getPosX();
                                prevY = redPawn2.getPosY();
                                if (deck.getReceivedCard() instanceof No1Card) {
                                    No1Card c = new No1Card();
                                    c.movePawn(redPawn2, board);
                                    if (c.moveCheck(redPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                    } else {
                                        cardHasBeenPlayed = false;
                                        redPawn2.setPosX(prevX);
                                        redPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(redPawn2, redButtons[1]);


                                } else if (deck.getReceivedCard() instanceof No2Card) {
                                    No2Card c = new No2Card();
                                    c.movePawn(redPawn2, board);
                                    if (c.moveCheck(redPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                    } else {
                                        cardHasBeenPlayed = false;
                                        redPawn2.setPosX(prevX);
                                        redPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }

                                    view.updatePawn(redPawn2, redButtons[1]);

                                } else if (deck.getReceivedCard() instanceof No3Card) {
                                    No3Card c = new No3Card();
                                    c.movePawn(redPawn2, board);
                                    if (c.moveCheck(redPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                    } else {
                                        redPawn2.setPosX(prevX);
                                        redPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(redPawn2, redButtons[1]);

                                    if (redPawn1.getPawnOn()) {
                                        prevX = redPawn1.getPosX();
                                        prevY = redPawn1.getPosY();
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                                        c.movePawn(redPawn1, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            redPawn1.setPosX(redPawn1.getPosX());
                                            redPawn1.setPosY(redPawn1.getPosY());
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        if (redPawn1.getHomeStatus()) {
                                            view.homeZone(redPawn1, redButtons[0]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                                            redPawn1.setPosY(10);
                                            redPawn1.setPosX(10);

                                        }else {
                                            view.updatePawn(redPawn1, redButtons[0]);
                                        }

                                    }
                                } else if (deck.getReceivedCard() instanceof No4Card) {
                                    No4Card c = new No4Card();
                                    c.movePawn(redPawn2, board);
                                    if (c.moveCheck(redPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                    } else {
                                        redPawn2.setPosX(prevX);
                                        redPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(redPawn2, redButtons[1]);


                                } else if (deck.getReceivedCard() instanceof No5Card) {
                                    No5Card c = new No5Card();
                                    c.movePawn(redPawn2, board);
                                    if (c.moveCheck(redPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                    } else {
                                        redPawn2.setPosX(prevX);
                                        redPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(redPawn2, redButtons[1]);

                                    if (redPawn1.getPawnOn()) {
                                        prevX = redPawn1.getPosX();
                                        prevY = redPawn1.getPosY();
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                                        c.movePawn(redPawn1, board);
                                        if (c.moveCheck(redPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            redPawn1.setPosX(redPawn1.getPosX());
                                            redPawn1.setPosY(redPawn1.getPosY());
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {

                                            redPawn1.setPosX(prevX);
                                            redPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        if (redPawn1.getHomeStatus()) {
                                            view.homeZone(redPawn1, redButtons[0]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                                            redPawn1.setPosY(10);
                                            redPawn1.setPosX(10);

                                        }else {
                                            view.updatePawn(redPawn1, redButtons[0]);
                                        }

                                    }
                                } else if (deck.getReceivedCard() instanceof No7Card) {
                                    No7Card c = new No7Card();
                                    c.movePawn(redPawn2, board);
                                    if (c.moveCheck(redPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                    } else {

                                        redPawn2.setPosX(prevX);
                                        redPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(redPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }

                                    view.updatePawn(redPawn2, redButtons[1]);
                                } else if (deck.getReceivedCard() instanceof No8Card) {
                                    No8Card c = new No8Card();
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn2, board);
                                        if (c.moveCheck(redPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        } else {

                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(redPawn2, redButtons[1]);

                                    } else if(option==1){

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                } else if (deck.getReceivedCard() instanceof No10Card) {
                                    No10Card c = new No10Card();
                                    String[] options = {"move forward", "move 1 square backwards"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn2, board);
                                        if (c.moveCheck(redPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }




                                    } else if (option == 1){
                                        c.movePawn1Back(redPawn2,board);
                                        if (c.moveCheck(redPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                    }
                                    view.updatePawn(redPawn2, redButtons[1]);
                                } else if (deck.getReceivedCard() instanceof No11Card) {
                                    No11Card c = new No11Card();
                                    String[] options = {"Move forward","Change position with yellow pawn 1", "Change position with yellow pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn2, board);
                                        if (c.moveCheck(redPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn1.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(redPawn2, redButtons[1]);

                                    } else if(option==1&&yellowPawn1.getPawnOn()&&redPawn2.getPawnOn()){
                                        int tmpX=yellowPawn1.getPosX();
                                        int tmpY=yellowPawn1.getPosY();
                                        c.changePawn(redPawn2,yellowPawn1,board);
                                        if (c.moveCheck11(redPawn2,yellowPawn1 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        }else{
                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            yellowPawn1.setPosX(tmpX);
                                            yellowPawn1.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(yellowPawn1);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(redPawn2, redButtons[1]);
                                        view.updatePawn(yellowPawn1, yellowButtons[0]);

                                    }else if(option==2&&yellowPawn2.getPawnOn()&&redPawn2.getPawnOn()){
                                        int tmpX=yellowPawn2.getPosX();
                                        int tmpY=yellowPawn2.getPosY();
                                        c.changePawn(redPawn2,yellowPawn2,board);
                                        if (c.moveCheck11(redPawn2,yellowPawn2 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        }else{
                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            yellowPawn2.setPosX(tmpX);
                                            yellowPawn2.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(yellowPawn2);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(redPawn2, redButtons[1]);
                                        view.updatePawn(yellowPawn2, yellowButtons[1]);

                                    }
                                } else if (deck.getReceivedCard() instanceof No12Card) {
                                    No12Card c = new No12Card();
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(redPawn2, board);
                                        if (c.moveCheck(redPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            redPawn2.setPosX(prevX);
                                            redPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(redPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(redPawn2, redButtons[1]);

                                    } else if (option == 1){

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }
                                if (redPawn2.getHomeStatus()) {
                                    view.homeZone(redPawn2, redButtons[1]);
                                    cardHasBeenPlayed = true;
                                    board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                                    board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);
                                    redPawn2.setPosY(10);
                                    redPawn2.setPosX(10);

                                }
                            }


                        }

                        hasPlayerWon(redPlayer);
                    } else {
                        if (e.getSource() == yellowButtons[0]) {
                            if (!yellowPawn1.getPawnOn()) {
                                if ((deck.getReceivedCard() instanceof No12Card) || deck.getReceivedCard() instanceof No8Card) {
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        System.out.println("you cant move");
                                    } else if (option == 1){
                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        view.removeCurrentCard();
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }else if (deck.getReceivedCard() instanceof SorryCard) {
                                    SorryCard c = new SorryCard();
                                    yellowPawn1.setPawnOn(true);
                                    String[] options = {"Change position with red pawn 1", "Change position with red pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0 &&!redPawn1.getHomeStatus() && redPawn1.getPawnOn()&&!(board.getSquare(redPawn1.getPosX(),redPawn1.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(yellowPawn1, redPawn1, board);
                                        if (c.moveCheck(yellowPawn1, board)) {

                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                            yellowPawn1.setPawnOn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(yellowPawn1, yellowButtons[0]);

                                        }else {
                                            yellowPawn1.setPawnOn(false);
                                            yellowPawn1.setPosX(10);
                                            yellowPawn1.setPosY(10);
                                        }


                                    } else if (option == 1&& !redPawn2.getHomeStatus() && redPawn2.getPawnOn()&&!(board.getSquare(redPawn2.getPosX(),redPawn2.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(yellowPawn1, redPawn2, board);
                                        if (c.moveCheck(yellowPawn1, board)) {

                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                            yellowPawn1.setPawnOn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(yellowPawn1, yellowButtons[0]);
                                        }else {
                                            yellowPawn1.setPawnOn(false);
                                            yellowPawn1.setPosX(10);
                                            yellowPawn1.setPosY(10);
                                        }
                                    } else {
                                        yellowPawn1.setPawnOn(false);
                                        yellowPawn1.setPosX(10);
                                        yellowPawn1.setPosY(10);
                                    }


                                }else if ((deck.getReceivedCard() instanceof No1Card) || (deck.getReceivedCard() instanceof No2Card)) {
                                    if (board.getSquare(15, 11).getHasPawn()) {
                                        if (board.getSquare(15, 11).getPawn().getColor() != Color.yellow) {
                                            yellowPawn1.setPawnOn(true);
                                            board.getSquare(15, 11).getPawn().setSendStart(true);
                                            yellowPawn1.setPosX(15);
                                            yellowPawn1.setPosY(11);
                                            board.getSquare(15, 11).setHasPawn(true);
                                            board.getSquare(15, 11).setPawn(yellowPawn1);
                                            view.updateFirstMove(yellowPawn1, yellowButtons[0]);
                                            cardHasBeenPlayed = true;
                                        } else {
                                            yellowPawn1.setPawnOn(false);
                                        }
                                    } else {
                                        cardHasBeenPlayed = true;
                                        yellowPawn1.setPawnOn(true);
                                        yellowPawn1.setPosX(15);
                                        yellowPawn1.setPosY(11);
                                        board.getSquare(15, 11).setHasPawn(true);
                                        board.getSquare(15, 11).setPawn(yellowPawn1);
                                        view.updateFirstMove(yellowPawn1, yellowButtons[0]);
                                    }
                                }
                            } else {
                                board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                                board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                                prevX = yellowPawn1.getPosX();
                                prevY = yellowPawn1.getPosY();
                                if (deck.getReceivedCard() instanceof No1Card) {
                                    No1Card c = new No1Card();
                                    c.movePawn(yellowPawn1, board);
                                    if (c.moveCheck(yellowPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                    } else {
                                        cardHasBeenPlayed = false;

                                        yellowPawn1.setPosX(prevX);
                                        yellowPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }

                                    view.updatePawn(yellowPawn1, yellowButtons[0]);


                                } else if (deck.getReceivedCard() instanceof No2Card) {
                                    No2Card c = new No2Card();
                                    c.movePawn(yellowPawn1, board);
                                    if (c.moveCheck(yellowPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn1.setPosX(prevX);
                                        yellowPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(yellowPawn1, yellowButtons[0]);

                                } else if (deck.getReceivedCard() instanceof No3Card) {
                                    No3Card c = new No3Card();
                                    c.movePawn(yellowPawn1, board);
                                    if (c.moveCheck(yellowPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn1.setPosX(prevX);
                                        yellowPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(yellowPawn1, yellowButtons[0]);

                                    if (yellowPawn2.getPawnOn()) {
                                        prevX = yellowPawn2.getPosX();
                                        prevY = yellowPawn2.getPosY();
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(null);
                                        c.movePawn(yellowPawn2, board);
                                        if (c.moveCheck(yellowPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        } else {

                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        if (yellowPawn2.getHomeStatus()) {
                                            view.homeZone(yellowPawn2, yellowButtons[1]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(null);
                                            yellowPawn2.setPosY(10);
                                            yellowPawn2.setPosX(10);

                                        }else {
                                            view.updatePawn(yellowPawn2, yellowButtons[1]);
                                        }

                                    }

                                } else if (deck.getReceivedCard() instanceof No4Card) {
                                    No4Card c = new No4Card();
                                    c.movePawn(yellowPawn1, board);
                                    if (c.moveCheck(yellowPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn1.setPosX(prevX);
                                        yellowPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(yellowPawn1, yellowButtons[0]);


                                } else if (deck.getReceivedCard() instanceof No5Card) {
                                    No5Card c = new No5Card();
                                    c.movePawn(yellowPawn1, board);
                                    if (c.moveCheck(yellowPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn1.setPosX(prevX);
                                        yellowPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(yellowPawn1, yellowButtons[0]);

                                    if (yellowPawn2.getPawnOn()) {
                                        prevX = yellowPawn2.getPosX();
                                        prevY = yellowPawn2.getPosY();
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(null);
                                        c.movePawn(yellowPawn2, board);
                                        if (c.moveCheck(yellowPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        } else {

                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        if (yellowPawn2.getHomeStatus()) {
                                            view.homeZone(yellowPawn2, yellowButtons[1]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(null);
                                            yellowPawn2.setPosY(10);
                                            yellowPawn2.setPosX(10);

                                        }else {
                                            view.updatePawn(yellowPawn2, yellowButtons[1]);
                                        }

                                    }
                                } else if (deck.getReceivedCard() instanceof No7Card) {
                                    No7Card c = new No7Card();
                                    c.movePawn(yellowPawn1, board);
                                    if (c.moveCheck(yellowPawn1, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn1.setPosX(prevX);
                                        yellowPawn1.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(yellowPawn1, yellowButtons[0]);
                                } else if (deck.getReceivedCard() instanceof No8Card) {
                                    No8Card c = new No8Card();

                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn1, board);
                                        if (c.moveCheck(yellowPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        } else {

                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        view.updatePawn(yellowPawn1, yellowButtons[0]);

                                    } else if(option==1){

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                } else if (deck.getReceivedCard() instanceof No10Card) {
                                    No10Card c = new No10Card();
                                    String[] options = {"move forward", "move 1 square backwards"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn1, board);
                                        if (c.moveCheck(yellowPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }



                                    } else if (option == 1){
                                        c.movePawn1Back(yellowPawn1,board);
                                        if (c.moveCheck(yellowPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                    }
                                    view.updatePawn(yellowPawn1, yellowButtons[0]);
                                }else if (deck.getReceivedCard() instanceof No11Card) {
                                    No11Card c = new No11Card();
                                    String[] options = {"Move forward","Change position with red pawn 1", "Change position with red pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn1, board);
                                        if (c.moveCheck(yellowPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(yellowPawn1, yellowButtons[0]);

                                    } else if(option==1&&redPawn1.getPawnOn()&&yellowPawn1.getPawnOn()){
                                        int tmpX=redPawn1.getPosX();
                                        int tmpY=redPawn1.getPosY();
                                        c.changePawn(yellowPawn1,redPawn1,board);
                                        if (c.moveCheck11(yellowPawn1,redPawn1 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        }else{
                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            redPawn1.setPosX(tmpX);
                                            redPawn1.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(redPawn1);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(yellowPawn1, yellowButtons[0]);
                                        view.updatePawn(redPawn1, redButtons[0]);

                                    }else if(option==2&&yellowPawn1.getPawnOn()&&redPawn2.getPawnOn()){
                                        int tmpX=yellowPawn1.getPosX();
                                        int tmpY=yellowPawn1.getPosY();
                                        c.changePawn(yellowPawn1,redPawn2,board);
                                        if (c.moveCheck11(yellowPawn1,redPawn2 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        }else{
                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            redPawn2.setPosX(tmpX);
                                            redPawn2.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(redPawn2);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(yellowPawn1, yellowButtons[0]);
                                        view.updatePawn(redPawn2, redButtons[1]);

                                    }
                                }else if (deck.getReceivedCard() instanceof No12Card) {
                                    No12Card c = new No12Card();
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn1, board);
                                        if (c.moveCheck(yellowPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(yellowPawn1, yellowButtons[0]);

                                    } else if(option == 1){

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }

                            }
                            if (yellowPawn1.getHomeStatus()) {
                                view.homeZone(yellowPawn1, yellowButtons[0]);
                                cardHasBeenPlayed = true;
                                board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                                board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                                yellowPawn1.setPosY(10);
                                yellowPawn1.setPosX(10);

                            }


                        }
                        if (e.getSource() == yellowButtons[1]) {
                            if (!yellowPawn2.getPawnOn()) {
                                if ((deck.getReceivedCard() instanceof No12Card) || deck.getReceivedCard() instanceof No8Card) {
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        System.out.println("you cant move");
                                    } else if (option == 1){
                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        view.removeCurrentCard();
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }else if (deck.getReceivedCard() instanceof SorryCard) {
                                    SorryCard c = new SorryCard();
                                    yellowPawn2.setPawnOn(true);
                                    String[] options = {"Change position with red pawn 1", "Change position with red pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0&& !redPawn1.getHomeStatus() && redPawn1.getPawnOn()&&!(board.getSquare(redPawn1.getPosX(),redPawn1.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(yellowPawn2, redPawn1, board);
                                        if (c.moveCheck(yellowPawn2, board)) {

                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                            yellowPawn2.setPawnOn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(yellowPawn2, yellowButtons[1]);

                                        } else {
                                            yellowPawn2.setPawnOn(false);
                                            yellowPawn2.setPosX(10);
                                            yellowPawn2.setPosY(10);
                                        }


                                    } else if (option == 1 && !redPawn2.getHomeStatus()&& redPawn2.getPawnOn()&&!(board.getSquare(redPawn2.getPosX(),redPawn2.getPosY()) instanceof Safe_zone)) {
                                        c.moveSorryPawn(yellowPawn2, redPawn2, board);
                                        if (c.moveCheck(yellowPawn2, board)) {

                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                            yellowPawn2.setPawnOn(true);
                                            cardHasBeenPlayed = true;
                                            view.updatePawn(yellowPawn2, yellowButtons[1]);
                                        }else {
                                            yellowPawn2.setPawnOn(false);
                                            yellowPawn2.setPosX(10);
                                            yellowPawn2.setPosY(10);
                                        }
                                    } else {
                                        yellowPawn2.setPawnOn(false);
                                        yellowPawn2.setPosX(10);
                                        yellowPawn2.setPosY(10);
                                    }

                                }else if ((deck.getReceivedCard() instanceof No1Card) || (deck.getReceivedCard() instanceof No2Card)) {
                                    if (board.getSquare(15, 11).getHasPawn()) {
                                        if (board.getSquare(15, 11).getPawn().getColor() != Color.yellow) {
                                            yellowPawn2.setPawnOn(true);
                                            board.getSquare(15, 11).getPawn().setSendStart(true);
                                            yellowPawn2.setPosX(15);
                                            yellowPawn2.setPosY(11);
                                            board.getSquare(15, 11).setHasPawn(true);
                                            board.getSquare(15, 11).setPawn(yellowPawn2);
                                            view.updateFirstMove(yellowPawn2, yellowButtons[1]);
                                            cardHasBeenPlayed = true;
                                        } else {
                                            yellowPawn2.setPawnOn(false);
                                        }
                                    } else {
                                        cardHasBeenPlayed = true;
                                        yellowPawn2.setPawnOn(true);
                                        yellowPawn2.setPosX(15);
                                        yellowPawn2.setPosY(11);
                                        board.getSquare(15, 11).setHasPawn(true);
                                        board.getSquare(15, 11).setPawn(yellowPawn2);
                                        view.updateFirstMove(yellowPawn2, yellowButtons[1]);
                                    }
                                }
                            } else {
                                board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                                board.getSquare(yellowPawn2.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                                prevX = yellowPawn2.getPosX();
                                prevY = yellowPawn2.getPosY();
                                if (deck.getReceivedCard() instanceof No1Card) {
                                    No1Card c = new No1Card();
                                    c.movePawn(yellowPawn2, board);
                                    if (c.moveCheck(yellowPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn2.setPosX(prevX);
                                        yellowPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(yellowPawn2, yellowButtons[1]);

                                } else if (deck.getReceivedCard() instanceof No2Card) {
                                    No2Card c = new No2Card();
                                    c.movePawn(yellowPawn2, board);
                                    if (c.moveCheck(yellowPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn2.setPosX(prevX);
                                        yellowPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(yellowPawn2, yellowButtons[1]);

                                } else if (deck.getReceivedCard() instanceof No3Card) {
                                    No3Card c = new No3Card();
                                    c.movePawn(yellowPawn2, board);
                                    if (c.moveCheck(yellowPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                    } else {
                                        yellowPawn2.setPosX(prevX);
                                        yellowPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                        view.updatePawn(yellowPawn2, yellowButtons[1]);


                                    if (yellowPawn1.getPawnOn()) {
                                        prevX = yellowPawn1.getPosX();
                                        prevY = yellowPawn1.getPosY();
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                                        c.movePawn(yellowPawn1, board);
                                        if (c.moveCheck(yellowPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            yellowPawn1.setPosX(yellowPawn1.getPosX());
                                            yellowPawn1.setPosY(yellowPawn1.getPosY());
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        } else {

                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        if (yellowPawn1.getHomeStatus()) {
                                            view.homeZone(yellowPawn1, yellowButtons[0]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                                            yellowPawn1.setPosY(10);
                                            yellowPawn1.setPosX(10);
                                        }else {
                                            view.updatePawn(yellowPawn1, yellowButtons[0]);
                                        }

                                    }
                                } else if (deck.getReceivedCard() instanceof No4Card) {
                                    No4Card c = new No4Card();
                                    c.movePawn(yellowPawn2, board);
                                    if (c.moveCheck(yellowPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn2.setPosX(prevX);
                                        yellowPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }


                                    view.updatePawn(yellowPawn2, yellowButtons[1]);


                                } else if (deck.getReceivedCard() instanceof No5Card) {
                                    No5Card c = new No5Card();
                                    c.movePawn(yellowPawn2, board);
                                    if (c.moveCheck(yellowPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                    } else {
                                        yellowPawn2.setPosX(prevX);
                                        yellowPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }

                                    view.updatePawn(yellowPawn2, yellowButtons[1]);

                                    if (yellowPawn1.getPawnOn()) {
                                        prevX = yellowPawn1.getPosX();
                                        prevY = yellowPawn1.getPosY();
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                                        c.movePawn(yellowPawn1, board);
                                        if (c.moveCheck(yellowPawn1, board)) {
                                            cardHasBeenPlayed = true;
                                            yellowPawn1.setPosX(yellowPawn1.getPosX());
                                            yellowPawn1.setPosY(yellowPawn1.getPosY());
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(yellowPawn1);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(true);
                                        } else {

                                            yellowPawn1.setPosX(prevX);
                                            yellowPawn1.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        if (yellowPawn1.getHomeStatus()) {
                                            view.homeZone(yellowPawn1, yellowButtons[0]);
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                                            board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                                            yellowPawn1.setPosY(10);
                                            yellowPawn1.setPosX(10);
                                        }else {
                                            view.updatePawn(yellowPawn1, yellowButtons[0]);
                                        }

                                    }
                                } else if (deck.getReceivedCard() instanceof No7Card) {
                                    No7Card c = new No7Card();
                                    c.movePawn(yellowPawn2, board);
                                    if (c.moveCheck(yellowPawn2, board)) {
                                        cardHasBeenPlayed = true;
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                    } else {

                                        yellowPawn2.setPosX(prevX);
                                        yellowPawn2.setPosY(prevY);
                                        board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                        board.getSquare(prevX, prevY).setHasPawn(true);
                                    }
                                    view.updatePawn(yellowPawn2, yellowButtons[1]);
                                } else if (deck.getReceivedCard() instanceof No8Card) {
                                    No8Card c = new No8Card();
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn2, board);
                                        if (c.moveCheck(yellowPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        } else {

                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                        view.updatePawn(yellowPawn2, yellowButtons[1]);

                                    } else if(option==1) {

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                } else if (deck.getReceivedCard() instanceof No10Card) {
                                    No10Card c = new No10Card();
                                    String[] options = {"move forward", "move 1 square backwards"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn2, board);
                                        if (c.moveCheck(yellowPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }



                                    } else if(option == 1){
                                        c.movePawn1Back(yellowPawn2,board);
                                        if (c.moveCheck(yellowPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }
                                    }
                                    view.updatePawn(yellowPawn2, yellowButtons[1]);
                                }else if (deck.getReceivedCard() instanceof No11Card) {
                                    No11Card c = new No11Card();
                                    String[] options = {"Move forward","Change position with red pawn 1", "Change position with red pawn 2"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn2, board);
                                        if (c.moveCheck(yellowPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(yellowPawn2, yellowButtons[1]);

                                    }else if(option==1&&redPawn1.getPawnOn()&&yellowPawn2.getPawnOn()){
                                        int tmpX=redPawn1.getPosX();
                                        int tmpY=redPawn1.getPosY();
                                        c.changePawn(yellowPawn2,redPawn1,board);
                                        if (c.moveCheck11(yellowPawn2,redPawn1 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(redPawn1);
                                            board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(true);
                                        }else{
                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            redPawn1.setPosX(tmpX);
                                            redPawn1.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(redPawn1);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(yellowPawn2, yellowButtons[1]);
                                        view.updatePawn(redPawn1, redButtons[0]);

                                    }else if(option==2&&yellowPawn2.getPawnOn()&&redPawn2.getPawnOn()){
                                        int tmpX=yellowPawn2.getPosX();
                                        int tmpY=yellowPawn2.getPosY();
                                        c.changePawn(yellowPawn2,redPawn2,board);
                                        if (c.moveCheck11(yellowPawn2,redPawn2 ,board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(redPawn2);
                                            board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(true);
                                        }else{
                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                            redPawn2.setPosX(tmpX);
                                            redPawn2.setPosY(tmpY);
                                            board.getSquare(tmpX, tmpY).setPawn(redPawn2);
                                            board.getSquare(tmpX, tmpY).setHasPawn(true);
                                        }
                                        view.updatePawn(yellowPawn2, yellowButtons[1]);
                                        view.updatePawn(redPawn2, redButtons[1]);

                                    }
                                }else if (deck.getReceivedCard() instanceof No12Card) {
                                    No12Card c = new No12Card();
                                    String[] options = {"move forward", "get a card"};
                                    int option = JOptionPane.showOptionDialog(view, "Choose option", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (option == 0) {
                                        c.movePawn(yellowPawn2, board);
                                        if (c.moveCheck(yellowPawn2, board)) {
                                            cardHasBeenPlayed = true;
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(yellowPawn2);
                                            board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(true);
                                        } else {
                                            yellowPawn2.setPosX(prevX);
                                            yellowPawn2.setPosY(prevY);
                                            board.getSquare(prevX, prevY).setPawn(yellowPawn2);
                                            board.getSquare(prevX, prevY).setHasPawn(true);
                                        }

                                        view.updatePawn(yellowPawn2, yellowButtons[1]);

                                    } else if (option == 1) {

                                        if (deck.getCardSum() == 0) {
                                            deck.initDeck();
                                        }
                                        deck.ReceiveCard();
                                        hasCard = true;
                                        view.updateCurrentCard(deck.getReceivedCard());
                                        cardHasBeenPlayed = false;
                                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                                    }
                                }
                            }
                            if (yellowPawn2.getHomeStatus()) {
                                view.homeZone(yellowPawn2, yellowButtons[1]);
                                cardHasBeenPlayed = true;
                                board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                                board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(null);
                                yellowPawn2.setPosY(10);
                                yellowPawn2.setPosX(10);

                            }

                        }
                        hasPlayerWon(yellowPlayer);

                    }




                    if (redPawn1.getSendStart()) {
                        redPawn1.setSendStart(false);
                        redPawn1.setPawnOn(false);
                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                        board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                        redPawn1.setPosX(10);
                        redPawn1.setPosY(10);
                        view.startZone(redPawn1, redButtons[0]);
                    } else if (redPawn2.getSendStart()) {
                        redPawn2.setSendStart(false);
                        redPawn2.setPawnOn(false);
                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                        board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);
                        redPawn2.setPosX(10);
                        redPawn2.setPosY(10);
                        view.startZone(redPawn2, redButtons[1]);
                    } else if (yellowPawn1.getSendStart()) {
                        yellowPawn1.setSendStart(false);
                        yellowPawn1.setPawnOn(false);
                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                        board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                        yellowPawn1.setPosX(10);
                        yellowPawn1.setPosY(10);
                        view.startZone(yellowPawn1, yellowButtons[0]);
                    } else if (yellowPawn2.getSendStart()) {
                        yellowPawn2.setSendStart(false);
                        yellowPawn2.setPawnOn(false);
                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                        board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(null);
                        yellowPawn2.setPosX(10);
                        yellowPawn2.setPosY(10);
                        view.startZone(yellowPawn2, yellowButtons[1]);
                    }
                }
                if (cardHasBeenPlayed) {
                    if (!(deck.getReceivedCard() instanceof No2Card)) {
                        changeTurn();
                    }
                    view.removeCurrentCard();
                    cardHasBeenPlayed = false;
                    hasCard = false;
                }

            }
        }

    }

    /**
     * In this class we are setting the functionality for the card buttons and changing the received card if it can
     */
    public class CardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (getHasGameEnded()) {
                if (e.getSource() == view.getBackCard()) {
                    if (!hasCard) {
                        if (deck.getCardSum() == 0) {
                            deck.initDeck();
                        }
                        deck.ReceiveCard();
                        hasCard = true;
                        view.updateCurrentCard(deck.getReceivedCard());
                        cardHasBeenPlayed = false;
                        System.out.println(currPlayer.getName() + " got: " + deck.getReceivedCard().getNumber());
                    }

                }
            }
        }
    }

    /**
     * In this method we are making the option for the player to fold by checking the moves that he can possibly do
     * @param c the card that the player received
     * @param p1 the player
     * @param board the board of the game
     */

    public void Fold(Cards c, Player p1, Board board) {

        if (p1.getColor() == Color.red) {
            if(c instanceof No10Card){
                if(redPawn2.getPawnOn()||redPawn1.getPawnOn()) {
                    if (!(c.moveCheck(redPawn1, board) || c.moveCheck(redPawn2, board))) {
                        System.out.println("You have to go 1 square backwards");
                    }
                }else{
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    changeTurn();
                }
            }else if(c instanceof No11Card &&( (!yellowPawn1.getPawnOn()&& !yellowPawn2.getPawnOn())||( redPawn2.getPawnOn() & redPawn1.getPawnOn()))){
                System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                view.removeCurrentCard();
                hasCard = false;
                changeTurn();
            }else if(c instanceof SorryCard){
                if(!(redPawn1.getPawnOn() &&redPawn2.getPawnOn())){
                    if((yellowPawn1.getPawnOn()&&!(board.getSquare(yellowPawn1.getPosX(),yellowPawn1.getPosY()) instanceof Safe_zone))
                            ||(yellowPawn2.getPawnOn()&&!(board.getSquare(yellowPawn2.getPosX(),yellowPawn2.getPosY()) instanceof Safe_zone))){
                        System.out.println("you can move");
                    }else {
                        System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                        view.removeCurrentCard();
                        hasCard = false;
                        changeTurn();
                    }
                }else {
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    changeTurn();
                }
            }else if (c instanceof No1Card || c instanceof No2Card) {
                if (!(redPawn1.getPawnOn() || redPawn2.getPawnOn())) {
                    System.out.println("you can move");
                } else {
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    if (!(deck.getReceivedCard() instanceof No2Card)) {
                        changeTurn();
                    }
                }

            } else {
                prevX = redPawn1.getPosX();
                prevY = redPawn1.getPosY();
                int prevX2 = redPawn2.getPosX();
                int prevY2 = redPawn2.getPosY();
                board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setHasPawn(false);
                board.getSquare(redPawn1.getPosX(), redPawn1.getPosY()).setPawn(null);
                board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setHasPawn(false);
                board.getSquare(redPawn2.getPosX(), redPawn2.getPosY()).setPawn(null);

                c.movePawn(redPawn1, board);
                c.movePawn(redPawn2, board);
                if (!((c.moveCheck(redPawn1, board)) || (c.moveCheck(redPawn2, board)))) {
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    if (!(deck.getReceivedCard() instanceof No2Card)) {
                        changeTurn();
                    }
                } else {
                    System.out.println("You can move");
                }
                redPawn1.setPosY(prevY);
                redPawn1.setPosX(prevX);
                redPawn2.setPosX(prevX2);
                redPawn2.setPosY(prevY2);
                board.getSquare(prevX, prevY).setHasPawn(true);
                board.getSquare(prevX, prevY).setPawn(redPawn1);
                board.getSquare(prevX2, prevY2).setHasPawn(true);
                board.getSquare(prevX2, prevY2).setPawn(redPawn2);
            }
        } else{
            if(c instanceof No10Card) {
                if(yellowPawn1.getPawnOn()||yellowPawn2.getPawnOn()) {
                    if (!(c.moveCheck(yellowPawn1, board) || c.moveCheck(yellowPawn2, board))) {
                        System.out.println("You have to go 1 square backwards");
                    }
                }else{
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    changeTurn();
                }
            }else if(c instanceof No11Card && (!redPawn1.getPawnOn()&& !redPawn2.getPawnOn())){
                System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                view.removeCurrentCard();
                hasCard = false;
                changeTurn();
            }else if(c instanceof SorryCard ){
                if(!(yellowPawn1.getPawnOn() &&yellowPawn2.getPawnOn())){
                    if((redPawn1.getPawnOn()&&!(board.getSquare(redPawn1.getPosX(),redPawn1.getPosY()) instanceof Safe_zone))
                            ||(redPawn2.getPawnOn()&&!(board.getSquare(redPawn2.getPosX(),redPawn2.getPosY()) instanceof Safe_zone))){
                        System.out.println("you can move");
                    } else {
                        System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                        view.removeCurrentCard();
                        hasCard = false;
                        changeTurn();
                    }
                }else {
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    changeTurn();
                }
            }else if (c instanceof No1Card || c instanceof No2Card) {
                if (!(yellowPawn1.getPawnOn() || yellowPawn2.getPawnOn())) {
                    System.out.println("you can move");
                } else {
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    if (!(deck.getReceivedCard() instanceof No2Card)) {
                        changeTurn();
                    }
                }
            } else {
                prevX = yellowPawn1.getPosX();
                prevY = yellowPawn1.getPosY();
                int prevX2 = yellowPawn2.getPosX();
                int prevY2 = yellowPawn2.getPosY();
                board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setHasPawn(false);
                board.getSquare(yellowPawn1.getPosX(), yellowPawn1.getPosY()).setPawn(null);
                board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setHasPawn(false);
                board.getSquare(yellowPawn2.getPosX(), yellowPawn2.getPosY()).setPawn(null);
                c.movePawn(yellowPawn1, board);
                c.movePawn(yellowPawn2, board);
                if (!((c.moveCheck(yellowPawn1, board)) || (c.moveCheck(yellowPawn2, board))) && (!(c instanceof No12Card || c instanceof No8Card))) {
                    System.out.println(currPlayer.getName() + " folded the card: " + deck.getReceivedCard().getNumber());
                    view.removeCurrentCard();
                    hasCard = false;
                    if (!(deck.getReceivedCard() instanceof No2Card)) {
                        changeTurn();
                    }
                } else {
                    System.out.println("You can move");
                }
                yellowPawn1.setPosY(prevY);
                yellowPawn1.setPosX(prevX);
                yellowPawn2.setPosX(prevX2);
                yellowPawn2.setPosY(prevY2);
                board.getSquare(prevX, prevY).setHasPawn(true);
                board.getSquare(prevX, prevY).setPawn(yellowPawn1);
                board.getSquare(prevX2, prevY2).setHasPawn(true);
                board.getSquare(prevX2, prevY2).setPawn(yellowPawn2);
            }
        }

    }

    /**
     * In this method we are setting the fold button when is pressed we are checking if the player can fold with the fold() method
     */
    public class FoldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getHasGameEnded()) {
                if (e.getSource() == view.getFoldButton()) {
                    if (hasCard) {
                        if (deck.getReceivedCard() instanceof No1Card) {
                            Cards c = new No1Card();
                            Fold(c, currPlayer, board);
                        } else if (deck.getReceivedCard() instanceof No2Card) {
                            Cards c = new No2Card();
                            Fold(c, currPlayer, board);
                        } else if (deck.getReceivedCard() instanceof No3Card) {
                            Cards c = new No3Card();
                            Fold(c, currPlayer, board);
                        } else if (deck.getReceivedCard() instanceof No4Card) {
                            Cards c = new No4Card();
                            Fold(c, currPlayer, board);
                        } else if (deck.getReceivedCard() instanceof No5Card) {
                            Cards c = new No5Card();
                            Fold(c, currPlayer, board);
                        } else if (deck.getReceivedCard() instanceof No7Card) {
                            Cards c = new No7Card();
                            Fold(c, currPlayer,board);
                        } else if (deck.getReceivedCard() instanceof No10Card) {
                            Cards c = new No10Card();
                            Fold(c, currPlayer, board);
                        } else if (deck.getReceivedCard() instanceof No11Card) {
                            Cards c = new No11Card();
                            Fold(c, currPlayer, board);
                        } else if (deck.getReceivedCard() instanceof SorryCard) {
                            Cards c = new SorryCard();
                            Fold(c, currPlayer,board);
                        }

                    } else {
                        System.out.println("get a card");
                    }


                }
            }
        }
    }

    /**
     * In this method we are setting the buttons for the menu
     */
        public class MenuListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view.getTwoPlayers()) {
                setHasGameEnded(false);
                view.dispose();
                initialize();

            }
            if (e.getSource() == view.getThreePlayers()) {
                setHasGameEnded(false);
                view.dispose();
                initialize();
            }
            if (e.getSource() == view.getFourPlayers()) {
                setHasGameEnded(false);
                view.dispose();
                initialize();
            }
            if (e.getSource() == view.getExit()) exit(0);

        }
    }

    /**
     * In this method we are checking if the player won the game(if both of his pawn are at home)
     * @param p1 the player
     */
    public void hasPlayerWon(Player p1) {
        if (p1.getColor() == Color.red) {
            if ((redPawn1.getHomeStatus()) && (redPawn2.getHomeStatus())) {
                view.winMessage("Player 1 (red) won the game");
                setHasGameEnded(true);

            }
        } else if ((yellowPawn1.getHomeStatus()) && (yellowPawn2.getHomeStatus())) {
            view.winMessage("Player 2 (yellow) won the game");
            setHasGameEnded(true);
        }

    }

    /**
     * In this method we are setting the variable hasGameEnded(true/false)
     * @pre A status for the game(true/false) must be given
     * @param hasGameEnded true if the game has ended false otherwise
     */

    public void setHasGameEnded(boolean hasGameEnded) {
        this.hasGameEnded = hasGameEnded;
    }

    /**
     * In this method we are returning the status of the game(true if it has ended,false otherwise)
     * @pre a game status must be set
     * @return the status of the game
     */
    public boolean getHasGameEnded() {
        return !hasGameEnded;
    }


    public static void main(String[] args) {
        Controller game = new Controller();
        game.initialize();
    }


}

