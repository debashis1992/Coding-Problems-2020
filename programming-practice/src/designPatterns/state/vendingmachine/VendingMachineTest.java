package designPatterns.state.vendingmachine;


public class VendingMachineTest {
    public static void main(String[] args) {

        VendingMachine vendingMachine = new VendingMachine(3);
        VendingMachineState currentState = vendingMachine.state;

        currentState.selectItems(vendingMachine);
        currentState = vendingMachine.state;

        currentState.cancelOrder(vendingMachine);
        currentState = vendingMachine.state;

        System.out.println(currentState);

    }
}

class Item {
    public String name;
    public int price;
    public int[] code;

    public Item(String name, int price, int[] code) {
        this.name=name;
        this.price=price;
        this.code=code;
    }
}