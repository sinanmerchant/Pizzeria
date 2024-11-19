package com.example.smproj4;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Controller for managing and viewing store orders.
 * Allows the user to:
 * - View order details for a selected order.
 * - Cancel an order.
 * - Export all orders to a text file.
 * 
 * The class interacts with {@code OrderHistory} to fetch, modify, and export orders.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class StoreOrdersTabController {

    @FXML
    private ComboBox<Integer> orderNumberComboBox;

    @FXML
    private TextField orderTotalField;

    @FXML
    private ListView<String> pizzasListView;

    private OrderHistory orderHistory;

    private static final double TAX_RATE = 0.06625;

    /**
     * Initializes the controller.
     * Sets up listeners for order selection and initializes the UI components.
     */
    @FXML
    private void initialize() {
        orderNumberComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            displayOrderDetails(newVal);
        });
    }

    /**
     * Sets the {@code OrderHistory} instance to be used by this controller.
     * Updates the order numbers in the combo box.
     * 
     * @param history The {@code OrderHistory} instance.
     */
    public void setOrderHistory(OrderHistory history) {
        this.orderHistory = history;
        updateOrderNumbers();
    }

    /**
     * Updates the list of order numbers in the combo box.
     * If orders are present, the first order is selected by default.
     */
    public void updateOrderNumbers() {
        List<Integer> orderNumbers = orderHistory.getAllOrders().stream()
                .map(Order::getNumber)
                .collect(java.util.stream.Collectors.toList());
        orderNumberComboBox.setItems(FXCollections.observableArrayList(orderNumbers));

        if (!orderNumbers.isEmpty()) {
            orderNumberComboBox.getSelectionModel().clearSelection();
            orderNumberComboBox.getSelectionModel().selectFirst();

            Integer selectedOrderNumber = orderNumberComboBox.getSelectionModel().getSelectedItem();
            displayOrderDetails(selectedOrderNumber);
        } else {
            clearOrderDetails();
        }
    }

    /**
     * Displays the details of the selected order.
     * Shows the pizzas in the order and calculates the total with tax.
     * 
     * @param orderNumber The selected order number.
     */
    private void displayOrderDetails(Integer orderNumber) {
        if (orderNumber == null) {
            clearOrderDetails();
            return;
        }

        Order selectedOrder = orderHistory.getOrderByNumber(orderNumber);
        if (selectedOrder != null) {
            pizzasListView.getItems().clear();
            for (Pizza pizza : selectedOrder.getPizzas()) {
                pizzasListView.getItems().add(pizza.toString());
            }

            double subtotal = selectedOrder.calculateTotal();
            double tax = subtotal * TAX_RATE;
            double total = subtotal + tax;
            orderTotalField.setText(String.format("$%.2f", total));
        } else {
            clearOrderDetails();
        }
    }

    /**
     * Clears the order details from the UI.
     */
    private void clearOrderDetails() {
        pizzasListView.getItems().clear();
        orderTotalField.clear();
    }

    /**
     * Handles the cancellation of the selected order.
     * Removes the order from the order history and updates the UI.
     */
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
        } else {
            showAlert("Error", "Order Not Found", "The selected order could not be found.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the export of all orders to a text file.
     * Includes order details such as pizzas and total amounts.
     */
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

    /**
     * Displays an alert dialog with the specified title, header, and content.
     * 
     * @param title   The title of the alert.
     * @param header  The header text of the alert.
     * @param content The content message of the alert.
     * @param type    The type of alert (e.g., INFORMATION, ERROR).
     */
    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
