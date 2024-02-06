package designPatterns.observer.anotherexample;

public class Test {
    public static void main(String[] args) {

        Product p = new Product();
        User u1=new User();
        User u2=new User();
        User u3=new User();
        User u4=new User();

        p.addConsumers(u1);
        p.addConsumers(u2);
        p.addConsumers(u3);
        p.addConsumers(u4);
        p.setCount(2);


    }
}
