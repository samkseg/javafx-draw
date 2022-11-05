package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Triangle extends Shape {
    double size;
    double hypotenuse;
    public Triangle(double x, double y, double size, Color color) {
        super(x,y);
        super.setColor(color);
        super.setBorderColor(Color.TRANSPARENT);
        this.size = size;
        hypotenuse = Math.sqrt(Math.pow(size, 2) + Math.pow((size/2), 2));
    }

    @Override
    public void draw(GraphicsContext context) {

        int firstX = (int) (getX());
        int secondX = (int) (getX() + (size/2));
        int thirdX = (int) (getX() - (size/2));

        int firstY = (int) (getY() - (size/2));
        int secondY = (int) (getY() + (size)  - (size/2));
        int thirdY = (int) (getY() +(size) - (size/2));

        context.setFill(getBorderColor());
        context.fillPolygon(new double[]{firstX,secondX+2,thirdX-2},new double[]{firstY-2, secondY+2, thirdY+2},3);
        context.setFill(getColor());
        context.fillPolygon(new double[]{firstX,secondX,thirdX},new double[]{firstY, secondY, thirdY},3);

    }
    @Override
    public boolean onClick(MouseEvent mouseEvent){
        return false;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.TRIANGLE;
    }
    public double getXSize(){return size;}
    public double getYSize(){return size;}
    public void reSizeX (double size) {
        this.size = size;
    }
    public void reSizeY(double y) {
    }
}
