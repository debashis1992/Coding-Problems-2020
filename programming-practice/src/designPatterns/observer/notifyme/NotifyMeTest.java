package designPatterns.observer.notifyme;

import java.util.*;

public class NotifyMeTest {
    public static void main(String[] args) {

        User user1 = new User("d22@gmail.com");
        User user2 = new User( "d43242@gmail.com");
        User user3 = new User( "23432@gmail.com");

        SubscriptionManager subscriptionManager = new SubscriptionManager();
        subscriptionManager.subscribe(user1);
        subscriptionManager.subscribe(user2);
        subscriptionManager.subscribe(user3);

        subscriptionManager.update();

        subscriptionManager.unsubscribe(user2);

        subscriptionManager.update();

    }
}

interface NotifyObservable {
    boolean subscribe(User user);
    boolean unsubscribe(User user);
    void update();
    boolean unsubscribeAll();
}
class SubscriptionManager implements NotifyObservable {
    private final Set<User> subscriptions;
    public SubscriptionManager() {
        this.subscriptions = new HashSet<>();

    }

    @Override
    public synchronized boolean subscribe(User user) {
        if(user!=null)
            return subscriptions.add(user);
        return false;
    }

    @Override
    public synchronized boolean unsubscribe(User user) {
        if(user!=null)
            return subscriptions.removeIf(u -> u.equals(user));

        return false;
    }

    @Override
    public void update() {
        subscriptions.forEach(User::update);
    }

    @Override
    public synchronized boolean unsubscribeAll() {
        if(!subscriptions.isEmpty()) {
            subscriptions.clear();
            return true;
        }
        return false;
    }
}


interface ButtonObserver {
    void update();
}

class User implements ButtonObserver {
    private final String id;
    private final String email;

    public User(String email) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void update() {
        System.out.println("User notified with:  "+this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}