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
import java.util.concurrent.atomic.AtomicReference;

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
    static Deque<UnifiedCommand> undoCommandStack = new ArrayDeque<>();
    static Deque<UnifiedCommand> redoCommandStack = new ArrayDeque<>();

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

    }


    private void widthSliderChange(Observable observable) {
        widthText.setText(String.valueOf((int) widthSlider.getValue()));
        if (model.getSelectedShapes().size() > 0) {
            var shape = model.getSelectedShapes().get(0).getShape().getShape();
            shape.reSizeX(widthSlider.getValue());
            drawShapes();
        }
    }

    private void heightSliderChange(Observable observable) {
        heightText.setText(String.valueOf((int) heightSlider.getValue()));
        if (model.getSelectedShapes().size() > 0) {
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
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Shape s : model.getShapes()) {
            s.draw(context);
        }
        for (Shape s : model.getSelectedShapes()) {
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
        heightLabel.setDisable(true);
        heightSlider.setDisable(true);
        heightText.setDisable(true);
    }

    private void arrangeCircleSliders() {
        widthLabel.setText("Radius");
        heightLabel.setText("");
        heightLabel.setDisable(true);
        heightSlider.setDisable(true);
        heightText.setDisable(true);
    }

    private void arrangeRectSliders() {
        widthLabel.setText("Width");
        heightLabel.setText("Height");
        heightLabel.setDisable(false);
        heightSlider.setDisable(false);
        heightText.setDisable(false);
    }

    @FXML
    protected void canvasClicked(MouseEvent mouseEvent) {
        int counter = 0;
        counter = searchSelection(mouseEvent, counter);
        if (counter == model.getShapes().size() && !select) {
            model.getSelectedShapes().clear();
            createNewShape(mouseEvent);
        }
    }

    private int searchSelection(MouseEvent mouseEvent, int counter) {
        for (Shape s: model.getShapes()) {
            if (s.onClick(mouseEvent) && select) {
                if (s.isSelected) {
                    s.deSelect();
                    model.removeFromSelectedList();
                }
                if (!s.isSelected) {
                    choiceBox.getSelectionModel().select(s.getShape().getShapeType());
                    s.select();
                    model.removeFromSelectedList();
                    model.addSelectedList(s);
                    colorPicker.setValue(s.getColor());
                }
            } else
                counter++;
        }
        return counter;
    }

    private void createNewShape(MouseEvent mouseEvent) {
        Shape shape = Shape.createShape(choiceBox.getValue(), mouseEvent.getX(), mouseEvent.getY(), widthSlider.getValue(), heightSlider.getValue(), colorPicker.getValue());
        model.addShape(shape);

        Command undo = () -> {
            model.remove(shape);
            drawShapes();
        };

        UnifiedCommand unifiedCommand = new UnifiedCommand(CommandType.SHAPE, undo, shape,shape.getColor());
        undoCommandStack.push(unifiedCommand);
        redoCommandStack.clear();
    }

    @FXML
    protected void undoStack() {
        if (undoCommandStack.size() > 0) {
            clearSelection();

            UnifiedCommand unifiedCommand = undoCommandStack.pop();
            Shape shape = unifiedCommand.getShape();
            Color color = shape.getColor();
            Color previousColor = unifiedCommand.getColor();

            CommandType commandType = unifiedCommand.getCommandType();

            if (commandType == CommandType.SHAPE) {
                Command redo = () -> {
                    model.addShape(shape);
                    drawShapes();
                };
                UnifiedCommand commandToRedo = new UnifiedCommand(commandType, redo, shape, previousColor);
                redoCommandStack.push(commandToRedo);
            }

            if (commandType == CommandType.COLOR) {
                Command redo = () -> {
                    colorPicker.setValue(previousColor);
                    unifiedCommand.setColor(color);
                    shape.setColor(colorPicker.getValue());
                    drawShapes();
                };
                UnifiedCommand commandToRedo = new UnifiedCommand(commandType, redo, shape, previousColor);
                redoCommandStack.push(commandToRedo);
            }
            Command undoToExecute = unifiedCommand.getCommand();
            undoToExecute.execute();
        }
    }



    @FXML
    protected void redoStack() {
        if (redoCommandStack.size() > 0) {
            clearSelection();

            UnifiedCommand unifiedCommand = redoCommandStack.pop();
            Shape shape = unifiedCommand.getShape();
            Color color = shape.getColor();
            Color previousColor = unifiedCommand.getColor();

            CommandType commandType = unifiedCommand.getCommandType();

            if (commandType == CommandType.SHAPE){
                Command undo = () -> {
                    model.remove(shape);
                    drawShapes();
                };
                UnifiedCommand commandToUndo = new UnifiedCommand(commandType, undo, shape, previousColor);
                undoCommandStack.push(commandToUndo);
            }

            if (commandType == CommandType.COLOR) {
                Command undo = () -> {
                    colorPicker.setValue(color);
                    unifiedCommand.setColor(previousColor);
                    shape.setColor(colorPicker.getValue());
                    drawShapes();
                };
                UnifiedCommand commandToUndo = new UnifiedCommand(commandType, undo, shape, previousColor);
                undoCommandStack.push(commandToUndo);
            }

            Command redoToExecute = unifiedCommand.getCommand();
            redoToExecute.execute();
        }
    }
    private void clearSelection() {
        for (Shape s : model.getSelectedShapes())
            s.deSelect();
        model.getSelectedShapes().clear();
    }
    @FXML
    protected void onClearButtonClick() {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        undoCommandStack.clear();
        redoCommandStack.clear();
        model.removeAll();
    }

    @FXML
    protected void onUndoButtonClick() {
        undoStack();
    }

    @FXML
    protected void onRedoButtonClick() {
        redoStack();
    }

    public void onCreateButtonClick(ActionEvent actionEvent) {
        select = false;
    }

    public void onSelectButtonClick(ActionEvent actionEvent) {
        select = true;
    }

    public void onApplyColorButtonClick(ActionEvent actionEvent) {
        if (model.getSelectedShapes().size() > 0) {
            Shape shape = model.getSelectedShapes().get(0);
            Color color = shape.getColor();
            Color newColor = colorPicker.getValue();
            shape.setColor(newColor);

            Command undo = () -> {
                shape.setColor(color);
                drawShapes();
            };
            UnifiedCommand unifiedCommand = new UnifiedCommand(CommandType.COLOR, undo, shape, newColor);
            undoCommandStack.push(unifiedCommand);
            drawShapes();
        }
    }
}

@FunctionalInterface
interface Command {
    public void execute();
}

