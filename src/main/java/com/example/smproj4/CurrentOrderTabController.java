package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    private void initialize() {
        clearOrderDetails();
    }

    public void setMainController(MainViewController controller) {
        this.mainController = controller;
    }

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

    public void setOrder(Order order) {
        this.currentOrder = order;
        updateOrderDetails();
    }

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

    @FXML
    private void handleClearOrder() {
        currentOrder.getPizzas().clear();
        updateOrderDetails();
    }

    @FXML
    private void handlePlaceOrder() {
        if (currentOrder.getPizzas().isEmpty()) {
            showAlert("Error", "No Items in Order", "You cannot place an empty order.", Alert.AlertType.ERROR);
            return;
        }

        // Add the current order to OrderHistory
        mainController.getOrderHistory().addOrder(currentOrder);

        showAlert("Success", "Order Placed", "Your order has been placed successfully.", Alert.AlertType.INFORMATION);

        // Notify MainViewController to update the Store Orders Window
        mainController.updateStoreOrdersWindow();

        // Create a new order
        mainController.createNewOrderAndUpdate();
    }

    private void clearOrderDetails() {
        orderNumberField.clear();
        orderDetailsList.getItems().clear();
        subtotalField.clear();
        taxField.clear();
        totalField.clear();
    }

    private void updateTotals() {
        if (currentOrder == null) return;

        double subtotal = currentOrder.calculateTotal();
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        subtotalField.setText(String.format("%.2f", subtotal));
        taxField.setText(String.format("%.2f", tax));
        totalField.setText(String.format("%.2f", total));
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
