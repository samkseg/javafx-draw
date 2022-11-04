package se.iths.laboration3.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import se.iths.laboration3.Model;
import se.iths.laboration3.Shape;
import se.iths.laboration3.ShapeType;

import java.util.ArrayDeque;
import java.util.Deque;

public class CanvasViewController {
    @FXML
    public Canvas canvas;
    public GraphicsContext context;
    public ChoiceBox<ShapeType> choiceBox;
    public ColorPicker colorPicker;
    public Slider widthSlider;
    public Slider heightSlider;
    public Label widthLabel;
    public Label heightLabel;
    public Button clearButton;
    public Button undoButton;
    public Button redoButton;

    Model model = new Model();
    ObservableList<ShapeType> shapeTypesList = FXCollections.observableArrayList(ShapeType.values());
    static Deque<Command> undoStack = new ArrayDeque<>();
    static Deque<Shape> undoShapeStack = new ArrayDeque<>();
    static Deque<Command> redoStack = new ArrayDeque<>();
    static Deque<Shape> redoShapeStack = new ArrayDeque<>();

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        choiceBox.setItems(shapeTypesList);
        choiceBox.setValue(ShapeType.RECTANGLE);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(this::choiceBoxChanged);
        colorPicker.setValue(Color.WHITE);
        widthSlider.setMin(1);
        heightSlider.setMin(1);
        model.getShapes().addListener(this::listChanged);

    }

    private void listChanged(Observable observable) {
        var context = canvas.getGraphicsContext2D();
        context.clearRect(0,0, canvas.getWidth(),canvas.getHeight());
        for (Shape s : model.getShapes()) {
            s.draw(context);
        }
    }

    private void choiceBoxChanged(Observable observable) {

        ShapeType shapeChoice = choiceBox.getSelectionModel().getSelectedItem();
        switch (shapeChoice) {
            case RECTANGLE -> arrangeRectSliders();
            case CIRCLE -> arrangeCircleSliders();
            case TRIANGLE -> arrangeTriangleSliders();
        }
    }

    private void arrangeTriangleSliders() {
        widthLabel.setText("Side");
        heightLabel.setText("");
        widthSlider.setMax(100);
        heightSlider.setMax(100);
        widthSlider.setValue(0);
        heightSlider.setValue(0);
        heightLabel.setDisable(true);
        heightSlider.setDisable(true);
    }

    private void arrangeCircleSliders() {
        widthLabel.setText("Radius");
        heightLabel.setText("");
        widthSlider.setMax(50);
        heightSlider.setMax(50);
        widthSlider.setValue(0);
        heightSlider.setValue(0);
        heightLabel.setDisable(true);
        heightSlider.setDisable(true);
    }

    private void arrangeRectSliders() {
        widthLabel.setText("Width");
        heightLabel.setText("Height");
        widthSlider.setMax(100);
        heightSlider.setMax(100);
        widthSlider.setValue(0);
        heightSlider.setValue(0);
        heightLabel.setDisable(false);
        heightSlider.setDisable(false);
    }

    @FXML
    protected void canvasClicked(MouseEvent mouseEvent) {
        Shape shape = Shape.createShape(choiceBox.getValue(), mouseEvent.getX(), mouseEvent.getY(), widthSlider.getValue(), heightSlider.getValue(), colorPicker.getValue());
        model.addShape(shape);
        Command undo = () -> model.remove(shape);
        undoStack.push(undo);
        undoShapeStack.push(shape);
    }

    @FXML
    protected void undoStack(){
        if (undoStack.size() > 0) {
            Shape shape = undoShapeStack.pop();
            Command redo = () -> model.addShape(shape);
            redoStack.push(redo);
            redoShapeStack.push(shape);
            Command undoToExecute = undoStack.pop();
            undoToExecute.execute();
        }

    }
    @FXML
    protected void redoStack(){
        if (redoStack.size() > 0 && redoShapeStack.size() > 0){
            Shape shape = redoShapeStack.pop();
            model.addShape(shape);
            Command undo = () -> model.remove(shape);
            undoStack.push(undo);
            undoShapeStack.push(shape);
        }
    }
    @FXML
    protected void onClearButtonClick() {
        context.clearRect(0,0, canvas.getWidth(),canvas.getHeight());
        redoShapeStack.clear();
        undoShapeStack.clear();
        undoStack.clear();
        redoStack.clear();
        model.removeAll();
    }
    @FXML
    protected void onUndoButtonClick(){
        undoStack();
    }
    @FXML
    protected void onRedoButtonClick(){
        redoStack();
    }

}
    @FunctionalInterface
interface Command {
    public void execute();
}