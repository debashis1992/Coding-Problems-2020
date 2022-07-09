package java8plus;

import java.util.Arrays;
import java.util.List;

public class Java8PlusTest {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("happy", "sad");
        System.out.println(list);

        list.add("new");
        System.out.println(list);
    }
}
