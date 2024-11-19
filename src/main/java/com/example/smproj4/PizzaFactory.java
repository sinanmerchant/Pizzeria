package com.example.smproj4;

/**
 * Interface for a Pizza Factory.
 * Provides methods to create different types of pizzas.
 * 
 * This interface is implemented by classes like {@code ChicagoPizza} and {@code NYPizza}
 * to create pizzas specific to their respective styles.
 * 
 * Methods include creating predefined pizza types such as Deluxe, BBQ Chicken, and Meatzza,
 * as well as Build Your Own pizzas for customization.
 * 
 * @author Sinan Merchant + Varun Bondugula
 */
public interface PizzaFactory {
    /**
     * Creates a Deluxe pizza.
     * The specific implementation depends on the style of the factory (e.g., Chicago or NY).
     * 
     * @return A {@code Pizza} object representing the Deluxe pizza.
     */
    Pizza createDeluxe();
    
    /**
     * Creates a BBQ Chicken pizza.
     * The specific implementation depends on the style of the factory (e.g., Chicago or NY).
     * 
     * @return A {@code Pizza} object representing the BBQ Chicken pizza.
     */
    Pizza createBBQChicken();
    
    /**
     * Creates a Meatzza pizza.
     * The specific implementation depends on the style of the factory (e.g., Chicago or NY).
     * 
     * @return A {@code Pizza} object representing the Meatzza pizza.
     */
    Pizza createMeatzza();
   
    /**
     * Creates a Build Your Own pizza.
     * The specific implementation depends on the style of the factory (e.g., Chicago or NY).
     * 
     * @return A {@code Pizza} object representing a customizable Build Your Own pizza.
     */
    Pizza createBuildYourOwn();
}

