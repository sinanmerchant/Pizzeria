package com.example.smproj4;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the history of all orders placed in the pizzeria system.
 * Provides functionality to add, cancel, and retrieve orders.
 * 
 * The {@code OrderHistory} class maintains a list of all orders and allows
 * querying for specific orders by their unique order numbers.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public class OrderHistory {
    private List<Order> orders;

    /**
     * Constructs an empty {@code OrderHistory}.
     * Initializes the internal list for storing orders.
     */
    public OrderHistory() {
        orders = new ArrayList<>();
    }

    /**
     * Adds an order to the history.
     * 
     * @param order The order to add.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Cancels an order by removing it from the history.
     * 
     * @param order The order to cancel.
     */
    public void cancelOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Retrieves a list of all orders in the history.
     * Returns a copy of the internal list to maintain encapsulation.
     * 
     * @return A {@code List} of all orders.
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Retrieves an order by its unique order number.
     * If no matching order is found, returns {@code null}.
     * 
     * @param orderNumber The unique number of the order to retrieve.
     * @return The {@code Order} with the specified order number, or {@code null} if not found.
     */
    public Order getOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }
}

