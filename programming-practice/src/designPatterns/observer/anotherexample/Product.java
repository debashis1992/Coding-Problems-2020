package designPatterns.observer.anotherexample;

import java.util.ArrayList;
import java.util.List;

public class Product implements Observable {

    int count;
    public Product() {
        observerList = new ArrayList<>();
    }

    List<Observer> observerList;

    public void setCount(int count) {
        this.count = count;
        if(count > 0)
            notifyConsumers();
    }

    @Override
    public void addConsumers(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeConsumer(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyConsumers() {
        observerList.forEach(ob -> ob.getNotified());
    }
}



