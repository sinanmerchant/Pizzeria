package com.example.smproj4;

import java.util.ArrayList;

public class Order {
    private int number;
    private ArrayList<Pizza> pizzas = new ArrayList<>();

    public Order(int number) {
        this.number = number;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    public double calculateTotal() {
        double total = 0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total;
    }

    public int getNumber() { return number; }
    public ArrayList<Pizza> getPizzas() { return pizzas; }
}
