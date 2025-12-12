package designPatterns.decorator.pizzabillingsystem;

public class PizzaBillingSystemTest {
    public static void main(String[] args) {

        Pizza pizza = new PlainPizza();
        System.out.println(pizza.desc());

        pizza = new CheeseDecorator(pizza);
        System.out.println(pizza.desc());
        pizza = new ChickenDecorator(pizza);
        System.out.println(pizza.desc());
    }
}

interface Pizza {
    String desc();
    double cost();
}

class PlainPizza implements Pizza {
    @Override
    public String desc() {
        return "base pizza";
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
        return pizza.desc()+", cheese";
    }

    @Override
    public double cost() {
        return pizza.cost() + 0.22;
    }
}

class ChickenDecorator extends PizzaDecorator {
    public ChickenDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String desc() {
        return pizza.desc()+", chicken";
    }

    @Override
    public double cost() {
        return pizza.cost() + 1.55;
    }
}