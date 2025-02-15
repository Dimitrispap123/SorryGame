package Model;

import java.awt.*;

/**
 * In this class we are declaring the attributes of a player
 *
 * @author Dimitris Papadopoulos csd4976
 */
public class Player {
    private String name;
    private Color color;
    /**
     * This constructor constructs a new player
     * @pre The color and the name must be valid
     * @post Constructs a new player setting his name and color
     * @param name the name of the player
     * @param color the color of the player
     */
    public Player(String name,Color color) {
        this.color = color;
        this.name = name;

    }
    /**
     * Returns the name of the player
     * @pre The name must be set
     * @return the name of the player
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the color of the player
     * @pre The color must be set
     * @return the color of the player
     */
    public Color getColor(){return color;}


}
