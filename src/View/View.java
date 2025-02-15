package View;

import Model.Cards.Cards;
import Model.Pawn;

import javax.swing.*;
import java.awt.*;

/**
 * In this class we are showing the model to the user with graphics
 *
 * @author Dimitris Papadopoulos csd4976
 */

public class View extends JFrame {


    /**
     * The menu button for the save game option
     */
    JMenuItem save;
    /**
     * The menu button for continuing the game option
     */
    JMenuItem cont;
    /**
     * The menu button for exiting the game option
     */
    JMenuItem exit;
    /**
     * The menu button for the 2 players game mode option
     */
    JMenuItem twoP;
    /**
     * The menu button for the 3 players game mode option
     */
    JMenuItem threeP;
    /**
     * The menu button for the 4 players game mode option
     */
    JMenuItem fourP;
    /**
     * The red pawns buttons
     */
    JButton[] redPawns;
    /**
     * The yellow pawns buttons
     */
    JButton[] yellowPawns;
    /**
     * The cells of the game
     */
    JLabel[][] cells;
    /**
     * The bar of the menu
     */
    private JMenuBar menuBar;
    /**
     * The panel of the game
     */

    private JLayeredPane mainPanel;
    /**
     * The background of the panel
     */
    private JLabel background;
    /**
     * The sorry image in the middle of the board
     */
    private JLabel boardSorryBackground;
    /**
     * The board background
     */
    private JLabel boardBackground;
    /**
     * The image for the received card
     */
    private JLabel receiveCard;
    /**
     * The button of the current card
     */
    private JButton currentCard;
    /**
     * The button of the deck
     */
    private JButton backCard;
    /**
     * The button for folding
     */
    JButton foldButton;
    /**
     * The area for the info of the game
     */
    JTextArea infoBox;


    /**
     * In this method we are setting the background of the game
     */
    public void setBackground() {
        background = new JLabel(new ImageIcon("src/images/background.png"));
        background.setBounds(0, 20, 1000, 700);
    }
    /**
     * In this method we are setting the background of the board
     */
    public void setBoardBackground() {
        boardBackground = new JLabel("");
        boardBackground.setBounds(40, 60, 560, 560);
        boardBackground.setBackground(Color.CYAN);
        boardBackground.setOpaque(true);


    }
    /**
     * In this method we are setting the sorry image in the middle of the board
     */
    public void setSorryBackground(){
        Image image = new ImageIcon("src/images/sorryImage.png").getImage();
        image=image.getScaledInstance(207,60, Image.SCALE_SMOOTH);
        ImageIcon image2 = new ImageIcon(image);
        boardSorryBackground = new JLabel(image2);
        boardSorryBackground.setBounds(210, 280, 207, 60);
    }
    /**
     * In this method we are setting the graphics for the 2 home zones of the board
     */
    public void setHomeZones(){
        JLabel yellowHomeZone = new JLabel("HOME");
        yellowHomeZone.setFont(new Font("SERIF",Font.BOLD,20));
        yellowHomeZone.setVerticalAlignment(JLabel.TOP);
        yellowHomeZone.setHorizontalAlignment(JLabel.CENTER);
        yellowHomeZone.setBounds(495,330,90,90);
        yellowHomeZone.setBackground(Color.white);
        yellowHomeZone.setOpaque(true);
        yellowHomeZone.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
        JLabel redHomeZone = new JLabel("HOME");
        redHomeZone.setFont(new Font("SERIF",Font.BOLD,20));
        redHomeZone.setVerticalAlignment(JLabel.BOTTOM);
        redHomeZone.setHorizontalAlignment(JLabel.CENTER);
        redHomeZone.setBounds(55,260,90,90);
        redHomeZone.setBackground(Color.white);
        redHomeZone.setOpaque(true);
        redHomeZone.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
        mainPanel.add(redHomeZone,0);
        mainPanel.add(yellowHomeZone,0);
    }
    /**
     * In this method we are setting the graphics for the 2 start zones of the board
     */
    public void setStartZones(){
        JLabel yellowStartZone = new JLabel("START");
        yellowStartZone.setFont(new Font("SERIF",Font.BOLD,20));
        yellowStartZone.setVerticalAlignment(JLabel.TOP);
        yellowStartZone.setHorizontalAlignment(JLabel.CENTER);
        yellowStartZone.setBounds(410,530,90,90);
        yellowStartZone.setBackground(Color.white);
        yellowStartZone.setOpaque(true);
        yellowStartZone.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
        JLabel redStartZone = new JLabel("START");
        redStartZone.setFont(new Font("SERIF",Font.BOLD,20));
        redStartZone.setVerticalAlignment(JLabel.BOTTOM);
        redStartZone.setHorizontalAlignment(JLabel.CENTER);
        redStartZone.setBounds(140,60,90,90);
        redStartZone.setBackground(Color.white);
        redStartZone.setOpaque(true);
        redStartZone.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
        mainPanel.add(yellowStartZone,0);
        mainPanel.add(redStartZone,0);

    }

