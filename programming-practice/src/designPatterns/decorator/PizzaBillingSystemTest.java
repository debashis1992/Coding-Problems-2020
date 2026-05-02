package designPatterns.decorator;

public class PizzaBillingSystemTest {
    public static void main(String[] args) {

        BasePizza basePizza = new BasePizza();
        Pizza cheesePizza = new CheeseDecorator(basePizza);
        System.out.println(cheesePizza.desc());

        Pizza chickenPizza = new ChickenDecorator(basePizza);
        System.out.println(chickenPizza.desc());

        Pizza chickenCheesePizza = new ChickenDecorator(new CheeseDecorator(basePizza));
        System.out.println(chickenCheesePizza.desc());
    }
}
interface Pizza {
    String desc();
    double cost();
}

class BasePizza implements Pizza {
    @Override
    public String desc() {
        return "plain pizza";
    }

    @Override
    public double cost() {
        return 0.5;
    }
}

abstract class PizzaDecorator implements Pizza {
    protected final Pizza pizza;
    public PizzaDecorator(Pizza pizza) {
        this.pizza=pizza;
    }
}

class CheeseDecorator extends PizzaDecorator {
    public CheeseDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String desc() {
        return pizza.desc() + ", cheese";
    }

    @Override
    public double cost() {
        return pizza.cost() + 1.5;
    }
}

class ChickenDecorator extends PizzaDecorator {
    public ChickenDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String desc() {
        return pizza.desc() + ", chicken";
    }

    @Override
    public double cost() {
        return pizza.cost() + 2.0;
    }
}