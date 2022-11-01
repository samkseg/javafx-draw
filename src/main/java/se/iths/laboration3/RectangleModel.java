package se.iths.laboration3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class RectangleModel extends ShapeModel {
    public RectangleModel(double x, double y, Color color) {
        super(x,y);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.web("004B87"));
        context.fillRect(super.getX(),super.getY(),50,50);
    }
}
