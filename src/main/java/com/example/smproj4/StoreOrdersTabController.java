package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StoreOrdersTabController {

    @FXML
    private ComboBox<Integer> orderNumberComboBox;

    @FXML
    private TextField orderTotalField;

    @FXML
    private ListView<String> orderDetailsList;

    @FXML
    public void initialize() {
        orderNumberComboBox.getItems().addAll(1, 2, 3);
        orderNumberComboBox.setValue(1);
        orderDetailsList.getItems().addAll(
                "Chicago Style - Deluxe - Small - $16.99",
                "NY Style - Build Your Own - Large - $23.99"
        );
        orderTotalField.setText("40.98");
    }

    @FXML
    public void handleCancelOrder() {
        int orderNumber = orderNumberComboBox.getValue();
    }

    @FXML
    public void handleExportOrders() {
        // Export all orders to a file
    }
}
