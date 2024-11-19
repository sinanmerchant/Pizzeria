package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for managing the "Current Order" tab in the application.
 * Handles operations related to the current order, such as viewing order details,
 * removing pizzas, clearing the order, and placing the order.
 * 
 * This controller interacts with the {@code MainViewController} to update shared
 * application states like the order history and store orders.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class CurrentOrderTabController {

    @FXML
    private TextField orderNumberField;

    @FXML
    private ListView<String> orderDetailsList;

    @FXML
    private TextField subtotalField, taxField, totalField;

    private Order currentOrder;

    private static final double TAX_RATE = 0.06625;

    private MainViewController mainController;

    /**
     * Initializes the controller.
     * Clears the current order details upon initialization.
     */
    @FXML
    private void initialize() {
        clearOrderDetails();
    }

    /**
     * Sets the reference to the {@code MainViewController}.
     * This allows interaction with shared data and state.
     * 
     * @param controller The main controller of the application.
     */
    public void setMainController(MainViewController controller) {
        this.mainController = controller;
    }

    /**
     * Updates the details of the current order in the UI.
     */
    public void updateOrderDetails() {
        if (currentOrder == null) return;

        // Update order number
        orderNumberField.setText(String.valueOf(currentOrder.getNumber()));

        // Update order details
        orderDetailsList.getItems().clear();
        for (Pizza pizza : currentOrder.getPizzas()) {
            orderDetailsList.getItems().add(pizza.toString());
        }

        // Update totals
        updateTotals();
    }

    /**
     * Sets the current order and updates the UI.
     * 
     * @param order The current order.
     */
    public void setOrder(Order order) {
        this.currentOrder = order;
        updateOrderDetails();
    }

    /**
     * Handles removing the selected pizza from the current order.
     * If no pizza is selected, an error alert is displayed.
     */
    @FXML
    private void handleRemovePizza() {
        int selectedIndex = orderDetailsList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            currentOrder.getPizzas().remove(selectedIndex);
            updateOrderDetails();
        } else {
            showAlert("Error", "No Pizza Selected", "Please select a pizza to remove.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles clearing all pizzas from the current order.
     * Updates the UI to reflect the cleared state.
     */
    @FXML
    private void handleClearOrder() {
        currentOrder.getPizzas().clear();
        updateOrderDetails();
    }

    /**
     * Handles placing the current order.
     * Validates that the order is not empty before placing it.
     * Adds the order to the order history and creates a new order.
     */
    @FXML
    private void handlePlaceOrder() {
        if (currentOrder.getPizzas().isEmpty()) {
            showAlert("Error", "No Items in Order", "You cannot place an empty order.", Alert.AlertType.ERROR);
            return;
        }

        mainController.getOrderHistory().addOrder(currentOrder);

        showAlert("Success", "Order Placed", "Your order has been placed successfully.", Alert.AlertType.INFORMATION);

        mainController.updateStoreOrdersWindow();

        mainController.createNewOrderAndUpdate();
    }

    /**
     * Clears all fields in the current order view.
     */
    private void clearOrderDetails() {
        orderNumberField.clear();
        orderDetailsList.getItems().clear();
        subtotalField.clear();
        taxField.clear();
        totalField.clear();
    }

    /**
     * Updates the subtotal, tax, and total fields based on the current order.
     */
    private void updateTotals() {
        if (currentOrder == null) return;

        double subtotal = currentOrder.calculateTotal();
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        subtotalField.setText(String.format("%.2f", subtotal));
        taxField.setText(String.format("%.2f", tax));
        totalField.setText(String.format("%.2f", total));
    }

    /**
     * Displays an alert dialog with the given title, header, and content.
     * 
     * @param title   The title of the alert.
     * @param header  The header text of the alert.
     * @param content The content message of the alert.
     * @param type    The type of alert (e.g., ERROR, INFORMATION).
     */
    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
