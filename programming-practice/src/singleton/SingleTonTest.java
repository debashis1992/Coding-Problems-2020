package singleton;

public class SingleTonTest {
}

class SingleTon {
    private SingleTon() {}
    private static SingleTon instance;

    public static SingleTon getInstance() {
        if(instance==null)
            instance=new SingleTon();

        return instance;
    }
}


class ThreadSafeSingleTon {
    private ThreadSafeSingleTon() {}
    private static volatile ThreadSafeSingleTon instance;

    private static ThreadSafeSingleTon getInstance() {
        if(instance == null) {
            synchronized (ThreadSafeSingleTon.class) {
                instance = new ThreadSafeSingleTon();
            }
        }
        return instance;
    }
}

//https://javaninja.io/question/how-to-create-thread-safe-singleton-objects-in-java-without-using-synchronized/

class ThreadSafeSingleTonWithoutSynchronized {
    private ThreadSafeSingleTonWithoutSynchronized() {}

    private static final class HoldInstance {
        private static ThreadSafeSingleTonWithoutSynchronized instance
                = new ThreadSafeSingleTonWithoutSynchronized();
    }

    public static ThreadSafeSingleTonWithoutSynchronized getInstance() {
        return HoldInstance.instance;
    }
}