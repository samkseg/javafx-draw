package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Circle extends Shape {
    private double radius;
    public Circle(double x, double y, Color color, double radius) {
        super(x,y);
        super.setColor(color);
        super.setBorderColor(Color.TRANSPARENT);
        this.radius = radius;

    }

    private double getRadius() {
        return radius;
    }
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(getBorderColor());
        context.fillOval(getX()-radius-2, getY()-radius-2, radius * 2+4, radius * 2+4);;
        context.setFill(getColor());
        context.fillOval(getX()-radius, getY()-radius, radius * 2, radius * 2);
    }

    @Override
    public boolean onClick(MouseEvent mouseEvent){
        double xArea = mouseEvent.getX() - getX();
        double yArea = mouseEvent.getY() - getY();
        double distance = Math.sqrt((xArea*xArea) + (yArea *yArea));
        return distance <= getRadius();
    }

    public void reSizeX(double radius) {
        this.radius = radius;
    }
    public void reSizeY(double y) {
    }
}
