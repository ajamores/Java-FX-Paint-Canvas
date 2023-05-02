package Assignment08_000315902;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Circle object class
 * @author Armand Amores
 */
public class Circle extends GeometricObject {
    /** radius for circle */
    private double radius;

    /** creates circle
     * @param x
     * @param y
     * @param fillColor
     * @param radius
     */
    public Circle(double x, double y, Color fillColor, double radius) {
        super(x, y, fillColor);
        this.radius = radius;
    }


    /**
     * @param gc draw circle
     */
    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(this.getFillColor());
        gc.fillOval(this.getX(), this.getY(), this.radius, this.radius);
    }
}
