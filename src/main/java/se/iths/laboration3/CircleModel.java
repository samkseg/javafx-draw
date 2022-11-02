package se.iths.laboration3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleModel extends ShapeModel {
    private double radius;
    public CircleModel(double x, double y,  Color color, double radius) {
        super(x,y,color);
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillOval(super.getX()-radius, super.getY()-radius, radius * 2, radius * 2);
    }
}
