package com.example.smproj4;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating New York-style pizzas.
 * Implements the {@code PizzaFactory} interface and provides specific implementations
 * for creating Deluxe, BBQ Chicken, Meatzza, and Build Your Own pizzas with New York-style crusts.
 * 
 * This class follows the Abstract Factory design pattern.
 * 
 * New York-style crust options:
 * - Deluxe: Brooklyn
 * - BBQ Chicken: Thin
 * - Meatzza: Hand Tossed
 * - Build Your Own: Hand Tossed
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class NYPizza implements PizzaFactory {
    /**
     * Creates a New York-style Deluxe pizza.
     * Sets the crust to Brooklyn and includes predefined toppings.
     * 
     * @return A New York-style Deluxe pizza.
     */
    @Override
    public Pizza createDeluxe() {
        Pizza pizza = new Deluxe();
        pizza.setCrust(Crust.BROOKLYN);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.SAUSAGE, Topping.PEPPERONI, Topping.MUSHROOM, Topping.ONION, Topping.GREEN_PEPPER
        )));
        return pizza;
    }

    /**
     * Creates a New York-style BBQ Chicken pizza.
     * Sets the crust to Thin and includes predefined toppings.
     * 
     * @return A New York-style BBQ Chicken pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        Pizza pizza = new BBQChicken();
        pizza.setCrust(Crust.THIN);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.BBQ_CHICKEN, Topping.ONION, Topping.GREEN_PEPPER, Topping.CHEDDAR
        )));
        return pizza;
    }

    /**
     * Creates a New York-style Meatzza pizza.
     * Sets the crust to Hand Tossed and includes predefined toppings.
     * 
     * @return A New York-style Meatzza pizza.
     */
    @Override
    public Pizza createMeatzza() {
        Pizza pizza = new Meatzza();
        pizza.setCrust(Crust.HAND_TOSSED);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.SAUSAGE, Topping.PEPPERONI, Topping.HAM, Topping.BEEF
        )));
        return pizza;
    }

    /**
     * Creates a New York-style Build Your Own pizza.
     * Sets the crust to Hand Tossed and initializes it with no toppings.
     * 
     * @return A New York-style Build Your Own pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        Pizza pizza = new BuildYourOwn();
        pizza.setCrust(Crust.HAND_TOSSED);
        return pizza;
    }
}
