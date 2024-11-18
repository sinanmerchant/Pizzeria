package com.example.smproj4;

import java.util.ArrayList;
import java.util.List;

public class ChicagoPizza implements PizzaFactory {
    @Override
    public Pizza createDeluxe() {
        Pizza pizza = new Deluxe();
        pizza.setCrust(Crust.DEEP_DISH);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.SAUSAGE, Topping.PEPPERONI, Topping.MUSHROOM, Topping.ONION, Topping.GREEN_PEPPER
        )));
        return pizza;
    }

    @Override
    public Pizza createBBQChicken() {
        Pizza pizza = new BBQChicken();
        pizza.setCrust(Crust.PAN);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.BBQ_CHICKEN, Topping.ONION, Topping.GREEN_PEPPER, Topping.CHEDDAR
        )));
        return pizza;
    }

    @Override
    public Pizza createMeatzza() {
        Pizza pizza = new Meatzza();
        pizza.setCrust(Crust.STUFFED);
        pizza.setToppings(new ArrayList<>(List.of(
                Topping.SAUSAGE, Topping.PEPPERONI, Topping.HAM, Topping.BEEF
        )));
        return pizza;
    }

    @Override
    public Pizza createBuildYourOwn() {
        Pizza pizza = new BuildYourOwn();
        pizza.setCrust(Crust.PAN);
        return pizza;
    }
}
