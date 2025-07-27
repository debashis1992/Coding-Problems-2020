package designPatterns.vendingmachine;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class CoffeeVendingMachineTest {
    public static void main(String[] args) {

        CoffeeVendingMachine coffeeVendingMachine = CoffeeVendingMachine.getInstance();
        CoffeeRecipe coffeeRecipe = coffeeVendingMachine.createOrder("debashis",
                List.of("latte"),
                List.of( "milk", "sugar"));

        coffeeVendingMachine.dispenseCoffee(coffeeRecipe);
        coffeeVendingMachine.pay(coffeeRecipe);
    }
}

class Pricing {
    private final Map<String, Double> coffeePrice;
    private final Map<String, Double> ingredientPrice;
    public Pricing() {
        coffeePrice = new HashMap<>();
        coffeePrice.put("espresso", 0.5);
        coffeePrice.put("cappuccino", 1.4);
        coffeePrice.put("latte", 1.6);

        ingredientPrice = new HashMap<>();
        ingredientPrice.put("milk", 0.2);
        ingredientPrice.put("sugar", 0.1);
        ingredientPrice.put("chocolate", 2.0);
    }

    public Map<String, Double> getCoffeePrice() {
        return coffeePrice;
    }

    public Map<String, Double> getIngredientPrice() {
        return ingredientPrice;
    }

    public Double getCoffeePriceFor(String coffee) {
        return this.coffeePrice.getOrDefault(coffee, 0.0);
    }
    public Double getIngredientPriceFor(String in) {
        return this.ingredientPrice.getOrDefault(in, 0.0);
    }

}

class IngredientsStore {
    private final Map<String, Integer> inventory;
    public IngredientsStore() {
        this.inventory = Map.of(
                "milk", 5,
                "sugar", 10,
                "chocolate", 20
        );
    }

    public synchronized Map<String, Integer> getAllInventory() {
        return inventory;
    }

    public synchronized void refill() {
        //perform refill logic
    }
    public synchronized void use(String ingredient, int quantity) {
        int current = inventory.getOrDefault(ingredient,0);
        if(current < quantity)
            throw new InventoryNotAvailableException("inventory not adequate for :" + ingredient);
    }

    public synchronized Integer getCurrentQuantity(String ingredient) {
        return inventory.getOrDefault(ingredient, 0);
    }
}

class InventoryNotAvailableException extends RuntimeException {
    public InventoryNotAvailableException(String message) {
        super(message);
    }
}

class CoffeeRecipe {
    private final String orderId;
    private final String name;
    private final double price;
    private final List<String> ingredients;

    public CoffeeRecipe(String name, double price, List<String> ingredients) {
        this.orderId = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}


class CoffeeVendingMachine {
    private static volatile CoffeeVendingMachine instance;

    private final Pricing pricingEngine;
    private final IngredientsStore ingredientsStore;
    private final Dispenser dispenser;
    private final PaymentProcessor paymentProcessor;

    private CoffeeVendingMachine() {
        this.pricingEngine = new Pricing();
        this.ingredientsStore = new IngredientsStore();
        this.dispenser = new Dispenser();
        this.paymentProcessor = new PaymentProcessor();
    }
    public static CoffeeVendingMachine getInstance() {
        if(instance == null) {
            synchronized (CoffeeVendingMachine.class) {
                if(instance == null)
                    instance = new CoffeeVendingMachine();
            }
        }
        return instance;
    }

    public CoffeeRecipe createOrder(String name, List<String> coffeeOrder, List<String> ingredients) {

        // get prices for coffee
        // get list of ingredients from store
        double totalCost = 0.0;
        for(String coffee: coffeeOrder) {
            totalCost+= pricingEngine.getCoffeePriceFor(coffee);
        }

        for(String in: ingredients) {
            totalCost+= pricingEngine.getIngredientPriceFor(in);
            ingredientsStore.use(in, 1);
        }
        // create order
        System.out.println("created order for name: "+name);
        return new CoffeeRecipe(name, totalCost, ingredients);

    }

    public void dispenseCoffee(CoffeeRecipe coffeeRecipe) {
        dispenser.dispense(coffeeRecipe);
    }

    public void pay(CoffeeRecipe coffeeRecipe) {
        paymentProcessor.payment(coffeeRecipe);
    }

}

class Dispenser {
    public void dispense(CoffeeRecipe coffeeRecipe) {
        System.out.println("dispensing coffee for person: "+coffeeRecipe.getName());
    }
}

class PaymentProcessor {
    public void payment(CoffeeRecipe coffeeRecipe) {
        System.out.println("need payment for name: "+coffeeRecipe.getName()+", with amount: "+coffeeRecipe.getPrice());
    }
}
