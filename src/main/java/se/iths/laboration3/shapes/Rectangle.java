package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Rectangle extends Shape {
    private double height;
    private double width;
    public Rectangle(double x, double y, double width, double height, Color color) {

        super(x,y);
        super.setColor(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillRect(super.getX(),super.getY(),width,height);
    }
}
