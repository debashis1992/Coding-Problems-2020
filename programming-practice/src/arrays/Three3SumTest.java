package arrays;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Three3SumTest {
    public static void main(String[] args) {
        int[] a = {-10, -30,50,40,-20,20,0,-7,7};
        System.out.println(findPairs(a));

        Stream.of(1,2,3).collect(Collectors.toList());
    }
    public static List<List<Integer>> findPairs(int[] a) {
        List<List<Integer>> finalList = new ArrayList<>();
        Set<List<Integer>> setList = new HashSet<>();
        int n = a.length;
        for(int i=0;i+1<n;i++) {
            Set<Integer> set = new HashSet<>();
            for(int j=i+1;j<n;j++) {
                List<Integer> list = new ArrayList<>();
                int sum = -(a[i]+a[j]);
                if(set.contains(sum)) {
//                    System.out.println(sum+" "+a[i]+" "+a[j]);
                    list.add(sum);
                    list.add(a[i]);
                    list.add(a[j]);
                    Collections.sort(list);
                    setList.add(list);
                } else set.add(a[j]);
            }
        }

        return setList.stream().collect(Collectors.toList());
    }
}
