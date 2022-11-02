package se.iths.laboration3.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import se.iths.laboration3.ShapeModel;
import se.iths.laboration3.ShapeType;

import java.util.ArrayList;
import java.util.List;

public class CanvasViewController {
    @FXML
    public Canvas canvas;
    public GraphicsContext context;
    public Group root = new Group();
    public ChoiceBox<ShapeType> choiceBox;
    public ColorPicker colorPicker;
    public Slider widthSlider;
    public Slider heightSlider;

    ObservableList<ShapeType> shapeTypesList = FXCollections.observableArrayList(ShapeType.values());
    List<ShapeModel> shapeModelList = new ArrayList<>();


    public void initialize() {
        context = canvas.getGraphicsContext2D();
        choiceBox.setItems(shapeTypesList);
        choiceBox.setValue(ShapeType.CIRCLE);
        colorPicker.setValue(Color.WHITE);
    }
    @FXML
    protected void canvasClicked(MouseEvent mouseEvent) {

        ShapeModel shapeModel = ShapeModel.createShape(choiceBox.getValue(), mouseEvent.getX(), mouseEvent.getY(), widthSlider.getValue(), heightSlider.getValue(), colorPicker.getValue());
        shapeModelList.add(shapeModel);
        shapeModel.draw(context);
        System.out.println(shapeModel);
    }
}