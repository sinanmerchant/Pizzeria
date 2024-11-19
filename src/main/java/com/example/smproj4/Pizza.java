package com.example.smproj4;

import java.util.ArrayList;

/**
 * Abstract base class representing a pizza.
 * Defines common properties and behavior for all types of pizzas, such as toppings, crust, and size.
 * 
 * Subclasses must implement the {@code price()} method to calculate the price of the pizza
 * based on its specific type and properties.
 * 
 * Attributes:
 * - {@code toppings}: A list of toppings for the pizza.
 * - {@code crust}: The type of crust for the pizza.
 * - {@code size}: The size of the pizza (Small, Medium, or Large).
 * 
 * Enums {@code Topping}, {@code Crust}, and {@code Size} are used for these attributes.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings; // Enum for toppings
    private Crust crust;                // Enum for crusts
    private Size size;                  // Enum for sizes

    /**
     * Abstract method to calculate the price of the pizza.
     * Must be implemented by subclasses to provide specific price calculations.
     * 
     * @return The price of the pizza.
     */
    public abstract double price();     // Calculate the pizza price

    // Getters and setters

    /**
     * Retrieves the list of toppings on the pizza.
     * 
     * @return An {@code ArrayList} of {@code Topping} objects.
     */
    public ArrayList<Topping> getToppings() { return toppings; }
    
    /**
     * Sets the list of toppings for the pizza.
     * 
     * @param toppings An {@code ArrayList} of {@code Topping} objects to set.
     */
    public void setToppings(ArrayList<Topping> toppings) { this.toppings = toppings; }
    
    /**
     * Retrieves the crust type of the pizza.
     * 
     * @return The {@code Crust} of the pizza.
     */
    public Crust getCrust() { return crust; }
    
    /**
     * Sets the crust type for the pizza.
     * 
     * @param crust The {@code Crust} to set for the pizza.
     */
    public void setCrust(Crust crust) { this.crust = crust; }
   
   /**
     * Retrieves the size of the pizza.
     * 
     * @return The {@code Size} of the pizza.
     */
    public Size getSize() { return size; }
    
    /**
     * Sets the size of the pizza.
     * 
     * @param size The {@code Size} to set for the pizza.
     */
    public void setSize(Size size) { this.size = size; }
}