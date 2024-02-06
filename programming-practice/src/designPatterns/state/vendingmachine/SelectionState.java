package designPatterns.state.vendingmachine;

public class SelectionState implements VendingMachineState {
    @Override
    public void selectItems(VendingMachine machine) {
        System.out.println("added items");
    }

    @Override
    public void proceedToPayment(VendingMachine machine) {
        System.out.println("proceeding to payment");
    }

    @Override
    public void makePayment(VendingMachine machine) {
        System.out.println("make payment is not possible at this stage");
    }

    @Override
    public void cancelOrder(VendingMachine machine) {
        System.out.println("cancelling order");
        machine.setState(new SelectionState());
    }
}
