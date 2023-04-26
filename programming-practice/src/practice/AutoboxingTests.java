package practice;

import java.io.*;
import java.util.*;

public class AutoboxingTests implements Serializable {
    int i=10,j=20;

    transient final int k=20;
    transient static int l=40;
    transient final static int m=50;
    transient int n=60;

    public static void show(long i) {
        System.out.println("long");
    }

    public static void show(Integer i) {
        System.out.println("Integer");
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        TreeMap<Integer,String> map=new TreeMap<>();
        map.put(100, "hundred");
        map.put(43,"four three");
        map.put(1,"one");
        map.put(2,"two");
        System.out.println(map);

        MySum mySum = (i,j) -> i+j;
        try {
            System.out.println(mySum.sum(2, 3));
        } catch (Exception e) {

        }

        Display<Object> d1 = (i) -> System.out.println(i.getClass().getName());
        d1.show("something");
        d1.show(1.121);
        d1.show('c');
        d1.show(true);

        AutoboxingTests input = new AutoboxingTests();

        //serialization
        FileOutputStream fileOutputStream = new FileOutputStream("file.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
        oos.writeObject(input);


        //deserialization
        FileInputStream fis = new FileInputStream("file.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        AutoboxingTests output = (AutoboxingTests) ois.readObject();
        System.out.println("i = " + output.i);
        System.out.println("j = " + output.j);
        System.out.println("k = " + output.k);
        System.out.println("l = " + output.l);
        System.out.println("m = " + output.m);
        System.out.println("n = " + output.n);

    }


}

@FunctionalInterface
interface Display<T> {
    public void show(T t);
}

@FunctionalInterface
interface MySum {
    int sum(int i,int j) throws Exception;

    default void method1() {
        System.out.println("default method");
    }
    static void method2() {
        System.out.println("static method");
    }

}

class GenericApp<T,U> {
    private T val;
    private U val2;
    public GenericApp(T val, U val2) {
        this.val = val;
        this.val2 = val2;
    }

    public T getVal() { return val; }
    public U getVal2() { return val2; }

}