package test;

import java.util.ArrayList;
import java.util.List;

class Person {
    String name;
    public Person(String name) {
        this.name = name;
    }
}

class Employee extends Person {
    public Employee(String name) {
        super(name);
    }
}

class Engineer extends Person {
    public Engineer(String name) {
        super(name);
    }

}

public class Main {
    public static void main(String[] args) {


    }
}

