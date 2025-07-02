package refresh;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RefresherTest {
    public static void main(String[] args) {

        List<List<Integer>> l1=new ArrayList<>();
        l1.add(new ArrayList<>());

        l1.get(0).addAll(Arrays.asList(1,2,3));
        System.out.println(l1);

    }

    static void method2(Number i) {}

    static void method1(Number f) {
        System.out.println("double: " + f);
    }
}
class Parent {
    public void method1(Number i) {
        System.out.println("Parent Number: " + i);
    }
}
class Child extends Parent {
//    @Override
    public void method1(Integer i) {
        System.out.println("Child Integer: " + i);
    }

    public void method2(Double d) {
        System.out.println("Child Double: " + d);
    }

    public void method3(Number n) {
        System.out.println("Child Number: " + n);
    }
}


class OverloadingTest {

    public void doSomething(Integer i) {
        System.out.println("Integer: " + i);
    }
    public void doSomething(long i) {
        System.out.println("long: " + i);
    }

    public void call(int i, int j) {
//        System.out.println("Number: ");
        System.out.println("Number: " + i);
    }

    public void call(long i, long j) {
        System.out.println("Integer: " + i);
    }


    public void call() {

    }
}

class ComparableTest<T extends Comparable<T>> implements Comparable<ComparableTest<T>> {
    T obj;
    public ComparableTest(T obj) { this.obj = obj; }

    @Override
    public int compareTo(ComparableTest<T> that) {
        return this.obj.compareTo(that.obj);
    }
}

class IntValue<T> {
    private T val;
    public IntValue(T val) { this.val = val; }
    public T getVal() { return this.val; }
}

class ComparatorTest<T extends Comparable<T>> implements Comparator<IntValue<T>> {

    @Override
    public int compare(IntValue<T> obj1, IntValue<T> obj2) {
        return obj1.getVal().compareTo(obj2.getVal());
    }
}


interface Transformer<T, R> {
    R transform(T input);
}

class TextClass<T extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private T text;

    public TextClass(T text) {
        this.text = text;
    }

    public T getText() {
        return text;
    }

    public void setText(T text) {
        this.text = text;
    }
}


class ObjectLike<T> {
    T obj;
    public ObjectLike(T obj) {
        this.obj=obj;
    }

    public T getObj() {
        return obj;
    }
}

class Animal {}
class Dog extends Animal {}

final class CloningTest implements Cloneable {

    @Override
    public CloningTest clone() throws CloneNotSupportedException {
        Object that = super.clone();
        System.out.println(that.getClass().getName());
        return (CloningTest)that;
    }
}

class SomeResource implements AutoCloseable {
    @Override
    public void close() {
        System.out.println("Resource closed");
    }
    public void use() {
        System.out.println("using resource");
    }
}

class AnotherResource implements Closeable {
    @Override
    public void close() {
        System.out.println("Another resource closed");
    }

    public void use() {
        System.out.println("using another resource");
    }
}

class CustomCollection<T> {
    private Map<T, Object> map;
    public CustomCollection() {
        this.map = new LinkedHashMap<>();
    }

    public void add(T item) {
        if(null == item) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        map.put(item, new Object());
    }

    public void remove(T item) {
        if(null == item) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        map.remove(item);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Iterator<T> iterator() {
        return map.keySet().iterator();
    }

    public boolean contains(T item) {
        return map.containsKey(item);
    }
}


class Person implements Serializable {
    private final Integer id;
    private final String name;
    private static int count;
    private transient String password;

    public final static int serialVersionUUID = 1000;
    static {
        count=0;
    }
    public Person(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        ++count;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // accessOrder = true
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public synchronized void add(K key, V value) {
        super.put(key,value);
    }

    public synchronized V getValueFromKey(K key) {
        return super.get(key);
    }

    public synchronized  boolean doesContainKey(K key) {
        return super.containsKey(key);
    }
}



