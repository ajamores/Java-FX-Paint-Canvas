package Assignment08_000315902;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * class to make squares
 * @author Armand Amores
 */
public class Square extends GeometricObject {
    /** sizes square */
    private double size;

    /** creates square
     * @param x
     * @param y
     * @param fillColor
     * @param size
     */
    public Square(double x, double y, Color fillColor, double size){
        super(x, y, fillColor);
        this.size = size;
    }

    /**
     * @param gc draws square
     */
    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(this.getFillColor());
        gc.fillRect(this.getX(), this.getY(), this.size, this.size);
    }
}
