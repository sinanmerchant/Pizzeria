package com.example.smproj4;

public class Deluxe extends Pizza {
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 16.99;
            case MEDIUM: return 18.99;
            case LARGE: return 20.99;
            default: return 0;
        }
    }
}