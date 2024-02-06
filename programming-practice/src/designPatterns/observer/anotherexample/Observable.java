package designPatterns.observer.anotherexample;


public interface Observable {
    void addConsumers(Observer observer);
    void removeConsumer(Observer observer);
    void notifyConsumers();
}
