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
import se.iths.laboration3.saveImage;
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
    private Model model = new Model();
    private boolean selectMode;
    private ObservableList<ShapeType> shapeTypesList = FXCollections.observableArrayList(ShapeType.values());
    protected static Deque<UnifiedCommand> undoCommandStack = new ArrayDeque<>();
    protected static Deque<UnifiedCommand> redoCommandStack = new ArrayDeque<>();
    public void initialize() {

        context = canvas.getGraphicsContext2D();

        choiceBox.setItems(shapeTypesList);
        choiceBox.setValue(ShapeType.RECTANGLE);

        colorPicker.setValue(Color.WHITE);
        widthSlider.setMin(1);
        heightSlider.setMin(1);
        widthSlider.setMax(500);
        heightSlider.setMax(500);
        widthSlider.setValue(50);
        heightSlider.setValue(50);
        widthText.setText("50");
        heightText.setText("50");
        widthText.setText(String.valueOf(widthSlider.getValue()));
        heightText.setText(String.valueOf(heightSlider.getValue()));

        choiceBox.getSelectionModel().selectedItemProperty().addListener(this::choiceBoxChanged);
        widthText.styleProperty().addListener(this::widthTextChange);
        heightText.styleProperty().addListener(this::heightTextChange);
        widthSlider.valueProperty().addListener(this::widthSliderChange);
        heightSlider.valueProperty().addListener(this::heightSliderChange);
        model.getShapes().addListener(this::listChanged);
        model.getSelectedShapes().addListener(this::listChanged);
    }
    private void widthTextChange(Observable observable) {
        widthSlider.setValue(Integer.parseInt(widthText.getText()));
    }
    private void heightTextChange(Observable observable) {
        heightSlider.setValue(Integer.parseInt(heightText.getText()));
    }
    private void widthSliderChange(Observable observable) {
        widthText.setText(String.valueOf((int) widthSlider.getValue()));
    }
    private void heightSliderChange(Observable observable) {
        heightText.setText(String.valueOf((int) heightSlider.getValue()));
    }
    private void listChanged(Observable observable) {
        drawShapes();
    }
    private void drawShapes() {
        var context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        model.getShapes().forEach(shape -> shape.draw(context));
        model.getSelectedShapes().forEach(shape -> shape.draw(context));
    }
    private void choiceBoxChanged(Observable observable) {
        ShapeType shapeChoice = choiceBox.getSelectionModel().getSelectedItem();
        switch (shapeChoice) {
            case RECTANGLE -> arrangeRectSliders();
            case CIRCLE -> arrangeCircleSliders();
            case TRIANGLE -> arrangeTriangleSliders();
            case SQUARE -> arrangeSquareSliders();
        }
    }
    private void arrangeSquareSliders() {
        widthLabel.setText("Side");
        heightLabel.setText("");
        heightLabel.setDisable(true);
        heightSlider.setDisable(true);
        heightText.setDisable(true);
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
    private void canvasClicked(MouseEvent mouseEvent) {
        int counter = 0;
        counter = findSelection(mouseEvent, counter);
        if (counter == model.getShapes().size() && !selectMode) {
            model.getSelectedShapes().clear();
            createNewShape(mouseEvent.getX(),mouseEvent.getY());
        }
    }
    private int findSelection(MouseEvent mouseEvent, int counter) {
        for (Shape shape: model.getShapes()) {
            if (shape.onClick(mouseEvent) && selectMode) {
                if (shape.isSelected)
                    deSelectShape();
                if (!shape.isSelected)
                    selectShape(shape);
            } else
                counter++;
        }
        return counter;
    }
    private void deSelectShape() {
        choiceBox.setDisable(false);
        model.clearSelection();
    }
    private void selectShape(Shape shape) {
        choiceBox.getSelectionModel().select(shape.getShape().getShapeType());
        widthSlider.setValue(shape.getShape().getXSize());
        heightSlider.setValue(shape.getShape().getYSize());
        choiceBox.setDisable(true);
        colorPicker.setValue(shape.getColor());

        model.addSelectedList(shape);
    }
    private void createNewShape(double x, double y) {
        model.clearSelection();
        Shape shape = Shape.createShape(choiceBox.getValue(),
                x, y,
                widthSlider.getValue(), heightSlider.getValue(),
                colorPicker.getValue());
        model.addShape(shape);
        model.addSelectedList(shape);

        Command undo = () -> {
            model.clearSelection();
            model.remove(shape);
        };

        UnifiedCommand unifiedCommand = new UnifiedCommand(CommandType.SHAPE,
                undo, shape,shape.getColor(), shape.getXSize(),shape.getYSize());
        undoCommandStack.push(unifiedCommand);
        redoCommandStack.clear();
        drawShapes();
    }
    private void undoStack() {
        if (!undoCommandStack.isEmpty()) {
            model.clearSelection();

            UnifiedCommand unifiedCommand = undoCommandStack.pop();
            Shape shape = unifiedCommand.getShape();
            Color color = shape.getColor();
            Color previousColor = unifiedCommand.getColor();
            double oldWidth = shape.getXSize();
            double oldHeight = shape.getYSize();
            double newWidth = unifiedCommand.getWidth();
            double newHeight = unifiedCommand.getHeight();
            model.addSelectedList(shape);

            CommandType commandType = unifiedCommand.getCommandType();
            Command redo = () -> {};
            if (commandType == CommandType.SHAPE)
                redo = () -> {
                    model.clearSelection();
                    model.addSelectedList(shape);
                    model.addShape(shape);
                };
            if (commandType == CommandType.COLOR)
                redo = () -> {
                    colorPicker.setValue(previousColor);
                    unifiedCommand.setColor(color);
                    shape.setColor(colorPicker.getValue());
                    model.clearSelection();
                    model.addSelectedList(shape);
                };
            if (commandType == CommandType.RESIZE)
                redo = () -> {
                    model.clearSelection();
                    model.addSelectedList(shape);
                    shape.reSizeX(oldWidth);
                    shape.reSizeY(oldHeight);
            };

            UnifiedCommand commandToRedo = new UnifiedCommand(commandType,
                    redo, shape, previousColor, newWidth, newHeight);
            redoCommandStack.push(commandToRedo);
            Command undoToExecute = unifiedCommand.getCommand();
            undoToExecute.execute();
            drawShapes();
        }
    }
    private void redoStack() {
        if (!redoCommandStack.isEmpty()) {
            model.clearSelection();

            UnifiedCommand unifiedCommand = redoCommandStack.pop();
            Shape shape = unifiedCommand.getShape();
            Color color = shape.getColor();
            Color previousColor = unifiedCommand.getColor();
            double newWidth = unifiedCommand.getWidth();
            double newHeight = unifiedCommand.getHeight();
            model.addSelectedList(shape);

            CommandType commandType = unifiedCommand.getCommandType();
            Command undo = () ->{};
            if (commandType == CommandType.SHAPE)
                undo = () -> {
                    model.clearSelection();
                    model.remove(shape);
                };
            if (commandType == CommandType.COLOR)
                undo = () -> {
                    colorPicker.setValue(color);
                    unifiedCommand.setColor(previousColor);
                    shape.setColor(colorPicker.getValue());
                    model.clearSelection();
                    model.addSelectedList(shape);
                };
            if (commandType == CommandType.RESIZE)
                undo = () -> {
                    model.clearSelection();
                    model.addSelectedList(shape);
                    shape.reSizeX(newWidth);
                    shape.reSizeY(newHeight);
                };

            UnifiedCommand commandToUndo = new UnifiedCommand(commandType,
                    undo, shape, previousColor, newWidth,newHeight);
            undoCommandStack.push(commandToUndo);
            Command redoToExecute = unifiedCommand.getCommand();
            redoToExecute.execute();
            drawShapes();
        }
    }
    private void applyColor() {
        if (!model.getSelectedShapes().isEmpty()) {
            Shape shape = model.getSelectedShapes().get(0);
            Color color = shape.getColor();
            Color newColor = colorPicker.getValue();
            shape.setColor(newColor);

            Command undo = () -> {
                shape.setColor(color);
                model.clearSelection();
            };

            UnifiedCommand unifiedCommand = new UnifiedCommand(CommandType.COLOR,
                    undo, shape, newColor, shape.getXSize(),shape.getYSize());
            undoCommandStack.push(unifiedCommand);
            redoCommandStack.clear();
            drawShapes();
        }
    }
    private void applyReSize() {
        if (!model.getSelectedShapes().isEmpty()){
            Shape shape = model.getSelectedShapes().get(0);
            double oldWidth = shape.getXSize();
            double oldHeight = shape.getYSize();
            shape.reSizeX(Double.parseDouble(widthText.getText()));
            if (shape.getShapeType() == ShapeType.RECTANGLE) {
                shape.reSizeY(Double.parseDouble(heightText.getText()));
            }

            Command undo = () -> {
                model.clearSelection();
                model.addSelectedList(shape);
                shape.reSizeX(oldWidth);
                shape.reSizeY(oldHeight);
            };

            UnifiedCommand unifiedCommand = new UnifiedCommand(CommandType.RESIZE,
                    undo, shape, shape.getColor(), oldWidth, oldHeight);
            undoCommandStack.push(unifiedCommand);
            redoCommandStack.clear();
            drawShapes();
        }
    }
    @FXML
    private void onClearButtonClick() {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        undoCommandStack.clear();
        redoCommandStack.clear();
        model.removeAll();
    }
    @FXML
    private void onUndoButtonClick() {
        undoStack();
    }
    @FXML
    private void onRedoButtonClick() {
        redoStack();
    }
    @FXML
    private void onCreateButtonClick(ActionEvent actionEvent) {
        model.clearSelection();
        selectMode = false;
        choiceBox.setDisable(false);
    }
    @FXML
    private void onSelectButtonClick(ActionEvent actionEvent) {
        choiceBox.setDisable(true);
        selectMode = true;
    }
    @FXML
    private void onApplyColorButtonClick(ActionEvent actionEvent) {
        applyColor();
    }
    @FXML
    private void onApplySizeButtonClick(ActionEvent actionEvent) {
        applyReSize();
    }
    @FXML
    private void onSavePNGButtonClick(ActionEvent actionEvent) {
        model.clearSelection();
        saveImage.savePNG(canvas);
    }
    @FXML
    private void onSaveSVGButtonClick(ActionEvent actionEvent) {
        saveImage.saveSVG(model);
    }
}
@FunctionalInterface
interface Command {
    public void execute();
}

