package arrays;

import java.util.Arrays;

public class ProductArrayTest {
    public static void main(String[] args) {

        int arr[]  = {1, 2, 3, 4, 5};

//        Input: arr[]  = {1, 2, 3, 4, 5}
//        Output: ans[]  = {120, 60, 40, 30, 24 }

        int n=arr.length;
        int[] pre=new int[n];
        int[] suff=new int[n];
        pre[0]=1;
        suff[n-1]=1;

        for(int i=1;i<n;i++) {
            pre[i]=pre[i-1]*arr[i-1];
        }
        System.out.println(Arrays.toString(pre));
        for(int i=n-2;i>=0;i--) {
            suff[i]=suff[i+1]*arr[i+1];
        }
        System.out.println(Arrays.toString(suff));
        int[] prod=new int[n];
        for(int i=0;i<n;i++) {
            prod[i] = pre[i]*suff[i];
        }

        System.out.println(Arrays.toString(prod));
    }


}
