package designPatterns.singleton;

public class SingletonPatternTest {
}

class A {
    private static volatile A instance;
    private A() {}

    public static A getInstance() {
        if(instance == null) {
            synchronized (A.class) {
                instance = new A();
            }
        }
        return instance;
    }
}
