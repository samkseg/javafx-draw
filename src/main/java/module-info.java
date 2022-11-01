module com.example.laboration3 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens se.iths.laboration3 to javafx.fxml;
    exports se.iths.laboration3;
    exports se.iths.laboration3.controller;
    opens se.iths.laboration3.controller to javafx.fxml;
}