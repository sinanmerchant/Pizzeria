package com.example.smproj4;

import java.util.ArrayList;

/**
 * Represents an order in the pizzeria system.
 * Each order contains a unique order number and a list of pizzas.
 * 
 * Provides functionality for adding and removing pizzas, as well as calculating
 * the total price of the order.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class Order {
    private int number;
    private ArrayList<Pizza> pizzas = new ArrayList<>();

    /**
     * Constructs an Order with the specified order number.
     * 
     * @param number The unique order number.
     */
    public Order(int number) {
        this.number = number;
    }

    /**
     * Adds a pizza to the order.
     * 
     * @param pizza The pizza to add.
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Removes a pizza from the order.
     * 
     * @param pizza The pizza to remove.
     */
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Calculates the total price of all pizzas in the order.
     * 
     * @return The total price of the order.
     */
    public double calculateTotal() {
        double total = 0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total;
    }

    /**
     * Retrieves the unique order number.
     * 
     * @return The order number.
     */
    public int getNumber() { return number; }

    /**
     * Retrieves the list of pizzas in the order.
     * 
     * @return An {@code ArrayList} of pizzas in the order.
     */
    public ArrayList<Pizza> getPizzas() { return pizzas; }
}
