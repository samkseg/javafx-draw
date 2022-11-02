package se.iths.laboration3.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import se.iths.laboration3.Model;
import se.iths.laboration3.Shape;
import se.iths.laboration3.ShapeType;

public class CanvasViewController {
    @FXML
    public Canvas canvas;
    public GraphicsContext context;
    public Group root = new Group();
    public ChoiceBox<ShapeType> choiceBox;
    public ColorPicker colorPicker;
    public Slider widthSlider;
    public Slider heightSlider;
    public Label widthLabel;
    public Label heightLabel;

    public Button clearButton;

    Model model = new Model();
    ObservableList<ShapeType> shapeTypesList = FXCollections.observableArrayList(ShapeType.values());


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
        for (Shape s : model.getShapes()) {
            s.draw(context);
        }
    }

    private void choiceBoxChanged(Observable observable) {

        ShapeType shapeChoice = choiceBox.getSelectionModel().getSelectedItem();
        switch (shapeChoice) {
            case RECTANGLE -> {
                widthLabel.setText("Width");
                heightLabel.setText("Height");
                widthSlider.setMax(100);
                heightSlider.setMax(100);
                widthSlider.setValue(0);
                widthSlider.setValue(0);
                heightLabel.setDisable(false);
                heightSlider.setDisable(false);
            }
            case CIRCLE -> {
                widthLabel.setText("Radius");
                heightLabel.setText("");
                widthSlider.setMax(50);
                heightSlider.setMax(50);
                widthSlider.setValue(0);
                widthSlider.setValue(0);
                heightLabel.setDisable(true);
                heightSlider.setDisable(true);
            }
            case TRIANGLE -> {
                widthLabel.setText("Side");
                heightLabel.setText("");
                widthSlider.setMax(100);
                heightSlider.setMax(100);
                widthSlider.setValue(0);
                widthSlider.setValue(0);
                heightLabel.setDisable(true);
                heightSlider.setDisable(true);
            }
        }
    }
    @FXML
    protected void canvasClicked(MouseEvent mouseEvent) {
        Shape shape = Shape.createShape(choiceBox.getValue(), mouseEvent.getX(), mouseEvent.getY(), widthSlider.getValue(), heightSlider.getValue(), colorPicker.getValue());
        model.addShape(shape);
    }

    @FXML
    protected void onClearButtonClick() {
        context.clearRect(0,0, canvas.getWidth(),canvas.getHeight());
    }
}