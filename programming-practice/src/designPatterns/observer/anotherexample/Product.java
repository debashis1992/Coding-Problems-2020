package designPatterns.observer.anotherexample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Product implements Observable {

    int count;
    private ExecutorService service;
    public Product() {
        observerList = new ArrayList<>();
        service = Executors.newFixedThreadPool(10);
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
        observerList.forEach(ob -> service.submit(ob::getNotified));
    }
}



