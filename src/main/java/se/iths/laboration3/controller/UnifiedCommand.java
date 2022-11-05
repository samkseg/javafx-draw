package se.iths.laboration3.controller;

import javafx.scene.paint.Color;
import se.iths.laboration3.shapes.*;

public class UnifiedCommand {

    private CommandType commandType;
    private Command command;
    private Shape shape;

    private Color color;

    public UnifiedCommand(CommandType commandType, Command command, Shape shape, Color color) {
        this.commandType = commandType;
        this.command = command;
        this.shape = shape;
        this.color = color;
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
}
