package designPatterns.chainofresp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RespTest {

    public static void main(String[] args) {

        DispenseHandler dispenseHandler50 = new DispenseHandlers(50, "fifty");
        DispenseHandler dispenseHandler20 = new DispenseHandlers(20, "twenty");

        List<DispenseHandler> dispenseHandlerList = new ArrayList<>();
        dispenseHandlerList.add(dispenseHandler50);
        dispenseHandlerList.add(dispenseHandler20);

        DispenseHandler finalDispenseHandler = DispenseHandlerFactory.createDispenseHandler(dispenseHandlerList);
        finalDispenseHandler.handle(223);
    }
}

interface DispenseHandler {
    void handle(int amount);
    void setNextDispenseHandler(DispenseHandler dispenseHandler);
}

class DispenseHandlers implements DispenseHandler {
    private int currency;
    private String name;
    public DispenseHandlers(int currency, String name) {
        this.currency = currency;
        this.name = name;
    }
    private DispenseHandler dispenseHandler;

    @Override
    public void handle(int amount) {
        if(amount >= currency) {
            int notes = amount / currency;
            amount = amount % currency;
            System.out.println("dispensing notes : "+ notes +" at " + currency);
        }
        if(dispenseHandler!=null)
            dispenseHandler.handle(amount);
    }

    @Override
    public void setNextDispenseHandler(DispenseHandler dispenseHandler) {
        this.dispenseHandler = dispenseHandler;
    }

//    @Override
    public DispenseHandler getNextDispenseHandler() {
        return dispenseHandler;
    }
}

class DispenseHandlerFactory {
    public static DispenseHandler createDispenseHandler(List<DispenseHandler> dispenseHandlerList) {
        if(dispenseHandlerList==null || dispenseHandlerList.isEmpty())
            return null;

        DispenseHandler head = null, curr = null, next = null;
        for(int i=0;i+1<dispenseHandlerList.size();i++) {
            if(head == null) {
                head = dispenseHandlerList.get(i);
            }
            curr = dispenseHandlerList.get(i);
            next = dispenseHandlerList.get(i+1);
            curr.setNextDispenseHandler(next);
        }
        return head;
    }
}

