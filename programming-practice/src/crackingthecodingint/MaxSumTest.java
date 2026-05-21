package crackingthecodingint;

public class MaxSumTest {
    public static void main(String[] args) {

        int[] a={-100,1,2,-1000,100};
        System.out.println(solve(a));
    }

    public static int solve(int[] a) {
        int sum=a[0], max = a[0];
        for (int i=1;i<a.length;i++) {
            int maxSum = Math.max(sum + a[i], a[i]);
            max = Math.max(max, maxSum);
        }
        return max;
    }
}
