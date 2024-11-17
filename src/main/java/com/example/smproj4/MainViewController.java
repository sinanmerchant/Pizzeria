package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    private ImageView chicagoImageView;

    @FXML
    private ImageView nyImageView;

    @FXML
    private ImageView ordersImageView;

    @FXML
    private ImageView cartImageView;

    @FXML
    public void initialize() {
        chicagoImageView.setImage(new Image(getClass().getResource("/images/chicago_pizza.jpg").toExternalForm()));
        nyImageView.setImage(new Image(getClass().getResource("/images/ny_pizza.jpg").toExternalForm()));
        ordersImageView.setImage(new Image(getClass().getResource("/images/orders_icon.jpg").toExternalForm()));
        cartImageView.setImage(new Image(getClass().getResource("/images/cart_icon.jpg").toExternalForm()));


    }

    public void handleChicagoStyle() {
        addTab("Chicago Style", "/com/example/smproj4/OrderPizzaTab.fxml");
    }

    public void handleNYStyle() {
        addTab("NY Style", "/com/example/smproj4/OrderPizzaTab.fxml");
    }

    public void handleOrdersPlaced() {
        addTab("Store Orders", "/com/example/smproj4/StoreOrdersTab.fxml");
    }

    public void handleCurrentOrder() {
        addTab("Current Order", "/com/example/smproj4/CurrentOrderTab.fxml");
    }

    private void addTab(String title, String fxmlPath) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(title)) {
                tabPane.getSelectionModel().select(tab);
                return;
            }
        }

        try {
            Tab newTab = new Tab(title);
            newTab.setContent(FXMLLoader.load(getClass().getResource(fxmlPath)));
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

