package se.iths.laboration3.controller;


import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import se.iths.laboration3.model.Model;
import se.iths.laboration3.shapes.ObsShape;
import se.iths.laboration3.shapes.Shape;
import se.iths.laboration3.shapes.ShapeType;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void createNewShapeAtPosition1212() {
        Model model = new Model();
        Shape shape = Shape.createShape(ShapeType.CIRCLE, 12, 12, 100, 100, Color.BLUE);
        model.addShape(shape);
        var x = model.getShapes().get(0).getX();
        var y = model.getShapes().get(0).getY();
        assertEquals(12, x);
        assertEquals(12, y);
    }

    @Test
    void addShapeToSelectedShapesList() {
        Model model = new Model();
        Shape shape = Shape.createShape(ShapeType.CIRCLE, 12, 12, 100, 100, Color.BLUE);
        model.addShape(shape);
        model.addSelectedList(model.getShapes().get(0));
        var x = model.getSelectedShapes().get(0).getX();
        var y = model.getSelectedShapes().get(0).getY();
        assertEquals(12, x);
        assertEquals(12, y);
    }

    @Test
    void addNewShapeAsObsShape() {
        Model model = new Model();
        Shape shape = Shape.createShape(ShapeType.CIRCLE, 12, 12, 100, 100, Color.BLUE);
        model.addShape(shape);
        model.addSelectedList(model.getShapes().get(0));
        assertTrue(model.getSelectedShapes().get(0) instanceof ObsShape);
    }
}