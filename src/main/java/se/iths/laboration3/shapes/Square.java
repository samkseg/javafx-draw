package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Square extends Shape {
    private double height;
    private double width;
    public Square(double x, double y, double size, Color color) {
        super(x,y);
        super.setColor(color);
        super.setBorderColor(Color.TRANSPARENT);
        this.width = size;
        this.height = size;
    }
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(getBorderColor());
        context.fillRect(getX()-(width/2)-2,getY()-(height/2)-2,(width)+4,(height)+4);
        context.setFill(getColor());
        context.fillRect(getX()-(width/2),getY()-(height/2),width,height);
    }
    @Override
    public boolean onClick(MouseEvent mouseEvent){
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double xArea = getX() + (width/2);
        double yArea = getY() + (height/2);

        return x >= getX() - (width/2) &&
                x <= xArea &&
                y >= getY() - (height/2) &&
                y <= yArea;
    }
    @Override
    public ShapeType getShapeType() {
        return ShapeType.SQUARE;
    }
    @Override
    public double getXSize() {
        return width;
    }
    @Override
    public double getYSize() {
        return height;
    }
    public void reSizeX(double width) {
        this.width = width;
        this.height = width;
    }
    public void reSizeY(double height) {}
    @Override
    public String toStringSVG() {
        String color= "#"+getColor().toString().substring(2,10);
        return "<rect x=\"" + getX() + "\" y=\"" + getY() + "\" width=\"" + width + "\" height=\"" + height + "\" fill=\"" + color + "\" />";
    }
}
