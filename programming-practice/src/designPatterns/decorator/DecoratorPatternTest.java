package designPatterns.decorator;

public class DecoratorPatternTest {
    public static void main(String[] args) {

        Order baseCoffee = new BaseCoffee(new Order());
        System.out.println(baseCoffee.cost());

        Order baseCoffeeWithMilk = new Milk(new BaseCoffee(new Order()));
        System.out.println(baseCoffeeWithMilk.cost());

        Order baseCoffeeWithMilkWithCream = new Cream(new Milk(new BaseCoffee(new Order())));
        System.out.println(baseCoffeeWithMilkWithCream.cost());
    }
}

class Order {
    public Order() {}
    public int cost() { return 0; }
}

class BaseCoffee extends Order {
    Order order;
    public BaseCoffee(Order order) {
        this.order=order;
    }
    @Override
    public int cost() {
        return order.cost()+10;
    }
}

class Milk extends Order {
    Order order;
    public Milk(Order order) {
        this.order = order;
    }
    @Override
    public int cost() {
        return order.cost()+2;
    }
}

class Cream extends Order {
    Order order;
    public Cream(Order order) {
        this.order=order;
    }
    @Override
    public int cost() {
        return order.cost()+3;
    }
}

class Decaf extends Order {
    Order order;
    public Decaf(Order order) {
        this.order=order;
    }

    @Override
    public int cost() {
        return order.cost()+1;
    }
}