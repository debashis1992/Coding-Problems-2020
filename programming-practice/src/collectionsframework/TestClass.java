package collectionsframework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Employee {
    //1000 objects
}


public class TestClass implements Serializable {
    //primitives
    int i=1;
    char ch='a';
    double d;
    boolean b;

    //wrapper classes
    Integer i2 = 1;

    Character ch2;
    Double d2;
    String s2;
    Boolean b2;

    public static void main(String[] args) {

        ArrayList list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add("string");

        //using generics
        List<Integer> list2 = new ArrayList<>();//size 10
        List<Integer> integerList = Arrays.asList(1,2,3,4,5,4);

        System.out.println(integerList);
        list2.addAll(integerList);
        System.out.println(list2);

        for(int i=0;i<list2.size();i++) {
            if(list2.get(i) == 4) {
                System.out.println("got index : "+i);
                break;
            }
        }
        System.out.println("index: "+list2.indexOf(4));
        System.out.println("last index: "+list2.lastIndexOf(4));
        System.out.println(list2);
        list2.set(2,1000);
        System.out.println(list2);

        //[1, 2, 4, 4, 5, 1000] -> 1, 2, 0, 1, 995
        list2.sort((a,b) -> b-a);
        System.out.println("sorted: "+list2);


    }

}
