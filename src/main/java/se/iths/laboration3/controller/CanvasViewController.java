package se.iths.laboration3.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import se.iths.laboration3.model.Model;
import se.iths.laboration3.shapes.*;

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
    public TextField widthText;
    public TextField heightText;
    public Button createButton;
    public Button selectButton;
    public Button clearButton;
    public Button undoButton;
    public Button redoButton;

    Model model = new Model();

    private boolean select;
    ObservableList<ShapeType> shapeTypesList = FXCollections.observableArrayList(ShapeType.values());
    static Deque<Command> undoCommandStack = new ArrayDeque<>();
    static Deque<Shape> undoShapeStack = new ArrayDeque<>();
    static Deque<Command> redoCommandStack = new ArrayDeque<>();
    static Deque<Shape> redoShapeStack = new ArrayDeque<>();

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        choiceBox.setItems(shapeTypesList);
        choiceBox.setValue(ShapeType.RECTANGLE);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(this::choiceBoxChanged);
        colorPicker.setValue(Color.WHITE);
        widthSlider.setMin(1);
        heightSlider.setMin(1);
        widthSlider.setMax(500);
        heightSlider.setMax(500);
        widthSlider.setValue(50);
        heightSlider.setValue(50);
        widthText.setText(String.valueOf(widthSlider.getValue()));
        heightText.setText(String.valueOf(heightSlider.getValue()));
        colorPicker.valueProperty().addListener(this::colorPickerChange);
        widthSlider.valueProperty().addListener(this::widthSliderChange);
        heightSlider.valueProperty().addListener(this::heightSliderChange);
        model.getShapes().addListener(this::listChanged);
        model.getSelectedShapes().addListener(this::listChanged);

    }

    private void colorPickerChange(Observable observable) {
        if (model.getSelectedShapes().size() > 0) {
            Shape shape = model.getSelectedShapes().get(0);
            shape.setColor(colorPicker.getValue());
            drawShapes();
        }

    }

    private void widthSliderChange(Observable observable) {
        widthText.setText(String.valueOf((int) widthSlider.getValue()));
        if(model.getSelectedShapes().size() > 0) {
            var shape = model.getSelectedShapes().get(0).getShape().getShape();
            if (shape instanceof Rectangle)
                shape.reSizeX(widthSlider.getValue());
            if (shape instanceof Circle)
                shape.reSizeX(widthSlider.getValue());
            if (shape instanceof Triangle)
                shape.reSizeX(widthSlider.getValue());
            drawShapes();
        }
    }

    private void heightSliderChange(Observable observable) {
        heightText.setText(String.valueOf((int) heightSlider.getValue()));
        if(model.getSelectedShapes().size() > 0){
            var shape = model.getSelectedShapes().get(0).getShape().getShape();
            if (shape instanceof Rectangle)
                shape.reSizeY(heightSlider.getValue());
            drawShapes();
        }
    }

    private void listChanged(Observable observable) {
        drawShapes();
    }

    private void drawShapes() {
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
        widthSlider.setMax(500);
        heightSlider.setMax(500);
        widthSlider.setValue(50);
        heightSlider.setValue(50);
        heightLabel.setDisable(true);
        heightSlider.setDisable(true);
        heightText.setDisable(true);
    }

    private void arrangeCircleSliders() {
        widthLabel.setText("Radius");
        heightLabel.setText("");
        widthSlider.setMax(250);
        heightSlider.setMax(250);
        widthSlider.setValue(25);
        heightSlider.setValue(25);
        heightLabel.setDisable(true);
        heightSlider.setDisable(true);
        heightText.setDisable(true);
    }

    private void arrangeRectSliders() {
        widthLabel.setText("Width");
        heightLabel.setText("Height");
        widthSlider.setMax(500);
        heightSlider.setMax(500);
        widthSlider.setValue(50);
        heightSlider.setValue(50);
        heightLabel.setDisable(false);
        heightSlider.setDisable(false);
        heightText.setDisable(false);
    }

    @FXML
    protected void canvasClicked(MouseEvent mouseEvent) {
        int counter = 0;
        for (int i = 0; i < model.getShapes().size(); i++) {
            Shape shape = model.getShapes().get(i);
            if(shape.onClick(mouseEvent) && select) {
                if (shape.isSelected) {
                    model.removeFromSelectedList();
                    shape.deSelect();
                }
                if (!shape.isSelected) {
                    model.removeFromSelectedList();
                    model.addSelectedList(shape);
                    shape.select();
                }
            } else
                counter++;
        }
        if (counter == model.getShapes().size() && !select) {
            model.removeFromSelectedList();
            createNewShape(mouseEvent);
        }
    }

    private void createNewShape(MouseEvent mouseEvent) {
        Shape shape = Shape.createShape(choiceBox.getValue(), mouseEvent.getX(), mouseEvent.getY(), widthSlider.getValue(), heightSlider.getValue(), colorPicker.getValue());
        model.addShape(shape);
        Command undo = () -> model.remove(shape);
        undoCommandStack.push(undo);
        undoShapeStack.push(shape);
        redoShapeStack.clear();
        redoCommandStack.clear();
    }

    @FXML
    protected void undoStack(){
        if (undoCommandStack.size() > 0 && undoShapeStack.size() > 0) {
            Shape shape = undoShapeStack.pop();
            Command redo = () -> model.addShape(shape);
            redoCommandStack.push(redo);
            redoShapeStack.push(shape);
            Command undoToExecute = undoCommandStack.pop();
            undoToExecute.execute();
        }

    }
    @FXML
    protected void redoStack(){
        if (redoCommandStack.size() > 0 && redoShapeStack.size() > 0){
            Shape shape = redoShapeStack.pop();
            model.addShape(shape);
            Command undo = () -> model.remove(shape);
            undoCommandStack.push(undo);
            undoShapeStack.push(shape);
        }
    }
    @FXML
    protected void onClearButtonClick() {
        context.clearRect(0,0, canvas.getWidth(),canvas.getHeight());
        redoShapeStack.clear();
        undoShapeStack.clear();
        undoCommandStack.clear();
        redoCommandStack.clear();
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

    public void onCreateButtonClick(ActionEvent actionEvent) {
        select = false;
    }

    public void onSelectButtonClick(ActionEvent actionEvent) {
        select = true;
    }
}
    @FunctionalInterface
interface Command {
    public void execute();
}