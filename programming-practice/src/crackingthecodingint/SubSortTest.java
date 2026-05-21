package crackingthecodingint;

import java.util.Arrays;

public class SubSortTest {
    public static void main(String[] args) {

        int[] a={1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};

        int[] res=subsort(a);
        System.out.println(Arrays.toString(res));

    }
    public static int[] subsort(int[] a) {
        int[] res=new int[2];
        int left=0;
        while(left+1 < a.length && a[left] <= a[left+1])
            left++;

        if(left == a.length-1) return new int[]{-1,-1};

        int right=a.length-1;
        while(right-1>=0 && a[right-1]<=a[right])
            right--;

//        System.out.println(left+","+right);
        int min=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;

        for(int i=left;i<=right;i++) {
            min=Math.min(min, a[i]);
            max=Math.max(max, a[i]);
        }

//        System.out.println(min+","+max);

        while(left>=0 && min < a[left]) {
            left--;
        }

        while(right<a.length && max > a[right])
            right++;

        res[0]=left+1;
        res[1]=right-1;
        return res;
    }
}
