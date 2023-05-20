module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.graphics;
    requires com.fasterxml.jackson.databind;


    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;
    exports com.example.lab4.view;
    exports com.example.lab4.core.json;
    opens com.example.lab4.core.json to com.fasterxml.jackson.databind;
    opens com.example.lab4.view to javafx.fxml;
    exports com.example.lab4.model;
    opens com.example.lab4.model to javafx.fxml;
    exports com.example.lab4.core;
    opens com.example.lab4.core to javafx.fxml;
    exports com.example.lab4.core.ai;
    opens com.example.lab4.core.ai to javafx.fxml;
    exports com.example.lab4.Baza;
    //opens javafx.scene.image to com.fasterxml.jackson.databind;

}