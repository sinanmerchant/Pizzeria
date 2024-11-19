package com.example.smproj4;

import java.util.ArrayList;

/**
 * Represents a "Build Your Own" pizza.
 * This class extends the abstract {@code Pizza} class and allows for a customizable
 * pizza with a base price and additional cost for each topping.
 * 
 * Each topping costs $1.69, and the base price depends on the size of the pizza.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class BuildYourOwn extends Pizza {
    private static final double TOPPING_PRICE = 1.69;


    /**
     * Constructor for a Build Your Own pizza.
     * Initializes an empty list of toppings.
     */
    public BuildYourOwn() {
        setToppings(new ArrayList<>());
    }

    /**
     * Calculates the price of the Build Your Own pizza.
     * The price is determined by the size of the pizza and the number of toppings added.
     * 
     * @return The total price of the Build Your Own pizza.
     */
    @Override
    public double price() {
        double basePrice = 0;
        switch (getSize()) {
            case SMALL: basePrice = 8.99; break;
            case MEDIUM: basePrice = 10.99; break;
            case LARGE: basePrice = 12.99; break;
        }
        return basePrice + (getToppings().size() * TOPPING_PRICE);
    }

    /**
     * Generates a string representation of the Build Your Own pizza.
     * Includes the crust type, list of toppings, and the total price.
     * 
     * @return A formatted string describing the Build Your Own pizza.
     */
    @Override
    public String toString() {
        return String.format(
                "Build Your Own (%s), %s, $%.2f",
                getCrust(),
                String.join(", ", getToppings().toString()),
                price()
        );
    }
}
