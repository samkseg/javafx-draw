package se.iths.laboration3.controller;


import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import se.iths.laboration3.shapes.ObsShape;
import se.iths.laboration3.shapes.Shape;
import se.iths.laboration3.shapes.ShapeType;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void createNewShapeAtPosition1212() {

        CanvasViewController controller = new CanvasViewController();

        Shape shape = Shape.createShape(ShapeType.CIRCLE, 12, 12, 100, 100, Color.BLUE);
        controller.model.addShape(shape);
        var x = controller.model.getShapes().get(0).getX();
        var y = controller.model.getShapes().get(0).getY();
        assertEquals(12, x);
        assertEquals(12, y);
    }

    @Test
    void addShapeToSelectedShapesList() {

        CanvasViewController controller = new CanvasViewController();

        Shape shape = Shape.createShape(ShapeType.CIRCLE, 12, 12, 100, 100, Color.BLUE);
        controller.model.addShape(shape);
        controller.model.addSelectedList(controller.model.getShapes().get(0));
        var x = controller.model.getSelectedShapes().get(0).getX();
        var y = controller.model.getSelectedShapes().get(0).getY();
        assertEquals(12, x);
        assertEquals(12, y);
    }

    @Test
    void addNewShapeAsObsShape() {
        CanvasViewController controller = new CanvasViewController();

        Shape shape = Shape.createShape(ShapeType.CIRCLE, 12, 12, 100, 100, Color.BLUE);
        controller.model.addShape(shape);
        controller.model.addSelectedList(controller.model.getShapes().get(0));
        assertTrue(controller.model.getSelectedShapes().get(0) instanceof ObsShape);
    }
}