    /**
     * In this method we are returning the button linked in the deck
     * @return the button linked in the deck
     */
    public JButton getBackCard() {
        return backCard;
    }

    /**
     * In this method we are setting the graphics for the back card of the deck
     */
    public void setBackCard(){
        Image image = new ImageIcon("src/images/cards/backCard.png").getImage();
        image=image.getScaledInstance(100,152, Image.SCALE_SMOOTH);
        ImageIcon image2 = new ImageIcon(image);
        backCard = new JButton(image2);
        backCard.setBounds(700, 150, 100, 152);
        receiveCard= new JLabel("Receive Card               Current Card");
        receiveCard.setBounds(710,305,200,10);

    }

    /**
     * In this method we are setting the button for the current card
     */

     public void setCurrentCard(){
         currentCard = new JButton("");
         currentCard.setBounds(820, 150, 100, 152);

     }

    /**
     * In this method we are updating the current card  each time with the given card
     * @pre a card must be given
     * @param c the received card
     */

    public void updateCurrentCard(Cards c) {
        Image image= new ImageIcon(c.getImage()).getImage();
        image=image.getScaledInstance(100,152, Image.SCALE_SMOOTH);
        currentCard.setIcon(new ImageIcon(image));
        mainPanel.repaint();
    }

    /**
     * In this method we are removing the current card and leave its button empty
     */
    public void removeCurrentCard(){
        currentCard.setIcon(null);
        mainPanel.repaint();
    }

    /**
     * In this method we are setting the button for the fold option
     */
    public void setFoldButton(){
        foldButton = new JButton("FOLD");
        foldButton.setBounds(700,330,250,50);
        foldButton.setBackground(Color.red);
        foldButton.setOpaque(true);
        mainPanel.add(foldButton,0);

   }

    /**
     * In this method we are returning the button for the fold option
     * @return the button for the fold option
     */
    public JButton getFoldButton() {
        return foldButton;
    }

    /**
     * In this method we are setting the graphics for the initialization of the pawns
     */
   public void setPawns(){
        redPawns = new JButton[2];
        yellowPawns = new JButton[2];
        for (int i=0; i<2;i++) {
            Image image = new ImageIcon("src/images/pawns/redPawn"+ (i+1)+".png").getImage();
            image=image.getScaledInstance(40,40, Image.SCALE_SMOOTH);
            ImageIcon tmp = new ImageIcon(image);
            Image image2 = new ImageIcon("src/images/pawns/yellowPawn"+ (i+1)+".png").getImage();
            image2=image2.getScaledInstance(40,40, Image.SCALE_SMOOTH);
            ImageIcon tmp2 = new ImageIcon(image2);
            redPawns[i] = new JButton(tmp);
            yellowPawns[i] = new JButton(tmp2);
            redPawns[i].setBounds(145+40*i, 70, 40, 40);
            yellowPawns[i].setBounds(417+40*i, 570, 40, 40);
            mainPanel.add(redPawns[i],0);
            mainPanel.add(yellowPawns[i],0);
        }
   }

    /**
     * This method returns the array of buttons for the red pawns
     * @return the array of buttons for the red pawns
     */
   public JButton[] getRedPawns(){
        return redPawns;
   }
    /**
     * This method returns the array of buttons for the yellow pawns
     * @return the array of buttons for the yellow pawns
     */
   public JButton[] getYellowPawns() {
       return yellowPawns;
   }

