package jdk.practice;

import java.io.Serializable;
import java.lang.ref.Cleaner;

public class TestClass {

    private static final Cleaner cleaner = Cleaner.create();
    private State state;

    public TestClass(State state) {
        this.state=state;
        cleaner.register(this, state);
    }
    public static void main(String[] args) {
//        try(Attribute attribute = new Attribute()) {
//            System.out.println("inside try");
////            throw new Exception("some-exception");
//        } catch(Exception e) {
//            System.out.println("inside catch");
//        } finally {
//            System.out.println("executing finally");
//        }

//        State state1 = new State(1);
//        TestClass testClass = new TestClass(state1);
//
//        testClass=null;
//        for(int i=0;i<10;i++) {
//            System.gc();
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {}
//        }

        String s1 = "Javatpoint";
        String s2 = s1.intern();
        String s3 = new String("Javatpoint");
        String s4 = s3.intern();
        System.out.println(s1==s2); // true
        System.out.println(s1==s3); // false
        System.out.println(s1==s4); // true
        System.out.println(s2==s3); // false
        System.out.println(s2==s4); // true
        System.out.println(s3==s4); // false

    }
}

class State implements Runnable {
    private int id;
    public State(int id) {
        this.id=id;
    }

    @Override
    public void run() {
        System.out.println("cleaning this object: "+this.getClass().getName());
    }
}

class Attribute implements AutoCloseable {
    @Override
    public void close() {
        System.out.println("closing attribute");
    }
}


class MyExc extends Exception {}
class MyExc2 extends Exception {}



class C {
    public void method1() throws Exception {

    }
}


class D implements Serializable {
    public static final long serialVersionUID = 1L;

    int id;
    String name;
}
