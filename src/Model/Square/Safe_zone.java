package Model.Square;

import java.awt.*;

public class Safe_zone extends Square{
    /**
     * Constructs a square, that is considered a safe zone square
     * @param posX is the X position of the square
     * @param posY is the Y position of the square
     * @param color is the color of the square
     * @post Constructs the square, that is considered a safe zone square and declares its position(X and Y) and color
     */
    public Safe_zone(int posX,int posY, Color color) {
        setColor(color);
        setPos(posX,posY);
    }
}
