package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Triangle extends Shape {
    double size;
    public Triangle(double x, double y, double size, Color color) {
        super(x,y);
        super.setColor(color);
        super.setBorderColor(Color.TRANSPARENT);
        this.size = size;
    }
    private Polygon calculatePolygon(){
        int firstX = (int) (getX());
        int secondX = (int) (getX() + (size/2));
        int thirdX = (int) (getX() - (size/2));

        int firstY = (int) (getY() - (size/2));
        int secondY = (int) (getY() + (size)  - (size/2));
        int thirdY = (int) (getY() +(size) - (size/2));
        return new Polygon(firstX,firstY,secondX,secondY,thirdX,thirdY);
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
    public boolean onClick(final MouseEvent mouseEvent){
        if (calculatePolygon().contains(mouseEvent.getX(),mouseEvent.getY()))
                return true;
        else return false;
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
