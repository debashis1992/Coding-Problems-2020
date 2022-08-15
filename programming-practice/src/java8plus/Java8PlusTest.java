package java8plus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Java8PlusTest {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("happy", "sad");
        System.out.println(list);

//        list.add("new");
        System.out.println(list);

        //in java-8
        List<String> list2 = Collections.unmodifiableList(
                Arrays.asList("happy", "sad")
        );

        //in java-11
        List<String> list3 = new ArrayList<>(List.of("happy", "sad"));
        System.out.println(list3);
        list3.add("new-string");
        System.out.println(list3);

    }
}
