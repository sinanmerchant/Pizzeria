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

/**
 * Main controller for the RU Pizzeria application.
 * Manages navigation between different views and windows, including Chicago-style,
 * New York-style, current orders, and store orders.
 * 
 * Handles creating new orders, maintaining the order history, and updating UI windows dynamically.
 * 
 * This controller follows a centralized window management strategy using a map to track open windows.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
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

    /**
     * Nested class to hold both the stage and the controller for an open window.
     */
    private static class WindowInfo {
        Stage stage;
        Object controller;

        public WindowInfo(Stage stage, Object controller) {
            this.stage = stage;
            this.controller = controller;
        }
    }

    private Map<String, WindowInfo> openWindows = new HashMap<>();

    /**
     * Initializes the controller.
     * Loads images into the respective {@code ImageView} elements and creates the initial order.
     */
    @FXML
    public void initialize() {
        chicagoImageView.setImage(new Image(getClass().getResource("/images/chicago_pizza.jpg").toExternalForm()));
        nyImageView.setImage(new Image(getClass().getResource("/images/ny_pizza.jpg").toExternalForm()));
        ordersImageView.setImage(new Image(getClass().getResource("/images/orders_icon.jpg").toExternalForm()));
        cartImageView.setImage(new Image(getClass().getResource("/images/cart_icon.jpg").toExternalForm()));

        currentOrder = createNewOrder();
    }

    /**
     * Creates a new {@code Order} with a unique order number.
     * 
     * @return The newly created order.
     */
    private Order createNewOrder() {
        return new Order(orderCounter++);
    }

    /**
     * Opens the Chicago-style pizza ordering window.
     */
    @FXML
    public void handleChicagoStyle() {
        openPizzaWindow("Chicago Style", "/com/example/smproj4/OrderPizzaView.fxml", "Chicago Style");
    }

    /**
     * Opens the New York-style pizza ordering window.
     */
    @FXML
    public void handleNYStyle() {
        openPizzaWindow("NY Style", "/com/example/smproj4/OrderPizzaView.fxml", "NY Style");
    }

    /**
     * Opens the store orders window to view all orders placed.
     */
    @FXML
    public void handleOrdersPlaced() {
        openWindow("Store Orders", "/com/example/smproj4/StoreOrdersView.fxml");
    }

    /**
     * Opens the current order window to manage the ongoing order.
     */
    @FXML
    public void handleCurrentOrder() {
        openWindow("Current Order", "/com/example/smproj4/CurrentOrderView.fxml");
    }

    /**
     * Opens a pizza ordering window for the specified style.
     * 
     * @param windowKey Key used to track the window.
     * @param fxmlPath Path to the FXML file for the window.
     * @param style The pizza style (e.g., "Chicago Style").
     */
    private void openPizzaWindow(String windowKey, String fxmlPath, String style) {
        if (openWindows.containsKey(windowKey)) {
            openWindows.get(windowKey).stage.toFront();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox root = loader.load();

            OrderPizzaTabController pizzaController = loader.getController();
            pizzaController.setPizzaStyle(style);
            pizzaController.setOrder(currentOrder);
            pizzaController.setUpdateCallback(() -> {
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

            openWindows.put(windowKey, new WindowInfo(stage, pizzaController));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /**
     * Opens a general-purpose window.
     * 
     * @param windowKey Key used to track the window.
     * @param fxmlPath Path to the FXML file for the window.
     */
    private void openWindow(String windowKey, String fxmlPath) {
        if (openWindows.containsKey(windowKey)) {
            openWindows.get(windowKey).stage.toFront();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox root = loader.load();

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

        if (openWindows.containsKey("Current Order")) {
            WindowInfo windowInfo = openWindows.get("Current Order");
            if (windowInfo.controller instanceof CurrentOrderTabController) {
                CurrentOrderTabController currentOrderController = (CurrentOrderTabController) windowInfo.controller;
                currentOrderController.setOrder(currentOrder);
                currentOrderController.updateOrderDetails();
            }
        }

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

    /**
     * Retrieves the {@code OrderHistory} object.
     * 
     * @return The order history.
     */
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    /**
     * Updates the store orders window, if it is open.
     */
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
