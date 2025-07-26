package practice;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PracticeTest {
    static List<List<String>> list;
    static {

    }
    public static void main(String[] args) throws CloneNotSupportedException {


//        String s = "debab";
//        list=new ArrayList<>();
//        List<String> running=new ArrayList<>();
//        visit(0, s, running);
//        System.out.println(list);

        f("abc", 0);
    }

    public static void f(String s, int index) {
        if(index == s.length()) {
            System.out.println(s);
            return;
        }

        for(int i=index;i<s.length();i++) {
            s = swap(s, index, i);
            f(s, index+1);
            s = swap(s, index, i);
        }
    }

    public static String swap(String s, int i, int j) {
        char[] c=s.toCharArray();
        char tmp = c[i];
        c[i] = c[j];
        c[j] = tmp;
        return String.valueOf(c);
    }

    public static void visit(int i, String s, List<String> running) {
        list.add(new ArrayList<>(running));
        String mod = String.join("", running);
        if(isPalindrom(mod))
            System.out.println(running);

        for(int j=i;j<s.length();j++) {
//            if(j>i && s.charAt(j) == s.charAt(j-1))
//                continue;

            running.add(String.valueOf(s.charAt(j)));
            visit(j+1, s, running);
            running.remove(running.size()-1);
        }
    }

    public static boolean isPalindrom(String s) {
        if(s.isEmpty() || s.isBlank())
            return false;

        StringBuilder s2 = new StringBuilder(s).reverse();
        return s.equals(s2.toString());
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
