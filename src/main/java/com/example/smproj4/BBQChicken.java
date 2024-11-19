package com.example.smproj4;

/**
 * Represents a BBQ Chicken Pizza.
 * This class extends the abstract {@code Pizza} class and defines
 * specific behavior for the BBQ Chicken pizza type, including pricing and string representation.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */

 public class BBQChicken extends Pizza {
    /**
     * Calculates the price of the BBQ Chicken pizza based on its size.
     * The price varies for Small, Medium, and Large sizes.
     * 
     * @return The price of the BBQ Chicken pizza.
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 14.99;
            case MEDIUM:
                return 16.99;
            case LARGE:
                return 19.99;
            default:
                return 0;
        }
    }

    /**
     * Generates a string representation of the BBQ Chicken pizza.
     * Includes crust type, list of toppings, and the price.
     * 
     * @return A formatted string describing the BBQ Chicken pizza.
     */
    @Override
    public String toString() {
        return String.format(
                "BBQ Chicken (%s), %s, %.2f",
                getCrust(),
                String.join(", ", getToppings().toString()),
                price()
        );
    }
}
