package Model.Square;

import java.awt.*;

public class End_slide extends Square{
    /**
     * Constructs a square, where a slide ends
     * @param posX is the X position of the square
     * @param posY is the Y position of the square
     * @param color is the color of the square
     * @post Constructs the square, where a slide ends and declares its position(X and Y) and color
     */
    public End_slide(int posX,int posY, Color color){
        setColor(color);
        setPos(posX,posY);
    }
}
