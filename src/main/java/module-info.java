/**
 * Module declaration for the RU Pizzeria application.
 * Specifies dependencies and module accessibility.
 */
module com.example.smproj4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.smproj4 to javafx.fxml;
    exports com.example.smproj4;
}