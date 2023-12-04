package cloningTests;

//import cloning.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CloningUtils {
    public CloningUtils() {
        super();
    }
    public static void main(String[] args) throws CloneNotSupportedException {

        A a1 = new A();
        A a2=(A)a1.clone();

        System.out.println(a1.list);
        System.out.println(a2.list);

        a1.list.add(1000);
        System.out.println(a1.list);
        System.out.println(a2.list);

    }
}
class A implements Cloneable {
    List<Integer> list;
    public A() {
        list=new ArrayList<>();
        list.addAll(Arrays.asList(1,2,3));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        A newObj = new A();
        newObj.list = new ArrayList<>(this.list);
        return newObj;
    }
}
