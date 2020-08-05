package arrays;

import java.util.Scanner;

public class KadaneAlgorithmTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        while (t-- > 0) {
            int n = scanner.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = scanner.nextInt();
            System.out.println(findMaxContiguousSumUsingDp(a));
        }
    }
    /*
    Doesn't work if the array contains only negative numbers
     */
    public static int findMaxContiguousSum(int[] a) {
        int maxSoFar=0, maxEndingHere=0;
        int n = a.length;
        for(int i=0;i<n;i++) {
            maxEndingHere+= a[i];
            if(maxSoFar < maxEndingHere)
                maxSoFar = maxEndingHere;

            if(maxEndingHere < 0)   maxEndingHere=0;
        }
        return maxSoFar;
    }

    /*
    Using Dynamic Programming to handle negative numbers
     */
    public static int findMaxContiguousSumUsingDp(int[] a) {
        int currSum=a[0];
        int maxSum=a[0];

        for(int i=1;i<a.length;i++) {
            currSum = Math.max(a[i], currSum+a[i]);
            maxSum = Math.max(currSum, maxSum);
        }

        return maxSum;
    }


}