    /**
     * This method updates the board by changing position of the given pawn's button
     * @param p1 the pawn that moved
     * @param but the button of the pawn that has to update its position in the board
     */

   public void updatePawn(Pawn p1,JButton but){
        int x,y,x2,y2;
        y=p1.getPosY();
        x=p1.getPosX();
        x2=cells[x][y].getX();
        y2=cells[x][y].getY();
        but.setLocation(x2, y2);
        mainPanel.repaint();
   }

    /**
     * This method updates the position from the start zone to the first square for each pawn when is called
     * @param p1 the pawn of the player that is leaving the start zone
     * @param but the button of the pawn that we will update to the write position of the panel
     */

   public void updateFirstMove(Pawn p1,JButton but){
        int x,y;
        if(p1.getColor()==Color.red) {
            x = cells[0][4].getX();
            y=cells[0][4].getY();

        }else{
            x = cells[15][11].getX();
            y=cells[15][11].getY();
        }
       but.setLocation(x, y);
       mainPanel.repaint();

   }

    /**
     * This method sets each pawn in the home zone when its called
     * @pre A pawn and its button must be given
     * @param p the pawn that got at the home zone
     * @param but the button of the pawn that we are going update to the home zone
     */
   public void homeZone(Pawn p,JButton but){
        if(p.getColor()==Color.red){
            if(p.getNum()==1){
                but.setLocation(60,270);
            }else{
                but.setLocation(100,270);
            }
        }else{
            if(p.getNum()==1){
                but.setLocation(500,370);
            }else{
                but.setLocation(540,370);
            }
        }
        but.setEnabled(false);
   }
    /**
     * This method sets each pawn in the start zone when its called
     * @pre A pawn and its button must be given
     * @param p the pawn that got send at the start zone
     * @param but the button of the pawn that we are going update to the start zone
     */
   public void startZone(Pawn p,JButton but){
       if(p.getColor()==Color.red){
           if(p.getNum()==1){
               but.setLocation(145,70);
           }else{
               but.setLocation(185,70);
           }
       }else{
           if(p.getNum()==1){
               but.setLocation(417,570);
           }else{
               but.setLocation(457,570);
           }
       }
   }

    /**
     * This method creates a message for the winner
     * @pre a string must be given
     * @param str the message we want to create
     */
   public void winMessage(String str){
       JOptionPane.showMessageDialog(mainPanel, str);
   }

    /**
     * This method creates the graphics  for the info of the game
     */
    public void setInfoBox(){
        infoBox = new JTextArea("Info Box");
        infoBox.setEditable(false);
        Font font = new Font("Serif", Font.BOLD, 15); // Change "Serif" to any font you like
        infoBox.setFont(font);
        infoBox.setBounds(700, 400,250,150);
        infoBox.setBackground(Color.white);
        infoBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        infoBox.setOpaque(true);
        mainPanel.add(infoBox,0);

    }

    /**
     * This method updates the info at the infoBox
     * @pre a string must be given
     * @param message the new info that we give
     */

    public void updateInfoBox(String message){
        infoBox.setText( message);

    }

