package se.iths.laboration3.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.iths.laboration3.shapes.*;

public class Model {
    public ObjectProperty<ShapeType> currentShapeType = new SimpleObjectProperty<>(ShapeType.CIRCLE);
    private ObservableList<ObsShape> shapes = FXCollections.observableArrayList(param -> new Observable[]{
            param.colorProperty(),
            param.borderColorProperty()
    });
    private ObservableList<ObsShape> selectedShapes = FXCollections.observableArrayList();
    public ObservableList<? extends Shape> getShapes() {
        return shapes;
    }
    public ObservableList<? extends Shape> getSelectedShapes() {
        return selectedShapes;
    }
    public void addSelectedList(Shape shape) {
        removeFromSelectedList();
        var oShape = new ObsShape(shape);
        selectedShapes.add(oShape);
        oShape.select();
    }
    public void removeFromSelectedList() {
        for (Shape s : getSelectedShapes())
            s.deSelect();
        selectedShapes.clear();
    }
    public void addShape(Shape shape) {
        var oShape = new ObsShape(shape);
        shapes.add(oShape);
    }
    public void remove(Shape shape) {
        var oShape = new ObsShape(shape);
        if (shapes.size() > 0) {
            shapes.remove(oShape);
        }
    }
    public void removeAll() {
        shapes.clear();
        selectedShapes.clear();
    }
}


