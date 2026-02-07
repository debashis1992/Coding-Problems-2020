package designPatterns.solid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LiskovTest {

    public static void main(String[] args) {
        CoffeeMachine1 machine1 = new CoffeeMachine1();
        CoffeeMachine2 machine2 = new CoffeeMachine2();
        CoffeeShop coffeeShop = new CoffeeShop(machine2);
        coffeeShop.getCoffee();

        Random random = new Random();
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        char[] c = new char[7];
        for(int i=0;i<c.length;i++) {
            int idx = random.nextInt(s.length());
            c[i] = s.charAt(idx);
        }
        System.out.println(String.valueOf(c));

        String a=  "aabcbbbccc";
        String d = Stream.of(a).collect(Collectors.joining(""));
        System.out.println(d);

        String d2 = a.chars().distinct()
                        .mapToObj(x -> String.valueOf((char)x)).collect(Collectors.joining(""));
        System.out.println(d2);
    }
}

class CoffeeShop {
    private final CoffeeMaker coffeeMaker;
    public CoffeeShop(CoffeeMaker coffeeMaker) {
        this.coffeeMaker = coffeeMaker;
    }

    public void getCoffee() {
        coffeeMaker.makeCoffee();
    }
}

interface CoffeeMaker {
    void makeCoffee();
}

class CoffeeMachine1 implements CoffeeMaker {
    @Override
    public void makeCoffee() {
        System.out.println("making coffee from machine 1...");
    }
}

class CoffeeMachine2 implements CoffeeMaker {
    @Override
    public void makeCoffee() {
        System.out.println("making coffee from machine 2...");
    }
} 