    /**
     * In this method we are creating the graphics for the board
     */
    public void setBoard(){
        cells = new JLabel[16][16];
        for (int i=0; i<16; i++){
            for (int j = 0; j < 16; j++) {
                cells[i][j]=new JLabel();
                if(j==2 && i<6){
                    cells[i][j]=new JLabel();
                    cells[i][j].setBackground(Color.red);
                    cells[i][j].setBounds( 80, 20+i*40, 40, 40);
                }
                if(j==13 && i>9){
                    cells[i][j]=new JLabel();
                    cells[i][j].setBackground(Color.yellow);
                    cells[i][j].setBounds( 520, 20+i*40, 40, 40);
                }

                if(j==0 || j==15 || i==0 ||i==15) {


                    if (j == 0) {
                        if (i == 2 || i == 11) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/greenSlideEnd.png"));
                        }
                        if ((i > 2 && i < 6) || (i > 11 && i < 14)) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/greenSlideMedium.png"));
                        }
                        if (i == 6 || i == 14) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/greenSlideStart.png"));
                        }
                        cells[i][j].setBounds(0, 20 + i * 40, 40, 40);
                    } else if (j == 15) {
                        if (i == 1 || i == 9) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/blueSlideStart.png"));
                        }
                        if ((i > 1 && i < 4) || (i > 9 && i < 13)) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/blueSlideMedium.png"));
                        }
                        if (i == 4 || i == 13) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/blueSlideEnd.png"));
                        }
                        cells[i][j].setBounds(600, 20 + i * 40, 40, 40);

                    } else if (i == 0) {
                        if (j == 1 || j == 9) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/redSlideStart.png"));
                        }
                        if ((j > 1 && j < 4) || (j > 9 && j < 13)) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/redSlideMedium.png"));
                        }
                        if (j == 4 || j == 13) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/redSlideEnd.png"));
                        }
                        cells[i][j].setBounds(j * 40, 20, 40, 40);

                    } else {
                        if (j == 2 || j == 11) {

                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/yellowSlideEnd.png"));
                        }
                        if ((j > 2 && j < 6) || (j > 11 && j < 14)) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/yellowSlideMedium.png"));
                        }
                        if (j == 6 || j == 14) {
                            cells[i][j] = new JLabel(new ImageIcon("src/images/slides/yellowSlideStart.png"));
                        }
                        cells[i][j].setBounds( j * 40, 620, 40, 40);

                    }
                }
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visualization
                cells[i][j].setOpaque(true);
                mainPanel.add(cells[i][j],0);

            }

        }


        pack();
    }

    /**
     * In this method we are creating the graphics for the menu bar
     */
    public void setMenu() {
        menuBar = new JMenuBar();
        JMenu newGame, options;
        newGame = new JMenu("New Game");
        options = new JMenu("Options");
        save = new JMenuItem("Save game");
        cont = new JMenuItem("Continue game");
        exit = new JMenuItem("Exit");
        twoP = new JMenuItem("2 players");
        threeP = new JMenuItem("3 players");
        fourP = new JMenuItem("4 players");
        newGame.add(twoP);
        newGame.add(threeP);
        newGame.add(fourP);
        options.add(save);
        options.add(cont);
        options.add(exit);
        menuBar.add(newGame);
        menuBar.add(options);
        mainPanel.setVisible(true);

    }

    /**
     * This method returns the game option for two players from the menu
     * @return the game option for two players from the menu
     */
    public JMenuItem getTwoPlayers(){
        return twoP;
    }
    /**
     * This method returns the game option for three players from the menu
     * @return the game option for three players from the menu
     */
    public JMenuItem getThreePlayers(){
        return threeP;
    }
    /**
     * This method returns the game option for four players from the menu
     * @return the game option for four players from the menu
     */
    public JMenuItem getFourPlayers(){
        return fourP;
    }
    /**
     * This method returns the game option to exit from the menu
     * @return the game option to exit from the menu
     */
    public JMenuItem getExit(){
        return exit;
    }

    /**
     * This method calls the previous methods, that setts the graphics for all cards
     */
    public void setCards(){
        setBackCard();
        setCurrentCard();
        mainPanel.add(backCard,0);
        mainPanel.add(currentCard,0);
        mainPanel.add(receiveCard,0);
    }
    /**
     * This method calls the previous methods, that setts the graphics for all backgrounds
     */

    public void setBackgrounds(){
        setBackground();
        setBoardBackground();
        setSorryBackground();
        mainPanel.add(background);
        mainPanel.add(boardBackground,0);
        mainPanel.add(boardSorryBackground,0);
    }

    /**
     * In this method we are setting all the graphics of the panel
     */
    public void setPanel(){
        mainPanel =new JLayeredPane();
        setBackgrounds();
        setCards();
        setMenu();
        setFoldButton();
        setInfoBox();
        setBoard();
        setHomeZones();
        setStartZones();
        setPawns();
        add(mainPanel);
        JOptionPane.showMessageDialog(mainPanel, "New game started!");
    }

    /**
     * This constructor constructs all the graphics of the panel
     */
    public View() {
        super("Sorry!");
        setPanel();
        setLayout(null);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayeredPane(mainPanel);
        this.setJMenuBar(menuBar);
        setVisible(true);
        setLocationRelativeTo(null);
    }

}
