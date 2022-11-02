package se.iths.laboration3;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    public abstract void draw(GraphicsContext context);
}
