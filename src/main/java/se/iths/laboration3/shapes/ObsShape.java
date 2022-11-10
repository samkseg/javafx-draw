package se.iths.laboration3.shapes;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ObsShape extends Shape {
    Shape shape;
    ObjectProperty<Color> color = new SimpleObjectProperty<>();
    ObjectProperty<Color> borderColor = new SimpleObjectProperty<>();
    DoubleProperty xSize = new SimpleDoubleProperty();
    DoubleProperty ySize = new SimpleDoubleProperty();
    public ObsShape(Shape shape) {
        super(shape.getX(), shape.getY());
        this.shape = shape;
        xSize.set(shape.getXSize());
        ySize.set(shape.getYSize());
        color.set(shape.getColor());
        borderColor.set(shape.getBorderColor());
    }
    public ObjectProperty<Color> colorProperty() {
        return color;
    }
    public ObjectProperty<Color> borderColorProperty() {
        return borderColor;
    }
    public DoubleProperty xSizeProperty() {
        return xSize;
    }
    public DoubleProperty ySizeProperty() {
        return ySize;
    }
    @Override
    public boolean onClick(MouseEvent mouseEvent) {
        return shape.onClick(mouseEvent);
    }
    @Override
    public Shape getShape() {
        return this.shape;
    }
    @Override
    public ShapeType getShapeType() {
        Shape shape = this.shape.getShape();
        if (shape instanceof Circle) return ShapeType.CIRCLE;
        if (shape instanceof Rectangle) return ShapeType.RECTANGLE;
        if (shape instanceof Triangle) return ShapeType.TRIANGLE;
        return ShapeType.RECTANGLE;
    }
    @Override
    public Color getColor() {
        return color.get();
    }
    @Override
    public Color getBorderColor() {
        return borderColor.get();
    }
    @Override
    public void setColor(Color color) {
        shape.setColor(color);
        this.color.set(color);
    }
    @Override
    public void setBorderColor(Color borderColor) {
        shape.setBorderColor(borderColor);
        this.borderColor.set(borderColor);
    }
    @Override
    public void draw(GraphicsContext context) {
        this.shape.draw(context);
    }
    @Override
    public double getXSize() {
        return this.shape.getXSize();
    }
    @Override
    public double getYSize() {
        return this.shape.getYSize();
    }
    @Override
    public void reSizeX(double x) {
        this.shape.reSizeX(x);
        xSize.set(shape.getXSize());
    }
    @Override
    public void reSizeY(double y) {
        this.shape.reSizeY(y);
        ySize.set(shape.getYSize());
    }
    @Override
    public String toStringSVG() {
        return shape.toStringSVG();
    }
}
