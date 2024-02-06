package designPatterns.observer.anotherexample;


import java.util.UUID;

public class User implements Observer {
    String id;
    public User() {
        id = UUID.randomUUID().toString();
    }
    @Override
    public void getNotified() {
        System.out.println("i got notified with id: "+id);
    }
}

