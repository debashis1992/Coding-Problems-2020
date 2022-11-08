package subsets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubsetTest {

    public static void main(String[] args) {
        int[] a={1,5,3};
        createSubsets(a);
    }

    public static void createSubsets(int[] a) {
        List<List<Integer>> overall=new ArrayList<>();
        overall.add(new ArrayList<>());

        int n=a.length;
        for(int i=0;i<n;i++) {
            List<List<Integer>> existing = new ArrayList<>();
            //copy list
            for(List<Integer> list: overall)
                existing.add(new ArrayList<>(list));

            for(List<Integer> e: existing) {
                e.add(a[i]);
                overall.add(new ArrayList<>(e));
            }
        }

        System.out.println(overall);
    }
}
