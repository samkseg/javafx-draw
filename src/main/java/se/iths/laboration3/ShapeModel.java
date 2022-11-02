package se.iths.laboration3;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class ShapeModel {
    private final double x;
    private final double y;
    protected ShapeModel shape;
    protected Color color;

    public ShapeModel(double x, double y) {
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

    public static ShapeModel createShape(ShapeType type, double x, double y, double width, double height, Color color) {

        return switch (type) {
            case CIRCLE -> new CircleModel(x, y, color, width);
            case RECTANGLE -> new RectangleModel(x, y, width, height, color);
            case TRIANGLE -> new TriangleModel(x, y, width, color);
        };
    }

    public abstract void draw(GraphicsContext context);
}
