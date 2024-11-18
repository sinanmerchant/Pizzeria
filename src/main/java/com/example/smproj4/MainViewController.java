package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class MainViewController {

    @FXML
    private ImageView chicagoImageView;

    @FXML
    private ImageView nyImageView;

    @FXML
    private ImageView ordersImageView;

    @FXML
    private ImageView cartImageView;

    private Order currentOrder;
    private int orderCounter = 1;  // Order counter for generating new order numbers

    private OrderHistory orderHistory = new OrderHistory();

    // Nested class to hold both the Stage and the Controller
    private static class WindowInfo {
        Stage stage;
        Object controller;

        public WindowInfo(Stage stage, Object controller) {
            this.stage = stage;
            this.controller = controller;
        }
    }

    // Map to keep track of open windows and their controllers
    private Map<String, WindowInfo> openWindows = new HashMap<>();

    @FXML
    public void initialize() {
        // Load images for the image views
        chicagoImageView.setImage(new Image(getClass().getResource("/images/chicago_pizza.jpg").toExternalForm()));
        nyImageView.setImage(new Image(getClass().getResource("/images/ny_pizza.jpg").toExternalForm()));
        ordersImageView.setImage(new Image(getClass().getResource("/images/orders_icon.jpg").toExternalForm()));
        cartImageView.setImage(new Image(getClass().getResource("/images/cart_icon.jpg").toExternalForm()));

        // Create a new order
        currentOrder = createNewOrder();
    }

    private Order createNewOrder() {
        // Create a new order with a unique order number
        return new Order(orderCounter++);
    }

    @FXML
    public void handleChicagoStyle() {
        openPizzaWindow("Chicago Style", "/com/example/smproj4/OrderPizzaView.fxml", "Chicago Style");
    }

    @FXML
    public void handleNYStyle() {
        openPizzaWindow("NY Style", "/com/example/smproj4/OrderPizzaView.fxml", "NY Style");
    }

    @FXML
    public void handleOrdersPlaced() {
        openWindow("Store Orders", "/com/example/smproj4/StoreOrdersView.fxml");
    }

    @FXML
    public void handleCurrentOrder() {
        openWindow("Current Order", "/com/example/smproj4/CurrentOrderView.fxml");
    }

    private void openPizzaWindow(String windowKey, String fxmlPath, String style) {
        if (openWindows.containsKey(windowKey)) {
            openWindows.get(windowKey).stage.toFront();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox root = loader.load();

            // Get the controller
            OrderPizzaTabController pizzaController = loader.getController();
            pizzaController.setPizzaStyle(style);
            pizzaController.setOrder(currentOrder);
            pizzaController.setUpdateCallback(() -> {
                // Update current order window if open
                if (openWindows.containsKey("Current Order")) {
                    WindowInfo windowInfo = openWindows.get("Current Order");
                    if (windowInfo.controller instanceof CurrentOrderTabController) {
                        CurrentOrderTabController currentOrderController = (CurrentOrderTabController) windowInfo.controller;
                        currentOrderController.updateOrderDetails();
                    }
                }
            });

            Stage stage = new Stage();
            stage.setTitle(style + " Pizza Order");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(e -> openWindows.remove(windowKey));
            stage.show();

            // Store both the stage and the controller
            openWindows.put(windowKey, new WindowInfo(stage, pizzaController));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openWindow(String windowKey, String fxmlPath) {
        if (openWindows.containsKey(windowKey)) {
            openWindows.get(windowKey).stage.toFront();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox root = loader.load();

            // Get the controller
            Object controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle(windowKey);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(e -> openWindows.remove(windowKey));

            if (controller instanceof CurrentOrderTabController) {
                CurrentOrderTabController currentOrderController = (CurrentOrderTabController) controller;
                currentOrderController.setOrder(currentOrder);
                currentOrderController.setMainController(this);
            } else if (controller instanceof StoreOrdersTabController) {
                StoreOrdersTabController storeOrdersController = (StoreOrdersTabController) controller;
                storeOrdersController.setOrderHistory(orderHistory);
            }

            stage.show();

            // Store both the stage and the controller
            openWindows.put(windowKey, new WindowInfo(stage, controller));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new order and updates the current order controller.
     */
    public void createNewOrderAndUpdate() {
        currentOrder = createNewOrder();

        // Update Current Order window
        if (openWindows.containsKey("Current Order")) {
            WindowInfo windowInfo = openWindows.get("Current Order");
            if (windowInfo.controller instanceof CurrentOrderTabController) {
                CurrentOrderTabController currentOrderController = (CurrentOrderTabController) windowInfo.controller;
                currentOrderController.setOrder(currentOrder);
                currentOrderController.updateOrderDetails();
            }
        }

        // Update all pizza controllers with the new currentOrder
        for (String key : openWindows.keySet()) {
            if (key.equals("Chicago Style") || key.equals("NY Style")) {
                WindowInfo windowInfo = openWindows.get(key);
                if (windowInfo.controller instanceof OrderPizzaTabController) {
                    OrderPizzaTabController pizzaController = (OrderPizzaTabController) windowInfo.controller;
                    pizzaController.setOrder(currentOrder);
                }
            }
        }
    }

    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public void updateStoreOrdersWindow() {
        if (openWindows.containsKey("Store Orders")) {
            WindowInfo windowInfo = openWindows.get("Store Orders");
            if (windowInfo.controller instanceof StoreOrdersTabController) {
                StoreOrdersTabController storeOrdersController = (StoreOrdersTabController) windowInfo.controller;
                storeOrdersController.updateOrderNumbers();
            }
        }
    }
}
