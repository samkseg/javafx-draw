package se.iths.laboration3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Shape {
    double size;
    double hypotenuse;
    public Triangle(double x, double y, double size, Color color) {
        super(x,y);
        super.setColor(color);
        this.size = size;
        hypotenuse = Math.sqrt(Math.pow(size, 2) + Math.pow((size/2), 2));
    }

    @Override
    public void draw(GraphicsContext context) {

        int firstX = (int) (super.getX());
        int secondX = (int) (super.getX() + (size/2));
        int thirdX = (int) (super.getX() - (size/2));

        int firstY = (int) (super.getY());
        int secondY = (int) (super.getY() + (size));
        int thirdY = (int) (super.getY() +(size));

        context.setFill(color);
        context.fillPolygon(new double[]{firstX,secondX,thirdX},new double[]{firstY, secondY, thirdY},3);
    }
}
