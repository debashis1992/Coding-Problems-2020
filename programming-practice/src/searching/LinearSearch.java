package searching;

import java.util.Scanner;

public class LinearSearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = sc.nextInt();
            int term = sc.nextInt();
            System.out.println(performRecursirveLinearSearch(0,a.length-1,a,term));

        }
    }
    public static int performRecursirveLinearSearch(int index, int n, int[] a,int term) {
        if(index > n)
            return -1;
        if(a[index] == term)
            return index;
        if(a[n] == term)
            return n;
        else return performRecursirveLinearSearch(++index, --n,a,term);
    }
}
