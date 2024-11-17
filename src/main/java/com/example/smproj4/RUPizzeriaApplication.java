package com.example.smproj4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RUPizzeriaApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(RUPizzeriaApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("RU Pizzeria");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
