package com.example.smproj4;

public class Meatzza extends Pizza {
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 17.99;
            case MEDIUM: return 19.99;
            case LARGE: return 21.99;
            default: return 0;
        }
    }

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