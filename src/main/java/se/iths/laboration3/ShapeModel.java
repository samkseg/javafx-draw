package se.iths.laboration3;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class ShapeModel {
    private final double x;
    private final double y;

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

    public static ShapeModel createShape(ShapeType type, double x, double y, Color color) {

        return switch (type) {
            case CIRCLE -> new CircleModel(x, y, color);
            case RECTANGLE -> new RectangleModel(x, y, color);
            case TRIANGLE -> new TriangleModel(x, y, color);
        };
    }

    public abstract void draw(GraphicsContext context);
}
