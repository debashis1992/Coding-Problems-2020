package interfacetests;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

interface InterfaceA {
    default void method1() {

    }

    private void method2() {
        method1();
    }

    void method3();
}

public class InterfaceImpl implements InterfaceA {
    @Override
    public void method3() {
        System.out.println("implementing method3");
    }

    public static void main(String[] args) throws Exception {
        InterfaceImpl impl=new InterfaceImpl();
        impl.method3();

        TestClass testClass=new TestClass();
        System.out.println(testClass.s);

//        System.out.println(testClass.betterSwitch(2));

        Fruit fruit=new Fruit(1,"apple");
        Fruit fruit2=new Fruit(2,"apple2");


//        new B("Wubba lubba dub dub");

        Map<String, String> map=new HashMap<>();
        map.put(null, null);

        Address address=new Address(1,"house-a","a");
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("address.txt"))) {
            oos.writeObject(address);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("address.txt"))) {
            Object address2 = ois.readObject();
            System.out.println(address2.toString());

        }

        B b = new B();
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("address2.txt"))) {
            oos.writeObject(b);
        }

        try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream("address2.txt"))) {
            B b2 = (B)oos.readObject();
            System.out.println(b2);
        }


        B b2=new B();
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(b2);
        }

        B b3=null;
        ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            b3 =(B) ois.readObject();
            System.out.println(b3);
        }

        b2.i=10;
        System.out.println(b2.i+", "+b3.i);

        try {
            System.out.println("enter try");
            throw new Exception("throwing exception");
        } catch(Exception e) {
            throw e;
        }
        finally {
            System.out.println("finally");
        }
    }
}


class NewEx extends Exception {

}

class TestClass {

    String s = """
            {
                "name": "debashis",
                "roll": 23    
            }
            """;


    public String betterSwitch(int i) {
        var text = switch(i) {
            case 1,3,5 -> {
                System.out.println("even");
                yield "even";
            }
            case 2,4,6 -> {
                System.out.println("odd");
                yield "odd";
            }
            default -> "undefined";
        };

        return text;
    }
}


record Fruit(Integer id, String name) {
    public Fruit(int id) {
        this(id,null);
    }
    public Fruit(String name) {
        this(null, name);
    }
}

class A implements Serializable {
    protected int i;
    public void print() { System.out.println("Hello"); }
    public A() { i = 13; print(); }
}

class B extends A {
    public B() {
        super();
    }
    private String msg;
    public void print() { System.out.println(msg); }
    public B(String msg) { super(); this.msg = msg; }
}

class PatternMatch {

    public void doSomething() {
        Object o = "";
        if(o instanceof String) {
            String s = (String)o;
            System.out.println(s);
        }
    }

    public void doAnother() {
        Object o = "";
        if(o instanceof String s) {
            System.out.println(s);
        }
    }
}


class Address implements Serializable {
    int id;
    String name;
    transient String house;

    private static final long serialVersionUID = 12L;

    public Address(int id, String name, String house) {
        this.id=id;
        this.name=name;
        this.house=house;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", house='" + house + '\'' +
                '}';
    }
}

class AnotherAddress implements Externalizable {

    int id;
    public AnotherAddress() {}

    public AnotherAddress(int id) { this.id = id; }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        id=in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
    }
}

abstract class Ab {
    int id;
    public Ab() {
        id=1;
    }
    public void method1() {}
    abstract public void method2();
}

interface Ac {
    default void method2() {}
    private void method3() {}
    static void method4() {}
    void method5();
}

abstract class Ad extends Ab implements Ac {
    @Override
    public void method2() {}
}

interface Ae {
    default void method2() {}
    void method5();
}

class Af implements Ac, Ae {

    @Override
    public void method2() {

    }

    @Override
    public void method5() {

    }
}