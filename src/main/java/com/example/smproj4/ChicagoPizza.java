package com.example.smproj4;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating Chicago-style pizzas.
 * Implements the {@code PizzaFactory} interface and provides specific implementations
 * for creating Deluxe, BBQ Chicken, Meatzza, and Build Your Own pizzas with Chicago-style crusts.
 * 
 * This class follows the Abstract Factory design pattern.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * Creates a Chicago-style Deluxe pizza.
     * Sets the crust to Deep Dish and includes predefined toppings.
     * 
     * @return A Chicago-style Deluxe pizza.
     */
    @Override
    public Pizza createDeluxe() {
        Pizza pizza = new Deluxe();
        pizza.setCrust(Crust.DEEP_DISH);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.SAUSAGE, Topping.PEPPERONI, Topping.MUSHROOM, Topping.ONION, Topping.GREEN_PEPPER
        )));
        return pizza;
    }

    /**
     * Creates a Chicago-style BBQ Chicken pizza.
     * Sets the crust to Pan and includes predefined toppings.
     * 
     * @return A Chicago-style BBQ Chicken pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        Pizza pizza = new BBQChicken();
        pizza.setCrust(Crust.PAN);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.BBQ_CHICKEN, Topping.ONION, Topping.GREEN_PEPPER, Topping.CHEDDAR
        )));
        return pizza;
    }

    /**
     * Creates a Chicago-style Meatzza pizza.
     * Sets the crust to Stuffed and includes predefined toppings.
     * 
     * @return A Chicago-style Meatzza pizza.
     */
    @Override
    public Pizza createMeatzza() {
        Pizza pizza = new Meatzza();
        pizza.setCrust(Crust.STUFFED);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.SAUSAGE, Topping.PEPPERONI, Topping.HAM, Topping.BEEF
        )));
        return pizza;
    }

    /**
     * Creates a Chicago-style Build Your Own pizza.
     * Sets the crust to Pan and initializes it with no toppings.
     * 
     * @return A Chicago-style Build Your Own pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        Pizza pizza = new BuildYourOwn();
        pizza.setCrust(Crust.PAN);
        return pizza;
    }
}
