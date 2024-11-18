package com.example.smproj4;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BuildYourOwnTest {
    private static final double DECIMAL_LENGTH = 0.01;

    /**
     * Test case 1: Small pizza with no toppings.
     */
    @Test
    public void testPrice_Small_NoToppings() {
        BuildYourOwn pizza = new BuildYourOwn();
        pizza.setSize(Size.SMALL);
        pizza.setCrust(Crust.PAN);

        double expectedPrice = 8.99;
        assertEquals(expectedPrice, pizza.price(), DECIMAL_LENGTH);
    }

    /**
     * Test case 2: Medium pizza with 3 toppings.
     */
    @Test
    public void testPrice_Medium_ThreeToppings() {
        BuildYourOwn pizza = new BuildYourOwn();
        pizza.setSize(Size.MEDIUM);
        pizza.setCrust(Crust.PAN);

        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.MUSHROOM);
        toppings.add(Topping.ONION);
        pizza.setToppings(toppings);

        double expectedPrice = 10.99 + (3 * 1.69);
        assertEquals(expectedPrice, pizza.price(), DECIMAL_LENGTH);
    }

    /**
     * Test case 3: Large pizza with maximum toppings (7 toppings).
     */
    @Test
    public void testPrice_Large_MaxToppings() {
        BuildYourOwn pizza = new BuildYourOwn();
        pizza.setSize(Size.LARGE);
        pizza.setCrust(Crust.PAN);

        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.HAM);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.ONION);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.MUSHROOM);
        pizza.setToppings(toppings);

        double expectedPrice = 12.99 + (7 * 1.69);
        assertEquals(expectedPrice, pizza.price(), DECIMAL_LENGTH);
    }

    /**
     * Test case 4: Small pizza with maximum toppings (7 toppings).
     */
    @Test
    public void testPrice_Small_MaxToppings() {
        BuildYourOwn pizza = new BuildYourOwn();
        pizza.setSize(Size.SMALL);
        pizza.setCrust(Crust.PAN);

        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.HAM);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.ONION);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.MUSHROOM);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.SAUSAGE);
        pizza.setToppings(toppings);

        double expectedPrice = 8.99 + (7 * 1.69);
        assertEquals(expectedPrice, pizza.price(), DECIMAL_LENGTH);
    }

    /**
     * Test case 5: Large pizza with no toppings.
     */
    @Test
    public void testPrice_Large_NoToppings() {
        BuildYourOwn pizza = new BuildYourOwn();
        pizza.setSize(Size.LARGE);
        pizza.setCrust(Crust.PAN);

        double expectedPrice = 12.99;
        assertEquals(expectedPrice, pizza.price(), DECIMAL_LENGTH);
    }
}
