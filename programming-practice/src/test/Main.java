package test;

import java.util.*;

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

        String s= "the sky     is  blue";
        System.out.println(Arrays.toString(s.split(" ")));

        List<String> words = Arrays.asList("the","sky","is","blue");
        List<String> rev = words.reversed();

        System.out.println("words: "+rev);

        Random random = new Random();
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));

        List<Integer> list = Arrays.asList(100,101,102);


    }
}

