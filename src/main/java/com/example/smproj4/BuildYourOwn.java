package com.example.smproj4;

public class BuildYourOwn extends Pizza {
    private static final double TOPPING_PRICE = 1.69;

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
}
