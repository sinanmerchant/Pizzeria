package com.example.smproj4;

import java.util.ArrayList;

public abstract class Pizza {
    private ArrayList<Topping> toppings; // Enum for toppings
    private Crust crust;                // Enum for crusts
    private Size size;                  // Enum for sizes

    public abstract double price();     // Calculate the pizza price

    // Getters and setters
    public ArrayList<Topping> getToppings() { return toppings; }
    public void setToppings(ArrayList<Topping> toppings) { this.toppings = toppings; }
    public Crust getCrust() { return crust; }
    public void setCrust(Crust crust) { this.crust = crust; }
    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }
}