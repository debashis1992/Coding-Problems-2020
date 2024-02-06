package designPatterns.state.vendingmachine;

public interface VendingMachineState {
    void selectItems(VendingMachine machine);
    void proceedToPayment(VendingMachine machine);
    void makePayment(VendingMachine machine);
    void cancelOrder(VendingMachine machine);
}
