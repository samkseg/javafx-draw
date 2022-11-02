package se.iths.laboration3;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Model {
    ObjectProperty<ShapeType> currentShapeType = new SimpleObjectProperty<>(ShapeType.CIRCLE);
    ObservableList<ObsShape> shapes = FXCollections.observableArrayList(param -> new Observable[]{param.colorProperty()});


    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType.set(currentShapeType);
    }
    public ObservableList<? extends ShapeModel> getShapes() {
        return shapes;
    }

    public ShapeModel addShape(ShapeModel shape) {
        var oShape = new ObsShape(shape);
        shapes.add(oShape);
        return shape;
    }

}
class ObsShape extends ShapeModel {
    ShapeModel shape;
    ObjectProperty<Color> color = new SimpleObjectProperty<>();

    public ObsShape(ShapeModel shape){
        super(shape.getX(),shape.getY());
        this.shape = shape;
        color.set(shape.getColor());
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    @Override
    public Color getColor() {
        return color.get();
    }

    @Override
    public void setColor(Color color) {
        shape.setColor(color);
        this.color.set(color);
    }

    @Override
    public void draw(GraphicsContext context) {
        this.shape.draw(context);
    }
}


