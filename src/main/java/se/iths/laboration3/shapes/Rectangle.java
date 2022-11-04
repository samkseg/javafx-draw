package se.iths.laboration3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;


public class Rectangle extends Shape {
    private double height;
    private double width;
    public Rectangle(double x, double y, double width, double height, Color color) {

        super(x,y);
        super.setColor(color);
        super.setBorderColor(Color.TRANSPARENT);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(getBorderColor());
        context.fillRect(getX()-2,getY()-2,width+4,height+4);
        context.setFill(getColor());
        context.fillRect(getX(),getY(),width,height);
    }
    @Override
    public boolean onClick(MouseEvent mouseEvent){
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double xArea = getX() + height;
        double yArea = getY() + width;

        return x >= getX() &&
                x <= xArea &&
                y >= getY() &&
                y <= yArea;
    }
}
