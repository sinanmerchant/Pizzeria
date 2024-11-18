package com.example.smproj4;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class StoreOrdersTabController {

    @FXML
    private ComboBox<Integer> orderNumberComboBox;

    @FXML
    private TextField orderTotalField;

    @FXML
    private ListView<String> pizzasListView;

    private OrderHistory orderHistory;

    private static final double TAX_RATE = 0.06625;

    @FXML
    private void initialize() {
        orderNumberComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            displayOrderDetails(newVal);
        });
    }

    public void setOrderHistory(OrderHistory history) {
        this.orderHistory = history;
        updateOrderNumbers();
    }

    public void updateOrderNumbers() {
        List<Integer> orderNumbers = orderHistory.getAllOrders().stream()
                .map(Order::getNumber)
                .collect(java.util.stream.Collectors.toList());
        orderNumberComboBox.setItems(FXCollections.observableArrayList(orderNumbers));

        if (!orderNumbers.isEmpty()) {
            // Clear selection to ensure listener is triggered
            orderNumberComboBox.getSelectionModel().clearSelection();
            orderNumberComboBox.getSelectionModel().selectFirst();

            // Explicitly display order details for the new selection
            Integer selectedOrderNumber = orderNumberComboBox.getSelectionModel().getSelectedItem();
            displayOrderDetails(selectedOrderNumber);
        } else {
            clearOrderDetails();
        }
    }

    private void displayOrderDetails(Integer orderNumber) {
        if (orderNumber == null) {
            clearOrderDetails();
            return;
        }

        Order selectedOrder = orderHistory.getOrderByNumber(orderNumber);
        if (selectedOrder != null) {
            // Display pizzas
            pizzasListView.getItems().clear();
            for (Pizza pizza : selectedOrder.getPizzas()) {
                pizzasListView.getItems().add(pizza.toString());
            }

            // Calculate total with tax
            double subtotal = selectedOrder.calculateTotal();
            double tax = subtotal * TAX_RATE;
            double total = subtotal + tax;
            orderTotalField.setText(String.format("$%.2f", total));
        } else {
            clearOrderDetails();
        }
    }

    private void clearOrderDetails() {
        pizzasListView.getItems().clear();
        orderTotalField.clear();
    }

    @FXML
    private void handleCancelOrder() {
        Integer selectedOrderNumber = orderNumberComboBox.getSelectionModel().getSelectedItem();
        if (selectedOrderNumber == null) {
            showAlert("Error", "No Order Selected", "Please select an order to cancel.", Alert.AlertType.ERROR);
            return;
        }

        Order selectedOrder = orderHistory.getOrderByNumber(selectedOrderNumber);
        if (selectedOrder != null) {
            orderHistory.cancelOrder(selectedOrder);
            showAlert("Success", "Order Cancelled", "Order #" + selectedOrderNumber + " has been cancelled.", Alert.AlertType.INFORMATION);
            updateOrderNumbers();
            // No need to call displayOrderDetails here as it's handled in updateOrderNumbers()
        } else {
            showAlert("Error", "Order Not Found", "The selected order could not be found.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleExportOrders() {
        if (orderHistory.getAllOrders().isEmpty()) {
            showAlert("Error", "No Orders to Export", "There are no orders to export.", Alert.AlertType.ERROR);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Orders to File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(orderTotalField.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (Order order : orderHistory.getAllOrders()) {
                    writer.write("Order #" + order.getNumber() + "\n");
                    for (Pizza pizza : order.getPizzas()) {
                        writer.write(pizza.toString() + "\n");
                    }
                    double subtotal = order.calculateTotal();
                    double tax = subtotal * TAX_RATE;
                    double total = subtotal + tax;
                    writer.write(String.format("Total: $%.2f\n\n", total));
                }
                showAlert("Success", "Orders Exported", "All orders have been exported successfully.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", "Export Failed", "An error occurred while exporting orders.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
