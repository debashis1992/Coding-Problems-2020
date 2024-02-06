package subarrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateTest {

    static int ans=0;
    public static void main(String[] args) {
        int[] a={10,1 ,0,3, 4, 7};
        ans=0;
//        generate(a, 0, new ArrayList<>());

//        generateXOR(a, 0, 0, 4);
//        System.out.println("ans: "+ans);

        count();
    }

    public static void count() {
        List<List<Integer>> list =
        List.of(
                List.of(),
                List.of(10),
                List.of(10, 1),
                List.of(10, 1, 0),
                List.of(10, 1, 0, 3),
                List.of(10, 1, 0, 3, 4),
                List.of(10, 1, 0, 3, 4, 7),
                List.of(10, 1, 0, 3, 7),
                List.of(10, 1, 0, 4),
                List.of(10, 1, 0, 4, 7),
                List.of(10, 1, 0, 7),
                List.of(10, 1, 3),
                List.of(10, 1, 3, 4),
                List.of(10, 1, 3, 4, 7),
                List.of(10, 1, 3, 7),
                List.of(10, 1, 4),
                List.of(10, 1, 4, 7),
                List.of(10, 1, 7),
                List.of(10, 0),
                List.of(10, 0, 3),
                List.of(10, 0, 3, 4),
                List.of(10, 0, 3, 4, 7),
                List.of(10, 0, 3, 7),
                List.of(10, 0, 4),
                List.of(10, 0, 4, 7),
                List.of(10, 0, 7),
                List.of(10, 3),
                List.of(10, 3, 4),
                List.of(10, 3, 4, 7),
                List.of(10, 3, 7),
                List.of(10, 4),
                List.of(10, 4, 7),
                List.of(10, 7),
                List.of(1),
                List.of(1, 0),
                List.of(1, 0, 3),
                List.of(1, 0, 3, 4),
                List.of(1, 0, 3, 4, 7),
                List.of(1, 0, 3, 7),
                List.of(1, 0, 4),
                List.of(1, 0, 4, 7),
                List.of(1, 0, 7),
                List.of(1, 3),
                List.of(1, 3, 4),
                List.of(1, 3, 4, 7),
                List.of(1, 3, 7),
                List.of(1, 4),
                List.of(1, 4, 7),
                List.of(1, 7),
                List.of(0),
                List.of(0, 3),
                List.of(0, 3, 4),
                List.of(0, 3, 4, 7),
                List.of(0, 3, 7),
                List.of(0, 4),
                List.of(0, 4, 7),
                List.of(0, 7),
                List.of(3),
                List.of(3, 4),
                List.of(3, 4, 7),
                List.of(3, 7),
                List.of(4),
                List.of(4, 7),
                List.of(7)
        );

        list.forEach(sublist -> {
            int cr=0;
            for (Integer integer : sublist) {
                cr^=integer;
            }
            if(cr == 4) {
                System.out.println("got here...");
            }
        });


    }

    public static void generate(int[] a, int i, List<Integer> list) {

        System.out.println(list);
        for(int j=i;j<a.length;j++) {
            list.add(a[j]);
            generate(a, j+1, list);
            list.remove(list.size()-1);
        }
    }


    public static void generateXOR(int[] a, int i, int cr, int key) {
        System.out.println(cr);
        if(cr == key)
            ans++;

        for(int j=i;j<a.length;j++) {
            int cr1 = cr^a[i];
            generateXOR(a, j+1, cr1, key);
            cr1 = cr; //backtracking
        }
    }
}
