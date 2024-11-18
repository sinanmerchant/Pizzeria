module com.example.smproj4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.smproj4 to javafx.fxml;
    exports com.example.smproj4;
}