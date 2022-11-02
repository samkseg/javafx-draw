package se.iths.laboration3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TriangleModel extends ShapeModel {
    public TriangleModel(double x, double y, double size, Color color) {
        super(x,y);
        super.setColor(color);
    }

    @Override
    public void draw(GraphicsContext context) {
        int firstX = 90;
        int secondX = 190;
        int thirdX = 10;

        int firstY = 30;
        int secondY = 170;
        int thirdY = 170;

        context.setFill(color);
        context.fillPolygon(new double[]{firstX,secondX,thirdX},new double[]{firstY, secondY, thirdY},3);
    }
}
