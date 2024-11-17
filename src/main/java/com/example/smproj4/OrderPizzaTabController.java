package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OrderPizzaTabController {

    @FXML
    private Label pizzaStyleLabel;

    @FXML
    private ImageView pizzaImage;

    @FXML
    private ComboBox<String> pizzaTypeComboBox;

    @FXML
    private RadioButton smallSizeButton, mediumSizeButton, largeSizeButton;

    @FXML
    private ListView<String> availableToppingsList, selectedToppingsList;

    @FXML
    private TextField pizzaPriceField;

    private ToggleGroup sizeGroup;
    private String pizzaStyle;

    public void setPizzaStyle(String style) {
        this.pizzaStyle = style;
    }

    @FXML
    public void initialize() {
        pizzaStyleLabel.setText(pizzaStyle + " Pizza");

        /*
        if ("Chicago Style".equals(pizzaStyle)) {
            pizzaImage.setImage(new Image("/images/chicago_pizza.jpg"));
        } else {
            pizzaImage.setImage(new Image("/images/ny_pizza.jpg"));
        }
        */


        sizeGroup = new ToggleGroup();
        smallSizeButton.setToggleGroup(sizeGroup);
        mediumSizeButton.setToggleGroup(sizeGroup);
        largeSizeButton.setToggleGroup(sizeGroup);

        pizzaTypeComboBox.getItems().addAll("Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own");
        availableToppingsList.getItems().addAll("Mushroom", "Pepperoni", "Onion", "Green Pepper", "Sausage");
        pizzaPriceField.setText("0.00");
    }

    @FXML
    public void handleAddTopping() {
        String selectedTopping = availableToppingsList.getSelectionModel().getSelectedItem();
        if (selectedTopping != null && selectedToppingsList.getItems().size() < 7) {
            availableToppingsList.getItems().remove(selectedTopping);
            selectedToppingsList.getItems().add(selectedTopping);
        }
    }

    @FXML
    public void handleRemoveTopping() {
        String selectedTopping = selectedToppingsList.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            selectedToppingsList.getItems().remove(selectedTopping);
            availableToppingsList.getItems().add(selectedTopping);
        }
    }

    @FXML
    public void handleAddToOrder() {
        // Calculate pizza price based on size and toppings
        double basePrice = 0.0;

        // Check which RadioButton is selected
        Toggle selectedToggle = sizeGroup.getSelectedToggle();
        if (selectedToggle == smallSizeButton) {
            basePrice = 8.99;
        } else if (selectedToggle == mediumSizeButton) {
            basePrice = 10.99;
        } else if (selectedToggle == largeSizeButton) {
            basePrice = 12.99;
        }

        // Calculate total price with toppings
        double totalPrice = basePrice + (selectedToppingsList.getItems().size() * 1.69);
        pizzaPriceField.setText(String.format("%.2f", totalPrice));

        // Add pizza details to the current order (integration required with CurrentOrderTabController)
    }
}
