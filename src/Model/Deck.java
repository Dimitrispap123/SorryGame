package Model;

import Model.Cards.Cards;
import Model.Cards.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * In this class we are declaring the attributes of the deck
 *
 * @author Dimitris Papadopoulos csd4976
 */
public  class  Deck {
    /**
     * The received card at this moment
     */
    private Cards receivedCard;
    /**
     * The number of the cards that left
     */
    int cardSum=44;


    ArrayList<Cards> deck;

    /**
     * In this method we are initializing the deck and randomize it
     * @post Initializes the deck with the cards and randomize it
     **/
    public void initDeck() {
        cardSum=44;
        deck=new ArrayList<>();

        for (int i=0;i<4;i++) {
            Cards card = new No1Card();
            deck.add(card);
            card = new No2Card();
            deck.add(card);
            card = new No3Card();
            deck.add(card);
            card = new No4Card();
            deck.add(card);
            card = new No5Card();
            deck.add(card);
            card = new No7Card();
            deck.add(card);
            card = new No8Card();
            deck.add(card);
            card = new No10Card();
            deck.add(card);
            card = new No11Card();
            deck.add(card);
            card = new No12Card();
            deck.add(card);
            card = new SorryCard();
            deck.add(card);
        }
        Collections.shuffle(deck);
    }

    /**
     * In this method we are changing the received card and reducing the cards left
     * @post changing the received card and reducing the cards left
     */
    public void ReceiveCard(){
        receivedCard= deck.get(cardSum-1);
        cardSum--;
    }

    /**
     * In this method we are returning the card tha we received
     * @pre A card must be received
     * @return the received card
     */
    public Cards getReceivedCard(){
        return receivedCard;
    }
    /**
     * In this method we are returning how many cards left
     * @return the cards left
     */
    public int getCardSum() {return cardSum;}

}
