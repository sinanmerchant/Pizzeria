package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

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

    private Order currentOrder;
    private int orderCounter = 1;

    private Tab currentOrderTab;
    private CurrentOrderTabController currentOrderController;

    private OrderHistory orderHistory = new OrderHistory();

    private Map<String, OrderPizzaTabController> pizzaControllers = new HashMap<>();

    private StoreOrdersTabController storeOrdersController; // Added reference

    @FXML
    public void initialize() {
        // Initialize images for the buttons
        chicagoImageView.setImage(new Image(getClass().getResource("/images/chicago_pizza.jpg").toExternalForm()));
        nyImageView.setImage(new Image(getClass().getResource("/images/ny_pizza.jpg").toExternalForm()));
        ordersImageView.setImage(new Image(getClass().getResource("/images/orders_icon.jpg").toExternalForm()));
        cartImageView.setImage(new Image(getClass().getResource("/images/cart_icon.jpg").toExternalForm()));

        // Create a new order
        currentOrder = createNewOrder();
        loadCurrentOrderTab();
    }

    private Order createNewOrder() {
        return new Order(orderCounter++);
    }

    @FXML
    public void handleChicagoStyle() {
        loadPizzaTab("Chicago Style", "/com/example/smproj4/OrderPizzaTab.fxml", "Chicago Style");
    }

    @FXML
    public void handleNYStyle() {
        loadPizzaTab("NY Style", "/com/example/smproj4/OrderPizzaTab.fxml", "NY Style");
    }

    @FXML
    public void handleOrdersPlaced() {
        loadTab("Store Orders", "/com/example/smproj4/StoreOrdersTab.fxml");
    }

    @FXML
    public void handleCurrentOrder() {
        if (currentOrderTab != null) {
            tabPane.getSelectionModel().select(currentOrderTab);
        } else {
            loadCurrentOrderTab();
        }
    }

    private void loadPizzaTab(String title, String fxmlPath, String style) {
        Tab existingTab = findTab(title);
        if (existingTab != null) {
            tabPane.getSelectionModel().select(existingTab);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane content = loader.load();

            // Set pizza style and shared order
            OrderPizzaTabController pizzaController = loader.getController();
            pizzaController.setPizzaStyle(style);
            pizzaController.setOrder(currentOrder);
            pizzaController.setUpdateCallback(() -> {
                if (currentOrderController != null) {
                    currentOrderController.updateOrderDetails();
                }
            });

            // Store the controller in the map
            pizzaControllers.put(title, pizzaController);

            Tab pizzaTab = new Tab(title, content);
            tabPane.getTabs().add(pizzaTab);
            tabPane.getSelectionModel().select(pizzaTab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCurrentOrderTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/smproj4/CurrentOrderTab.fxml"));
            Pane content = loader.load();

            currentOrderController = loader.getController();
            currentOrderController.setOrder(currentOrder);
            currentOrderController.setMainController(this);

            currentOrderTab = new Tab("Current Order", content);
            tabPane.getTabs().add(currentOrderTab);
            tabPane.getSelectionModel().select(currentOrderTab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTab(String title, String fxmlPath) {
        Tab existingTab = findTab(title);
        if (existingTab != null) {
            tabPane.getSelectionModel().select(existingTab);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane content = loader.load();

            if (title.equals("Store Orders")) {
                storeOrdersController = loader.getController(); // Store the controller reference
                storeOrdersController.setOrderHistory(orderHistory);
            }

            Tab newTab = new Tab(title, content);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Tab findTab(String title) {
        return tabPane.getTabs().stream()
                .filter(tab -> tab.getText().equals(title))
                .findFirst()
                .orElse(null);
    }

    public void createNewOrderAndUpdate() {
        currentOrder = createNewOrder();
        if (currentOrderController != null) {
            currentOrderController.setOrder(currentOrder);
            currentOrderController.updateOrderDetails();
        }
        // Update all pizza controllers with the new currentOrder
        for (OrderPizzaTabController pizzaController : pizzaControllers.values()) {
            pizzaController.setOrder(currentOrder);
        }
    }

    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public void updateStoreOrdersTab() {
        if (storeOrdersController != null) {
            storeOrdersController.updateOrderNumbers();
        }
    }
}
