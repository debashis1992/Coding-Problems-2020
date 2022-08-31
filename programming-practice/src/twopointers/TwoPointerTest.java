package twopointers;

import java.util.*;

public class TwoPointerTest {

    public static List<List<Integer>> list = new ArrayList<>();
    public static void main(String[] args) {

//        int[] a = {1, 2, 3, 4, -5};
//        int target = 10;
//
//        Arrays.sort(a);
//        int min = Integer.MAX_VALUE;
//        for(int i=0;i+2<a.length;i++) {
//            min = Math.min(min, find(a, a[i], target - a[i], i+1, min));
//            // System.out.println("min: "+min);
//        }
//
//        int res=Integer.MAX_VALUE;
//        for(List<Integer> l : list) {
//            res = Math.min(res, l.stream().reduce(0, Integer::sum));
//        }
//
//        System.out.println("res: "+res);

        list = new ArrayList<>();
        int[] a = {-1,0,1,2,-1,-4};
        Arrays.sort(a);

        for(int i=0;i+2<a.length;i++) {
            if(i>0 && a[i] == a[i-1])
                continue;

            search3Sum(a, -a[i], a[i], i+1);
        }
        System.out.println("Result: "+list);
    }

    public static void search3Sum(int[] a, int target, int start, int left) {
        int right = a.length-1;

        while(left < right) {
            int c = a[left]+a[right];
            if(target == c) {
                list.add(Arrays.asList(start, a[left], a[right]));
                left++;
                right--;
                while(left < right && a[left] == a[left-1])
                    left++;
                while(left < right && a[right] == a[right+1])
                    right--;
            }

            else if(target > c)
                left++;
            else right--;
        }
    }

    public static int find(int[] a, int first, int diff, int left, int min) {
        int right = a.length-1;
        int runningMin = min;
        List<Integer> temp = new ArrayList<>();
        while(left < right) {
            int c = a[left] + a[right];
            runningMin = Math.abs(diff - c);
            temp = Arrays.asList(first, a[left], a[right]);
            // System.out.println(temp);
            if(diff == c) {
                break;
            }
            if(diff > c)
                left++;
            else right--;
        }

        if(runningMin < min) {
            list = new ArrayList<>();
            list.add(temp);
        } else if(runningMin == min) {
            list.add(temp);
        }
        // int[] res = {first, a[left], a[right]};
        // System.out.println(Arrays.toString(res));

        System.out.println("list: "+list);

        return runningMin;
    }
}
