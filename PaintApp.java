package Assignment08_000315902;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Graphics for Paint Application. Contains GUI Components that takes user inputs
 * to help draw shapes on a Drawing Canvas.
 * @author Armand Amores
 */
public class PaintApp extends Application {
    /** Contains and holds each shape created*/
    private ArrayList<GeometricObject> shapes = new ArrayList<>();
    /** label for shape selector */
    private Label shapeTitle;
    /** Shape selector for circle */
    private Button circleButton;
    /** Shape selector for square*/
    private Button squareButton;
    /** Label for location*/
    private Label location;
    /** Takes x co-ord input */
    private TextField xInput;
    /** Takes y co-ord input*/
    private TextField yInput;
    /** label  size of shape */
    private Label size;
    /** Takes size input*/
    private TextField sizeInput;
    /** label for color*/
    private Label color;
    /** rgb value 1 */
    private TextField color1Input;
    /** rbg value 2 */
    private TextField color2Input;
    /** rgb value 3 */
    private TextField color3Input;
    /** Label for action buttons */
    private Label action;
    /** When pressed draws shape */
    private Button draw;
    /** When pressed removes last shape*/
    private Button undraw;
    /** Where shapes are drawn */
    /** Erase 10 items at once*/
    private Button biggerUndraw;
    private Canvas drawCanvas;
    /** Menu button user interface*/
    private Canvas userInterface;
    /** circle object */
    private Circle circle;
    /** square object */
    private Square square;
    /** Graphics drawer */
    private GraphicsContext gc;
    /** Layout */
    private Pane root;
    /** label for error status */
    private Label status;



    /** Launch arguments
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** Adds children to stage, draws on drawCanvas
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        /** Set title */
        primaryStage.setTitle("Java FX Paint Canvas");
        /** Set layout */
        root = new Pane();
        root.setStyle("fx-background-color: #f8f8f8");
        root.setPrefSize(1200, 700); // set a specific width and height for the root pane

        //Add layout to scene, specify dimensions
        Scene scene = new Scene(root, 1200, 700);

        //set Scene for stage
        primaryStage.setScene(scene);

        //1. Create GUI components
        userInterface = new Canvas(1200, 100);
        drawCanvas = new Canvas(900, 500);
        circleButton = new Button("Circle");
        squareButton = new Button("Square");
        xInput = new TextField();
        yInput = new TextField();
        sizeInput = new TextField();
        color1Input = new TextField();
        color2Input = new TextField();
        color3Input = new TextField();
        draw = new Button("Draw");
        undraw = new Button("Undraw");
        biggerUndraw = new Button("Big Erase");
        status = new Label("Status: Clear");
        location = new Label("Location (X, Y)");
        shapeTitle = new Label("Shapes");
        size = new Label("Size");
        color = new Label("Color");
        action = new Label("Action");

        //2. Add GUI elements to layout
        root.getChildren().addAll(userInterface, drawCanvas, circleButton, squareButton, xInput, yInput,
                sizeInput, color1Input, color2Input, color3Input, draw, undraw, status, location, shapeTitle,
                size, color, action, biggerUndraw);

        //3. Configure Components
        gc = drawCanvas.getGraphicsContext2D();


        //position user interface
        userInterface.setLayoutY(root.getHeight() - userInterface.getHeight() - 600);

        //position canvas
        drawCanvas.setLayoutX((root.getWidth() - drawCanvas.getWidth()) / 2);
        //subtracting to position y slightly to the bottom
        drawCanvas.setLayoutY(root.getHeight() - drawCanvas.getHeight() - 40);

        //position GUI components
        //status bar
        status.setLayoutY(100);
        //shape buttons
        shapeTitle.relocate(50,60);
        circleButton.relocate(20,20);
        squareButton.relocate(70, 20);
        //location buttons
        xInput.relocate(200, 20);
        yInput.relocate(260, 20);
        location.relocate(220, 60);
        //size buttons
        size.relocate(365, 60);
        sizeInput.relocate(350, 20);
        //color buttons
        color.relocate(530, 60);
        color1Input.relocate(450, 20);
        color2Input.relocate(510, 20);
        color3Input.relocate(570, 20);
        //action buttons
        action.relocate(760, 60);
        draw.relocate(700, 20);
        undraw.relocate(750, 20);
        biggerUndraw.relocate(813,20);


        //styles

        //adds shadow to canvas
        DropShadow shadow = new DropShadow(10, Color.GRAY);
        drawCanvas.setEffect(shadow);

        //gui styling, setting width and height, color
        xInput.setPrefWidth(50);
        yInput.setPrefWidth(50);
        sizeInput.setPrefWidth(50);
        color1Input.setPrefWidth(50);
        color2Input.setPrefWidth(50);
        color3Input.setPrefWidth(50);
        status.setPrefWidth(1200);
        status.setPrefHeight(25);
        status.setStyle("-fx-background-color: red; -fx-height: 100px; -fx-width: 100px;");


