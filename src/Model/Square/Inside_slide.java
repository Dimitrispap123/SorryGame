package Model.Square;

import java.awt.*;

public class Inside_slide extends Square{
    /**
     * Constructs a square that is inside a slide
     * @param posX is the X position of the square
     * @param posY is the Y position of the square
     * @param color is the color of the square
     * @post Constructs the square that is inside a slide and declares its position(X and Y) and color
     */
    public Inside_slide(int posX,int posY, Color color) {
        setColor(color);
        setPos(posX,posY);
    }
}
