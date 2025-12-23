package designPatterns.solid;

public class LiskovTest {

    public static void main(String[] args) {
        CoffeeMachine1 machine1 = new CoffeeMachine1();
        CoffeeMachine2 machine2 = new CoffeeMachine2();
        CoffeeShop coffeeShop = new CoffeeShop(machine2);
        coffeeShop.getCoffee();
    }
}

class CoffeeShop {
    private final CoffeeMaker coffeeMaker;
    public CoffeeShop(CoffeeMaker coffeeMaker) {
        this.coffeeMaker = coffeeMaker;
    }

    public void getCoffee() {
        coffeeMaker.makeCoffee();
    }
}

interface CoffeeMaker {
    void makeCoffee();
}

class CoffeeMachine1 implements CoffeeMaker {
    @Override
    public void makeCoffee() {
        System.out.println("making coffee from machine 1...");
    }
}

class CoffeeMachine2 implements CoffeeMaker {
    @Override
    public void makeCoffee() {
        System.out.println("making coffee from machine 1...");
    }
}