package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CurrentOrderTabController {

    @FXML
    private ListView<String> currentOrderList;

    @FXML
    private TextField subtotalField, taxField, totalField;

    @FXML
    public void initialize() {
        // Initialize fields with default values
        subtotalField.setText("0.00");
        taxField.setText("0.00");
        totalField.setText("0.00");
    }

    @FXML
    public void handleRemovePizza() {
        String selectedPizza = currentOrderList.getSelectionModel().getSelectedItem();
        if (selectedPizza != null) {
            currentOrderList.getItems().remove(selectedPizza);
            updateTotals();
        }
    }

    @FXML
    public void handleClearOrder() {
        currentOrderList.getItems().clear();
        updateTotals();
    }

    @FXML
    public void handlePlaceOrder() {
        // Finalize the current order and clear the list
        currentOrderList.getItems().clear();
        updateTotals();
    }

    private void updateTotals() {
        double subtotal = currentOrderList.getItems().stream()
                .mapToDouble(this::calculatePizzaPrice)
                .sum();
        double tax = subtotal * 0.06625;
        double total = subtotal + tax;

        subtotalField.setText(String.format("%.2f", subtotal));
        taxField.setText(String.format("%.2f", tax));
        totalField.setText(String.format("%.2f", total));
    }

    private double calculatePizzaPrice(String pizzaDetails) {
        // Extract price from pizza details (dummy implementation)
        return Double.parseDouble(pizzaDetails.split(" - ")[1].replace("$", ""));
    }
}