        //sets canvas
        GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 900, 700);
        //sets user interface
        GraphicsContext ui = userInterface.getGraphicsContext2D();
        ui.setFill(Color.valueOf("#f9f9f9"));
        ui.fillRect(0, 0, 1200, 100);

        // 4. Add event handlers
        circleButton.setOnAction(this::circleButtonHandler);
        squareButton.setOnAction(this::squareButtonHandler);
        draw.setOnAction(this::drawButtonHandler);
        undraw.setOnAction(this::undrawButtonHandler);
        biggerUndraw.setOnAction(this::biggerUndrawButton);
        //mouse events
        drawCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressHandler);
        drawCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::releaseHandler);
        drawCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::drawHandler);
        drawCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::drawHandler);


        primaryStage.show();
    }




    /** checks if circle button selected */
    boolean isCircleButtonSelected = false;
    /** checks if square button selected */
    boolean isSquareButtonSelected = false;

    /** checks to see if circle is pressed and sets square button to false
     * @param a
     */
    private void circleButtonHandler(ActionEvent a) {
        System.out.println("Circle button pressed.");
        isCircleButtonSelected = true;
        //styles
        circleButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        squareButton.setStyle(null);
        isSquareButtonSelected = false;
    }

    /** checks to see if square is pressed and sets circle button to false
     * @param a
     */
    private void squareButtonHandler(ActionEvent a) {
        System.out.println("Square button pressed");
        isSquareButtonSelected = true;
        squareButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        circleButton.setStyle(null);
        isCircleButtonSelected = false;
    }
    /** initialize x value */
    private double x ;
    /** initialize y value*/
    private double y;
    /** initialize size value */
    private double sizeDouble = 10;
    /** initialize color values*/
    private int rgb1 = 0, rgb2 = 0, rgb3 = 0;
    /** check if mouse press */
    private boolean mousePress = false;
    /** initialize mouse event */
    private MouseEvent me;


    /**
     * @param me listen for mouse clicks, get co-ordinates
     */
    private void pressHandler(MouseEvent me) {
        mousePress = true;
        System.out.println("Pressed " + me.getButton() + " at (" + me.getX() + "," + me.getY() + ").");
    }

    /**
     * @param me listen for when mouse is released, gets co-ordinates
     */
    private void releaseHandler(MouseEvent me) {
        mousePress = true;
        System.out.println("Released " + me.getButton() + " at (" + me.getX() + "," + me.getY() + ").");
    }

    /**
     * @param me sets up x and y using either user inputs or mouse events
     */
    private void getCoordinates(MouseEvent me) {
        //error handle x and y inputs
        if(!mousePress){
            try{
                x = Double.parseDouble(xInput.getText());
                y = Double.parseDouble(yInput.getText());
            } catch (NumberFormatException e){
                status.setText("Status: Please enter a valid integer in the Location field!");
                System.out.println("Error: " + e.getMessage());
                throw e; //IMPORTANT... passes along exception
            }
            //ensures correct range and throws new exception if range violation
            if ( x < 0 || y < 0 ) {
                status.setText("Status: Error in Location input. Location inputs must be 0 or greater.");
                throw new IllegalArgumentException("Location must be 0 or greater.");
            } else if (x > 890 || y > 490 ) {
                status.setText("Status: Error in Location input. X(0-890) Y (0-490  )");
                throw new IllegalArgumentException("Too high.");
            }
            //uses mouse event x and y...subtract by 20 so that drawing appears more to the top left of mouse
        } else if (mousePress) {
            x = me.getX() - 20;
            y = me.getY() - 20;
        }



    }

    /** Handles action for when draw button is pressed
     * @param a
     */
    private void drawButtonHandler(ActionEvent a) {
        //check if no buttons are selected
        if (!isCircleButtonSelected && !isSquareButtonSelected){
            status.setText("Status: Select a Shape First!");
        }
        mousePress = false; // set to false so that x and y input used instead of mouse event x and y
        //get x and y coordinates
        getCoordinates(null);
        System.out.println("x-coordinate: " + x);
        System.out.println("y-coordinate: " + y);
        //get the position of the drawCanvas within the scene and set coordinates
        //local to the canvas as opposed to the root
        Bounds drawCanvasBounds = drawCanvas.localToScene(drawCanvas.getBoundsInLocal());


        //get size input
        getSize();
        //get rgb color inputs
        getRgb();
        //draw the shape
        if (isCircleButtonSelected) {
            createCircle();
        }
        if(isSquareButtonSelected) {
            createSquare();
        }
    }

    /** Handles actions for when undraw button pressed
     * @param a
     */
    private void undrawButtonHandler(ActionEvent a) {
        if (shapes.size() > 0) {
            //remove last shape from array
            shapes.remove(shapes.size() - 1);
            //clear canvas
            drawCanvas.getGraphicsContext2D().clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
            //redraw canvas and the every circle remaining in array list
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, 900, 700);
            //redraw each circle in list
            for (GeometricObject shape : shapes) {
                if (shape instanceof Circle){
                    //cast shape object to circle object to properly use circle class draw method
                    ((Circle)shape).draw(drawCanvas.getGraphicsContext2D());
                } else if (shape instanceof Square) {
                    //cast shape to square
                    ((Square)shape).draw(drawCanvas.getGraphicsContext2D());
                }
            }

            System.out.println("Shape Array Size: " + shapes.size());
        } else {
            status.setText("Status: Nothing to erase!");
        }
    }

    /**
     * @param a Deletes 10 items off list instead of 1
     */
    private void biggerUndrawButton(ActionEvent a) {
        System.out.println("Pressed");
        if (shapes.size() >= 10 ) {
            // remove last 10 shapes from array
            for (int i = 0; i < 10 && shapes.size() > 0; i++) {
                shapes.remove(shapes.size() - 1);
            }
            // clear canvas
            drawCanvas.getGraphicsContext2D().clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
            // redraw canvas and every circle remaining in array list
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, 900, 700);
            // redraw each circle in list
            for (GeometricObject shape : shapes) {
                if (shape instanceof Circle){
                    // cast shape object to circle object to properly use circle class draw method
                    ((Circle)shape).draw(drawCanvas.getGraphicsContext2D());
                } else if (shape instanceof Square) {
                    // cast shape to square
                    ((Square)shape).draw(drawCanvas.getGraphicsContext2D());
                }
            }

            System.out.println("Shape Array Size: " + shapes.size());
        } else {
            status.setText("Status: Not enough items to erase!");
        }
    }

    /**
     * @param me Action for  mouse press and drags
     */
    private void drawHandler(MouseEvent me) {
        if (isCircleButtonSelected) {
            getCoordinates(me);
            getSize();
            getRgb();
            createCircle();
        }
        if(isSquareButtonSelected) {
            getCoordinates(me);
            getSize();
            getRgb();
            createSquare();
        }
    }

    /**
     * Creates circle object, adds to shapes array list
     */
    private void createCircle() {
        status.setText("Status: Clear");
        circle = new Circle(x, y, Color.rgb(rgb1,rgb2,rgb3), sizeDouble);
        //add to array list shapes
        shapes.add(circle);
        //draws last shape of array list
        shapes.get(shapes.size() - 1).draw(drawCanvas.getGraphicsContext2D());
        System.out.println("Shapes array list size: " + shapes.size()); //debug
        System.out.println(shapes.size()-1);

    }

    /**
     * creates square with gathered parameters
     */
    private void createSquare(){
        status.setText("Status: Clear");
        //add to array list shapes
        square = new Square(x,y,Color.rgb(rgb1,rgb2,rgb3),sizeDouble);
        //add to array list shapes
        shapes.add(square);
        //draws last shape in array list
        shapes.get(shapes.size() - 1).draw(drawCanvas.getGraphicsContext2D());
        System.out.println("Shapes array list size: " + shapes.size());
    }

    /**
     * takes size input
     */
    private void getSize(){
        try{
            sizeDouble = Double.parseDouble(sizeInput.getText());
        } catch (NumberFormatException e) {
            status.setText("Status: Error in size input. " + e.getMessage());
            System.out.println(e.getMessage());
            throw e; // IMPORTANT re-throw otherwise default parse number is 0
        }
        // ensure size is greater than 0... NOTE if size = 1, object created still too small to see
        if (sizeDouble <= 0) {
            status.setText("Status: Error in size input. Size must be larger than 0.");
            throw new IllegalArgumentException("Size must be larger than 0.");
        }
        System.out.println("size: " + sizeDouble);
    }

    /**
     * takes color input
     */
    private void getRgb() {
        //try to parse color values
        try {
            rgb1 = Integer.parseInt(color1Input.getText());
            rgb2 = Integer.parseInt(color2Input.getText());
            rgb3 = Integer.parseInt(color3Input.getText());
            //if not within range
            if (rgb1 < 0 || rgb1 > 255 || rgb2 < 0 || rgb2 > 255 || rgb3 < 0 || rgb3 > 255) {
                throw new IllegalArgumentException("RGB color values must be between 0 and 255.");
            }
        //if invalid ints, re-throw exception
        } catch (NumberFormatException e) {
            status.setText("Status: Please enter valid integer values for RGB colors (0-255).");
            System.out.println("Error: " + e.getMessage());
            throw e;
            //if non numbers enters re-throw e
        } catch (IllegalArgumentException e) {
            status.setText("Status: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }


}
