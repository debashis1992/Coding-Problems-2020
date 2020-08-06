package arrays;

import java.util.Arrays;
import java.util.TreeSet;

public class FirstAndLastIndex {
    public static TreeSet<Integer> set = new TreeSet<>();

    public static void main(String[] args) {
        int[] a = {};
        int target = 1;
        System.out.println(Arrays.toString(searchRange(a, target)));
    }

    public static int[] searchRange(int[] a, int target) {
        int n = a.length - 1;
        if(a.length==1 && a[0]==target)
            return new int[]{0,0};
        return findInHalves(a, 0, n, target);
    }

    private static int[] findInHalves(int[] a, int f, int l, int target) {
        if (f < l) {
            int mid = (f + l) / 2;
            findInHalves(a, f, mid, target);
            findInHalves(a, mid + 1, l, target);
            performBinarySearch(a, f, mid, target);
            performBinarySearch(a, mid + 1, l, target);
        }
        if (set.isEmpty())
            return new int[]{-1, -1};
        else return new int[]{set.first(), set.last()};

    }

    private static int performBinarySearch(int[] a, int f, int l, int target) {
        if (f <= l) {
            int mid = (f + l) / 2;
            if (a[mid] == target) {
                set.add(mid);
                return mid;
            } else if (a[mid] < target)
                return performBinarySearch(a, mid + 1, l, target);
            else return performBinarySearch(a, f, mid - 1, target);
        }
        return -1;
    }
}
