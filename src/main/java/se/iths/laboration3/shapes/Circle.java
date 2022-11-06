package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Circle extends Shape {
    private double diameter;
    public Circle(double x, double y, Color color, double diameter) {
        super(x,y);
        super.setColor(color);
        super.setBorderColor(Color.TRANSPARENT);
        this.diameter = diameter;

    }
    private double getRadius() {
        return diameter/2;
    }
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(getBorderColor());
        context.fillOval(getX()-getRadius()-2, getY()-getRadius()-2, getRadius() * 2+4, getRadius() * 2+4);;
        context.setFill(getColor());
        context.fillOval(getX()-getRadius(), getY()-getRadius(), getRadius() * 2, getRadius() * 2);
    }
    @Override
    public boolean onClick(MouseEvent mouseEvent){
        double xArea = mouseEvent.getX() - getX();
        double yArea = mouseEvent.getY() - getY();
        double distance = Math.sqrt((xArea*xArea) + (yArea *yArea));
        return distance <= getRadius();
    }
    @Override
    public ShapeType getShapeType() {
        return ShapeType.CIRCLE;
    }
    public double getXSize(){return diameter;}
    public double getYSize(){return diameter;}
    public void reSizeX(double diameter) {
        this.diameter = diameter;
    }
    public void reSizeY(double y) {
    }
    @Override
    public String toStringSVG() {
        String color = "#" + getColor().toString().substring(2, 10);
        return "<circle cx=\"" + getX() + "\" cy=\"" + getY() + "\" r=\"" + getRadius() / 2 + "\" fill=\"" + color + "\" />";
    }
}
