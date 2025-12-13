package designPatterns.template;

public class TemplatePatternTest {
    public static void main(String[] args) {

        BeverageMaker coffeeMaker = new CoffeeMaker();
        coffeeMaker.makeBeverage();

        BeverageMaker teaMaker = new TeaMaker();
        teaMaker.makeBeverage();


    }
}

abstract class BeverageMaker {
    public final void makeBeverage() {
        boilWater();
        addIngredients();
        make();
        addCondiments();
        serve();
    }

    private void boilWater() {
        System.out.println("boiling water");
    }

    private void serve() {
        System.out.println("beverage is served");
    }

    abstract protected void addIngredients();
    abstract protected void make();
    abstract protected void addCondiments();
}

class CoffeeMaker extends BeverageMaker {
    @Override
    public void addIngredients() {
        System.out.println("adding coffee & milk");
    }
    @Override
    public void make() {
        System.out.println("brewing coffee");
    }
    @Override
    public void addCondiments() {
        System.out.println("adding syrup & sugar");
    }
}

class TeaMaker extends BeverageMaker {
    @Override
    public void addIngredients() {
        System.out.println("adding tea bag");
    }
    @Override
    public void make() {
        System.out.println("making tea");
    }
    @Override
    public void addCondiments() {
        System.out.println("adding just sugar");
    }
}