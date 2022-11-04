package se.iths.laboration3;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Model {
    ObjectProperty<ShapeType> currentShapeType = new SimpleObjectProperty<>(ShapeType.CIRCLE);
    ObservableList<ObsShape> shapes = FXCollections.observableArrayList(param -> new Observable[]{param.colorProperty()});


    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType.set(currentShapeType);
    }
    public ObservableList<? extends Shape> getShapes() {
        return shapes;
    }

    public Shape addShape(Shape shape) {
        var oShape = new ObsShape(shape);
        shapes.add(oShape);
        return shape;
    }

    public void remove(Shape shape){
        var oShape = new ObsShape(shape);
        if (shapes.size() > 0) {
            shapes.remove(shapes.size() - 1);
        }
    }
    public void removeAll(){
        shapes.clear();

    }

}
class ObsShape extends Shape {
    Shape shape;
    ObjectProperty<Color> color = new SimpleObjectProperty<>();

    public ObsShape(Shape shape){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObsShape obsShape)) return false;
        return Objects.equals(shape, obsShape.shape) && Objects.equals(color, obsShape.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, color);
    }
}


