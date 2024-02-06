package designPatterns.singleton;

public class SingletonPatternTest {
}

class ThreadUnsafeSingleton {
    private static ThreadUnsafeSingleton instance;
    private ThreadUnsafeSingleton() {}

    public static ThreadUnsafeSingleton getInstance() {
        if(instance == null) {
            instance = new ThreadUnsafeSingleton();
        }
        return instance;
    }
}

class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;
    private ThreadSafeSingleton() {}

    public static ThreadSafeSingleton getInstance() {
        if(instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                instance = new ThreadSafeSingleton();
            }
        }
        return instance;
    }
}

class ThreadSafeSingletonWithoutSynchronized {
    private static ThreadSafeSingletonWithoutSynchronized instance;
    private ThreadSafeSingletonWithoutSynchronized() {}

    private static class ThreadSafeInstanceHolder {
        public static ThreadSafeSingletonWithoutSynchronized intance = new ThreadSafeSingletonWithoutSynchronized();
    }

    public static ThreadSafeSingletonWithoutSynchronized getInstance() {
        return ThreadSafeInstanceHolder.intance;
    }
}