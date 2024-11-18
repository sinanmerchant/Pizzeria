package com.example.smproj4;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
    private ListView<Topping> availableToppingsList, selectedToppingsList;

    @FXML
    private TextField pizzaPriceField, crustField;

    private ToggleGroup sizeGroup;
    private String pizzaStyle;
    private Pizza currentPizza;
    private PizzaFactory pizzaFactory; // Use PizzaFactory to create pizzas
    private Order currentOrder;
    private Runnable updateCallback;

    /**
     * Sets the pizza style (Chicago or NY Style) for this controller.
     *
     * @param style The pizza style.
     */
    public void setPizzaStyle(String style) {
        this.pizzaStyle = style;
        pizzaStyleLabel.setText(style + " Pizza");

        // Initialize the appropriate PizzaFactory based on the style
        if ("Chicago Style".equals(style)) {
            pizzaFactory = new ChicagoPizza();
        } else if ("NY Style".equals(style)) {
            pizzaFactory = new NYPizza();
        } else {
            showAlert("Error", "Invalid Pizza Style", "The pizza style is invalid.", Alert.AlertType.ERROR);
        }
    }

    public void setOrder(Order order) {
        this.currentOrder = order;
    }

    public void setUpdateCallback(Runnable callback) {
        this.updateCallback = callback;
    }

    @FXML
    private void initialize() {
        sizeGroup = new ToggleGroup();
        smallSizeButton.setToggleGroup(sizeGroup);
        mediumSizeButton.setToggleGroup(sizeGroup);
        largeSizeButton.setToggleGroup(sizeGroup);

        sizeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> handleSizeChange());

        pizzaTypeComboBox.getItems().addAll("Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own");
        pizzaTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> handlePizzaTypeChange(newVal));

        availableToppingsList.getItems().addAll(EnumSet.allOf(Topping.class));
        pizzaPriceField.setText("0.00");
        crustField.setText("");
    }

    private void handlePizzaTypeChange(String pizzaType) {
        if (pizzaType == null) {
            currentPizza = null;
            selectedToppingsList.getItems().clear();
            crustField.setText("");
            pizzaPriceField.setText("0.00");
            return;
        }

        // Ensure pizzaStyle and pizzaFactory are not null
        if (pizzaStyle == null || pizzaFactory == null) {
            showAlert("Error", "Pizza Style Not Set", "The pizza style has not been initialized.", Alert.AlertType.ERROR);
            return;
        }

        selectedToppingsList.getItems().clear();
        availableToppingsList.getItems().clear();
        availableToppingsList.getItems().addAll(EnumSet.allOf(Topping.class));

        // Use the PizzaFactory to create pizzas
        switch (pizzaType) {
            case "Deluxe":
                currentPizza = pizzaFactory.createDeluxe();
                break;

            case "BBQ Chicken":
                currentPizza = pizzaFactory.createBBQChicken();
                break;

            case "Meatzza":
                currentPizza = pizzaFactory.createMeatzza();
                break;

            case "Build Your Own":
                currentPizza = pizzaFactory.createBuildYourOwn();
                unlockSizeAndToppings();
                break;

            default:
                currentPizza = null;
                showAlert("Error", "Invalid Pizza Type", "The selected pizza type is invalid.", Alert.AlertType.ERROR);
                return;
        }

        if (currentPizza != null) {
            crustField.setText(currentPizza.getCrust().toString()); // Display the crust
            setDefaultSize();
            updatePizzaDetails();
        }
    }

    private void unlockSizeAndToppings() {
        availableToppingsList.setDisable(false);
        selectedToppingsList.setDisable(false);
        smallSizeButton.setDisable(false);
        mediumSizeButton.setDisable(false);
        largeSizeButton.setDisable(false);
    }

    private void setDefaultSize() {
        smallSizeButton.setSelected(true);
        if (currentPizza != null) {
            currentPizza.setSize(Size.SMALL);
        }
    }

    private void handleSizeChange() {
        if (currentPizza != null) {
            if (smallSizeButton.isSelected()) currentPizza.setSize(Size.SMALL);
            if (mediumSizeButton.isSelected()) currentPizza.setSize(Size.MEDIUM);
            if (largeSizeButton.isSelected()) currentPizza.setSize(Size.LARGE);
            updatePizzaDetails();
        }
    }

    private void updatePizzaDetails() {
        if (currentPizza != null) {
            List<Topping> toppings = currentPizza.getToppings();
            if (toppings == null) {
                toppings = new ArrayList<>();
            }

            selectedToppingsList.getItems().setAll(toppings);
            pizzaPriceField.setText(String.format("%.2f", currentPizza.price()));
        }
    }

    @FXML
    private void handleAddTopping() {
        if (!(currentPizza instanceof BuildYourOwn)) return;

        Topping selectedTopping = availableToppingsList.getSelectionModel().getSelectedItem();
        if (selectedTopping != null && selectedToppingsList.getItems().size() < 7) {
            availableToppingsList.getItems().remove(selectedTopping);
            selectedToppingsList.getItems().add(selectedTopping);
            currentPizza.getToppings().add(selectedTopping);
            updatePizzaDetails();
        }
    }

    @FXML
    private void handleRemoveTopping() {
        if (!(currentPizza instanceof BuildYourOwn)) return;

        Topping selectedTopping = selectedToppingsList.getSelectionModel().getSelectedItem();
        if (selectedTopping != null) {
            selectedToppingsList.getItems().remove(selectedTopping);
            availableToppingsList.getItems().add(selectedTopping);
            currentPizza.getToppings().remove(selectedTopping);
            updatePizzaDetails();
        }
    }

    @FXML
    private void handleAddToOrder() {
        if (currentPizza == null) {
            showAlert("Error", "No Pizza Selected", "Please select a pizza type before adding to order.", Alert.AlertType.ERROR);
            return;
        }

        currentOrder.addPizza(currentPizza); // Add pizza to the shared order
        if (updateCallback != null) {
            updateCallback.run();
        }
        showAlert("Success", "Pizza Added", "The pizza has been added to your order.", Alert.AlertType.INFORMATION);

        resetPizzaSelection();
    }

    private void resetPizzaSelection() {
        pizzaTypeComboBox.getSelectionModel().clearSelection();
        selectedToppingsList.getItems().clear();
        availableToppingsList.getItems().clear();
        availableToppingsList.getItems().addAll(EnumSet.allOf(Topping.class));
        crustField.clear();
        pizzaPriceField.setText("0.00");
        currentPizza = null;
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
