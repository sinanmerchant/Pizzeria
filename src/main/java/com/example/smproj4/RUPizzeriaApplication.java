package com.example.smproj4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the RU Pizzeria system.
 * Extends the {@code Application} class and serves as the entry point for the JavaFX application.
 * 
 * This class loads the {@code MainView.fxml} file and initializes the primary stage.
 * The application allows users to interact with the RU Pizzeria system for ordering pizzas.
 * 
 * Usage:
 * - Run this class to launch the RU Pizzeria application.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class RUPizzeriaApplication extends Application {
    /**
     * Starts the JavaFX application.
     * Loads the {@code MainView.fxml} layout and sets up the primary stage.
     * 
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws Exception If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(RUPizzeriaApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("RU Pizzeria");
        primaryStage.show();
    }

    /**
     * The main method for launching the RU Pizzeria application.
     * 
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
