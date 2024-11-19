package com.example.smproj4;

/**
 * Represents a Deluxe Pizza.
 * This class extends the abstract {@code Pizza} class and defines specific behavior
 * for the Deluxe pizza type, including its price calculation and string representation.
 * 
 * The Deluxe pizza comes with a predefined set of toppings and is available in three sizes.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class Deluxe extends Pizza {
    /**
     * Calculates the price of the Deluxe pizza based on its size.
     * 
     * Prices:
     * - Small: $16.99
     * - Medium: $18.99
     * - Large: $20.99
     * 
     * @return The price of the Deluxe pizza.
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 16.99;
            case MEDIUM: return 18.99;
            case LARGE: return 20.99;
            default: return 0;
        }
    }

    /**
     * Generates a string representation of the Deluxe pizza.
     * Includes crust type, list of toppings, and the price.
     * 
     * @return A formatted string describing the Deluxe pizza.
     */
    @Override
    public String toString() {
        return String.format(
                "Deluxe (%s), %s, $%.2f",
                getCrust(),
                String.join(", ", getToppings().toString()),
                price()
        );
    }
}
