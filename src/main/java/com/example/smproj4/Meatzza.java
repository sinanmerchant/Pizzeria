package com.example.smproj4;

/**
 * Represents a Meatzza Pizza.
 * This class extends the abstract {@code Pizza} class and defines specific behavior
 * for the Meatzza pizza type, including its price calculation and string representation.
 * 
 * The Meatzza pizza comes with a predefined set of toppings and is available in three sizes.
 * 
 * Prices:
 * - Small: $17.99
 * - Medium: $19.99
 * - Large: $21.99
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class Meatzza extends Pizza {
    /**
     * Calculates the price of the Meatzza pizza based on its size.
     * 
     * Prices:
     * - Small: $17.99
     * - Medium: $19.99
     * - Large: $21.99
     * 
     * @return The price of the Meatzza pizza.
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 17.99;
            case MEDIUM: return 19.99;
            case LARGE: return 21.99;
            default: return 0;
        }
    }

    /**
     * Generates a string representation of the Meatzza pizza.
     * Includes the crust type, list of toppings, and the total price.
     * 
     * @return A formatted string describing the Meatzza pizza.
     */
    @Override
    public String toString() {
        return String.format(
                "Meatzza (%s), %s, $%.2f",
                getCrust(),
                String.join(", ", getToppings().toString()),
                price()
        );
    }
}