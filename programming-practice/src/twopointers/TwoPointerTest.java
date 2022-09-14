package twopointers;

import java.util.*;

public class TwoPointerTest {

    public static int c=0;
    public static List<List<Integer>> actualList = new ArrayList<>();
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

//        list = new ArrayList<>();
//        int[] a = {-1,0,1,2,-1,-4};
//        Arrays.sort(a);
//
//        for(int i=0;i+2<a.length;i++) {
//            if(i>0 && a[i] == a[i-1])
//                continue;
//
//            search3Sum(a, -a[i], a[i], i+1);
//        }
//        System.out.println("Result: "+list);

//        int[] a = {-1,4,2,1,3};
//        int t = 5;
//        System.out.println(search3SumSmaller(a, t));

//        System.out.println("final list: "+actualList);

        String s = "bxj##tw";
        String t = "bxj###tw";

//        compare(s,t);

        int[] a = {3,1,2};
        System.out.println(minimumWindowSorting(a));
    }

    public static int minimumWindowSorting(int[] a) {
        int start=0;
        int end=0;

        for(int i=0;i<a.length;i++) {
            if(i>0 && a[i-1] > a[i]) {
                start=i-1;
                break;
            }
        }

        for(int i=a.length-1;i>0;i--) {
            if(a[i-1] > a[i]) {
                end=i;
                break;
            }
         }

        int min=Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int i=start;i<=end;i++) {
            min = Math.min(min, a[i]);
            max = Math.max(max, a[i]);
        }

        for(int i=0;i<start;i++) {
            if(a[i] > min) {
                start=i;
                break;
            }
        }

        for(int i=a.length-1;i>end;i--) {
            if(a[i] < max) {
                end=i;
                break;
            }
        }

        System.out.println("start: "+start+", end: "+end);
        if(start==0 && end==0)
            return 0;
        return end-start+1;
    }

    public static boolean compare(String s, String t) {
        int i=s.length()-1, j=t.length()-1;

        int iBS=0, jBS=0;
        while(i>=0 || j>=0) {
            if(i>=0 && s.charAt(i) == '#') {
                i--;
                iBS++;
                continue;
            }

            if(j>=0 && t.charAt(j) == '#') {
                j--;
                jBS++;
                continue;
            }
            if(i>=0 && iBS>0) {
                iBS--;
                i--;
                continue;
            }
            if(j>=0 && jBS>0) {
                jBS--;
                j--;
                continue;
            }

            if(i>=0 && j>=0 && s.charAt(i) == t.charAt(j)) {
                i--;
                j--;
            }
            else
                return false;
        }

        return true;

    }

    public static int search3SumSmaller(int[] a, int target) {
        Arrays.sort(a);
        c=0;
        actualList = new ArrayList<>();
        for(int i=0;i<a.length-2;i++) {
            if(i>0 && a[i] == a[i-1])
                continue;
            search3SumSmaller(a, a[i], i+1, target);
        }

        return c;
    }

    public static void search3SumSmaller(int[] a, int start, int left, int target) {
        int right = a.length-1;
        while(left < right) {
            int s = start+a[left]+a[right];
            if(s < target) {
                c+= (right - left);
                for(int i=left+1;i<=right;i++)
                    actualList.add(Arrays.asList(start, a[left], a[i]));
                ++left;
            }
            else --right;
        }
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
