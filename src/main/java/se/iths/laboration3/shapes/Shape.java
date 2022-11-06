package se.iths.laboration3.shapes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class Shape {
    private double x;
    private double y;
    protected Shape shape;
    private Color color;
    private Color borderColor;
    public boolean isSelected = false;

    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void select() {
        setBorderColor(Color.MAGENTA);
        if (!isSelected)
            isSelected = true;
    }

    public void deSelect(){
        setBorderColor(Color.TRANSPARENT);
        if (isSelected)
            isSelected = false;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    public Color getBorderColor() {
        return borderColor;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Shape getShape(){return this.shape;}
    public void setShapeType(Shape shape){
        this.shape = shape;
    }



    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static Shape createShape(ShapeType type, double x, double y, double width, double height, Color color) {

        return switch (type) {
            case CIRCLE -> new Circle(x, y, color, width);
            case RECTANGLE -> new Rectangle(x, y, width, height, color);
            case TRIANGLE -> new Triangle(x, y, width, color);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape shape1)) return false;
        return Double.compare(shape1.x, x) == 0 && Double.compare(shape1.y, y) == 0 && Objects.equals(shape, shape1.shape) && Objects.equals(color, shape1.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, shape, color);
    }
    public abstract boolean onClick(MouseEvent mouseEvent);
    public abstract void draw(GraphicsContext context);
    public abstract ShapeType getShapeType();
    public abstract double getXSize();
    public abstract double getYSize();
    public abstract void reSizeX(double x);
    public abstract void reSizeY(double y);

}
