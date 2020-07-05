package searching;

import java.util.Scanner;

public class JumpSearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = sc.nextInt();
            int term = sc.nextInt();
            System.out.println(performJumpSearch(a, term));
        }
    }

    public static int performJumpSearch(int[] a,int term) {
        int lastIndex = -1;
        int lastJumpIndex = -1;
        //Find jump range
        int m = (int)Math.sqrt(a.length);
        for(int i=0;i<a.length;i=i+m) {
            if (a[i] == term) {
                return i;
            }
            else if(a[i] > term) {
                lastIndex = i;
                break;
            }
            lastJumpIndex = i;
        }
        if(lastIndex==-1)
            return -1;
        for(int i=lastIndex-1;i>=lastJumpIndex;i--) {
            if(a[i] == term)
                return i;
        }
        return -1;
    }
}
