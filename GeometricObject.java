package Assignment08_000315902;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Contains basic attributes of a geometric object
 */
public abstract class GeometricObject implements Drawable {
    /** set x */
    private double x;
    /** set y */
    private double y;
    /** set color */
    private Color fillColor;

    /** Set geometric object constructor
     * @param x
     * @param y
     * @param fillColor
     */
    public GeometricObject(double x, double y, Color fillColor){
        try {
            this.x = x;
            this.y = y;
            this.fillColor = fillColor;
        } catch (Exception e){
            System.out.println("Geometric Object Error: " + e.getMessage());
        }
    }

    /**
     * @return draw method for objects
     */
    @Override
    public GraphicsContext draw() {
        return null;
    }

    /**
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * @return color
     */
    public Color getFillColor() {
        return fillColor;
    }

    public abstract void draw(GraphicsContext gc);
}
