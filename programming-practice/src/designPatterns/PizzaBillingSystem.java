package designPatterns;

import java.util.ArrayList;
import java.util.List;

public class PizzaBillingSystem {
    public static void main(String[] args) {
        PizzaFactory pizzaFactory = new PizzaFactory();
        PizzaStore store = new PizzaStore(pizzaFactory);


        store.order();
        store.pay();

    }
}

enum CrustTypes {
    THIN_CRUST(3), CHEESE_BURST(2), HAND_TOSSED(1);

    public final int price;

    CrustTypes(int price) {
        this.price = price;
    }
}

enum Size {
    REGULAR(2), MEDIUM(3), LARGE(5);

    public final int price;
    Size(int price) { this.price = price; }
}

enum PizzaType {
    VEG(1), NON_VEG(3);

    public final int price;
    PizzaType(int price) { this.price = price; }
}

class Topping {
    String type;
    int price;
    public Topping(String type, int price) {
        this.type = type;
        this.price = price;
    }
}

class PizzaFactory {
    Pizza pizza;

    public Pizza createPizza(CrustTypes crust, PizzaType type, Size size) {
        Pizza pizza = new Pizza();
        pizza.crust = crust;
        pizza.size = size;
        pizza.type = type;
        pizza.toppingList = new ArrayList<>();
        return pizza;
    }
}
class PizzaStore {
    PizzaFactory pizzaFactory;
    Pizza pizza;
    public PizzaStore(PizzaFactory pizzaFactory) {
        this.pizzaFactory=pizzaFactory;
    }

    public void order() {
        pizza = pizzaFactory.createPizza(CrustTypes.THIN_CRUST, PizzaType.VEG, Size.LARGE);

        pizza.toppingList.add(new Topping("onions",2));

    }
    public void pay() {
        System.out.println("pizza price: "+pizza.getPrice());
    }
}

class Pizza {
    CrustTypes crust;
    Size size;
    PizzaType type;
    List<Topping> toppingList;
    private int price;

    public void addTopping(Topping topping) {
        if(toppingList!=null)
            toppingList.add(topping);
    }

    public void removeTopping(Topping topping) {
        if(toppingList!=null)
            toppingList.remove(topping);
    }

    public final int getPrice() {
        this.price = 0;
        toppingList.forEach(topping -> this.price+= topping.price);
        this.price+= crust.price;
        this.price+= size.price;
        this.price+= type.price;
        return this.price;
    }
}