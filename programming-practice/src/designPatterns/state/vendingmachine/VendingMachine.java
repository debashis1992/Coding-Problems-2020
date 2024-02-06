package designPatterns.state.vendingmachine;


import java.util.ArrayList;
import java.util.List;

class VendingMachine {
    int n;
    Item[][] items;
    VendingMachineState state;

    public VendingMachine(int n) {
        items = new Item[n][n];
        state = new SelectionState();
    }

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public void populateItems(List<List<Item>> itemsList) {
        for(int i=0;i<n;i++) {
            List<Item> it1 = itemsList.get(i);
            for(int j=0;j<n;j++)
                items[i][j] = it1.get(j);
        }
    }
}

