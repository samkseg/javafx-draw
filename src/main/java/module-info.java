module se.iths.laboration3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;


    opens se.iths.laboration3 to javafx.fxml;
    exports se.iths.laboration3;
    exports se.iths.laboration3.controller;
    opens se.iths.laboration3.controller to javafx.fxml;
    exports se.iths.laboration3.shapes;
    opens se.iths.laboration3.shapes to javafx.fxml;
    exports se.iths.laboration3.model;
    opens se.iths.laboration3.model to javafx.fxml;
}