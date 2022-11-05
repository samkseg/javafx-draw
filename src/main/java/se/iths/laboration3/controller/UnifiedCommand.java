package se.iths.laboration3.controller;

import javafx.scene.paint.Color;
import se.iths.laboration3.shapes.*;

public class UnifiedCommand {

    private CommandType commandType;
    private Command command;
    private Shape shape;

    private Color color;
    private double width;
    private double height;

    public UnifiedCommand(CommandType commandType, Command command, Shape shape, Color color, double width, double height) {
        this.commandType = commandType;
        this.command = command;
        this.shape = shape;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
