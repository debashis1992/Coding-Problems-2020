package practice;

import java.io.*;
import java.util.List;

public class PracticeTest {
    static {

    }
    public static void main(String[] args) throws CloneNotSupportedException {

        Animal animal = new Dog();
        Dog dog = (Dog)animal;


    }
    public static void call() throws Ex1 {
        throw new Ex1("ex1");
    }
}


class Animal {}
class Dog extends Animal {}

class Ex1 extends Exception {
    public Ex1(String name) {
        super(name);
    }
}

class Ex2 extends Exception {
    public Ex2(String name) {
        super(name);
    }
}

class AnotherPerson implements Cloneable {
    final String name;

    public AnotherPerson(String name) {
        this.name = name;
    }

    @Override
    public AnotherPerson clone() throws CloneNotSupportedException {
        return (AnotherPerson) super.clone();
    }
}


class Person implements Serializable {
    String id;
    List<String> friends;
    public Person(String id, List<String> friends) {
        this.id = id;
        this.friends = friends;
    }

    public Person deepCopy() throws IOException, ClassNotFoundException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        Person person = (Person)inputStream.readObject();
        return person;
    }
}

class Employee implements Cloneable {
    int id;
    String friends;

    public Employee(int id, String friends) {
        this.id=id;
        this.friends=friends;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class App implements Cloneable {
    int id;
    public App(int id) {
        this.id=id;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new App(this.id);
    }
}
