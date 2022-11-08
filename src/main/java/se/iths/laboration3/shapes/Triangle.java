package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Triangle extends Shape {
    double size;
    int firstX;
    int secondX;
    int thirdX;
    int firstY;
    int secondY;
    int thirdY;
    public Triangle(double x, double y, double size, Color color) {
        super(x,y);
        super.setColor(color);
        super.setBorderColor(Color.TRANSPARENT);
        this.size = size;
        updatePoints();
    }
    private Polygon calculatePolygon(){
        return new Polygon(firstX,firstY,secondX,secondY,thirdX,thirdY);
    }
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(getBorderColor());
        context.fillPolygon(new double[]{firstX,secondX+2,thirdX-2},new double[]{firstY-2, secondY+2, thirdY+2},3);
        context.setFill(getColor());
        context.fillPolygon(new double[]{firstX,secondX,thirdX},new double[]{firstY, secondY, thirdY},3);
    }
    @Override
    public boolean onClick(final MouseEvent mouseEvent){
        return calculatePolygon().contains(mouseEvent.getX(), mouseEvent.getY());
    }
    private void updatePoints() {
        firstX = (int) (getX());
        secondX = (int) (getX() + (size/2));
        thirdX = (int) (getX() - (size/2));

        firstY = (int) (getY() - (size/2));
        secondY = (int) (getY() + (size)  - (size/2));
        thirdY = (int) (getY() +(size) - (size/2));
    }
    @Override
    public ShapeType getShapeType() {
        return ShapeType.TRIANGLE;
    }
    public double getXSize(){return size;}
    public double getYSize(){return size;}
    public void reSizeX (double size) {
        this.size = size;
        updatePoints();
    }
    public void reSizeY(double y) {
    }
    public String toStringSVG() {
        String color= "#"+getColor().toString().substring(2,10);
        return "<polygon points=\"" + (firstX + (size/2)) + ", " + (firstY + (size/2)) + "  " + (secondX + (size/2)) + ", " + (secondY + (size/2)) + "  " + (thirdX + (size/2)) + ", " + (thirdY + (size/2)) + "\" fill=\"" + color + "\" />";
    }
}
