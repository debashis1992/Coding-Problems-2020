package designPatterns.chainofresp;

import java.util.*;

public class RespTest {

    public static void main(String[] args) {

        DispenseHandler dispenseHandler50 = new DispenseHandlers(50, "fifty");
        DispenseHandler dispenseHandler20 = new DispenseHandlers(20, "twenty");
        DispenseHandler dispenseHandler5 = new DispenseHandlers(5, "five");
        DispenseHandler dispenseHandler1 = new DispenseHandlers(1, "one");

        List<DispenseHandler> dispenseHandlerList = new ArrayList<>();
        dispenseHandlerList.add(dispenseHandler50);
        dispenseHandlerList.add(dispenseHandler20);
        dispenseHandlerList.add(dispenseHandler5);
        dispenseHandlerList.add(dispenseHandler1);

        DispenseHandler finalDispenseHandler = DispenseHandlerFactory.createDispenseHandler(dispenseHandlerList);
        finalDispenseHandler.handle(223);

        dispenseHandlerList.sort(Comparator.comparingInt(DispenseHandler::getCurrency).reversed());
    }
}

interface DispenseHandler {
    int getCurrency();
    void handle(int amount);
    void setNextDispenseHandler(DispenseHandler dispenseHandler);
}

class DispenseHandlers implements DispenseHandler {
    private final int currency;
    private final String name;
    public DispenseHandlers(int currency, String name) {
        this.currency = currency;
        this.name = name;
    }
    private DispenseHandler nextDispenseHandler;

    public int getCurrency() {
        return currency;
    }

    @Override
    public void handle(int amount) {
        if(amount >= currency) {
            int notes = amount / currency;
            amount = amount % currency;
            System.out.println("dispensing notes : "+ notes +" at " + currency);
        }
        if(nextDispenseHandler !=null)
            nextDispenseHandler.handle(amount);
    }

    @Override
    public void setNextDispenseHandler(DispenseHandler dispenseHandler) {
        this.nextDispenseHandler = dispenseHandler;
    }

    public DispenseHandler getNextDispenseHandler() {
        return nextDispenseHandler;
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

