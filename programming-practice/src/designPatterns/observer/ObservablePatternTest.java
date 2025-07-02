package designPatterns.observer;

import java.util.*;

public class ObservablePatternTest {
    public static void main(String[] args) {

        User user1 = new User(1);
        User user2 = new User(2);
        User user3 = new User(3);

        SubscriptionManager manager = new SubscriptionManager();
        manager.subscribe("open", user1);
        manager.subscribe("open", user2);
        manager.subscribe("open", user3);

        File file1 = new File("test.txt", manager);
        file1.openFile();

        manager.unsubscribe("open", user2);
        file1.openFile();

        System.out.println("---Print the event log----");
        EventLogger eventLogger = EventLogger.createOrGetEventLogger();
        eventLogger.getEventList().forEach(event -> System.out.println(event.toString()));

    }
}



class User {
    int id;

    public User(int id) {
        this.id = id;
    }

    public void update() {
        System.out.println("User with id: "+id+", got notified...");
    }
}

class SubscriptionManager {
    private Map<String, List<User>> listeners;

    public SubscriptionManager() {
        this.listeners = new HashMap<>();
    }

    public void subscribe(String eventType, User user) {
        List<User> userList = this.listeners.getOrDefault(eventType, new ArrayList<>());
        userList.add(user);
        this.listeners.put(eventType, userList);
    }

    public void unsubscribe(String eventType, User user) {
        List<User> userList = this.listeners.getOrDefault(eventType, new ArrayList<>());
        userList.remove(user);
        this.listeners.put(eventType, userList);
    }

    public void update(String eventType) {
        this.listeners.get(eventType).forEach(User::update);
    }

    public Map<String, List<User>> getListeners() {
        return new HashMap<>(listeners);
    }
}

class File {
    private final String name;
    private final SubscriptionManager subscriptionManager;

    public File(String name) {
        this.name=name;
        this.subscriptionManager=new SubscriptionManager();
    }

    public File(String name, SubscriptionManager subscriptionManager) {
        this.name=name;
        this.subscriptionManager=subscriptionManager;
    }

    public String getName() {
        return name;
    }

    public SubscriptionManager getSubscriptionManager() {
        return subscriptionManager;
    }

    public void openFile() {
        String type = "open";
        subscriptionManager.update(type);
        Event event = new Event(type);
        event.logEvent();
    }

    public void saveFile() {
        String type = "save";
        subscriptionManager.update(type);
        Event event = new Event(type);
        event.logEvent();
    }
}

class Event {
    String id;
    String type;
    Date triggerDateTime;
    EventLogger eventLogger;

    public Event(String type) {
        this.type=type;
        this.id=UUID.randomUUID().toString();
        this.triggerDateTime = new Date();
        this.eventLogger = EventLogger.createOrGetEventLogger();
    }

    public void logEvent() {
        eventLogger.logEvent(this);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", triggerDateTime=" + triggerDateTime +
                '}';
    }
}

class EventLogger {
    private static List<Event> eventList;
    private static EventLogger eventLogger;
    private EventLogger() {}

    public static EventLogger createOrGetEventLogger() {
        if(eventLogger == null) {
            eventLogger = new EventLogger();
            eventList = new ArrayList<>();
        }
        return eventLogger;
    }

    public void logEvent(Event event) {
        eventList.add(event);
    }

    public List<Event> getEventList() {
        return new ArrayList<>(eventList);
    }
}


