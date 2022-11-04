package se.iths.laboration3.shapes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class Shape {
    private final double x;
    private final double y;
    protected Shape shape;
    protected Color color;

    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

    public abstract void draw(GraphicsContext context);
}
