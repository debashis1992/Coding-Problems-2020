package designPatterns.state.vendingmachine;

public class PaymentState implements VendingMachineState {

    @Override
    public void selectItems(VendingMachine machine) {
        System.out.println("cannot add new items at this state");
    }

    @Override
    public void proceedToPayment(VendingMachine machine) {
        System.out.println("already in payment state");
    }

    @Override
    public void makePayment(VendingMachine machine) {
        System.out.println("payment made");
        machine.setState(new FulfilmentState());
    }

    @Override
    public void cancelOrder(VendingMachine machine) {
        System.out.println("refunding amount");
        machine.setState(new SelectionState());
    }
}
