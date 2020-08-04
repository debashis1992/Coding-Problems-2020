package arrays;

public class MinSubArraySumTest {
    public static void main(String[] args) {
//        int[] a = {2,3,1,2,4,3};
//        int s = 7;
//        System.out.println("count : "+minSubArrayLenImprov(s,a));

        int[] arr = {1, 4};
        int sum = 0;

        findSubArrayWithGivenSum(arr, sum);
    }

    public static int findSubArrayWithGivenSum(int[] ar, int sum) {
        int s = 0, start = 0, end = 0, n = ar.length;
        while (end < n) {
            while (s < sum && end < n) {
                s += ar[end++];
            }
            while (s >= sum && start < n) {
                if (s == sum) {
                    System.out.println("Found same between indexes : " + start + " and " + (end - 1));
                    return 1;
                }
                s -= ar[start++];
            }
        }
        System.out.println("No sub array was found");
        return 0;
    }

    public static int minSubArrayLen(int s, int[] a) {
        int n = a.length;
        int minCount = Integer.MAX_VALUE;
        if (checkIfPresent(a, s))
            return 1;
        for (int i = 0; i < n - 1; i++) {
            int count = 1;
            int sum = a[i];
            for (int j = i + 1; j < n; j++) {
                if (sum < s) {
                    sum += a[j];
                    ++count;
                } else break;
            }
//            System.out.println(count);
            if (count < minCount)
                minCount = count;
        }
        return minCount == Integer.MAX_VALUE ? 0 : minCount;
    }

    private static boolean checkIfPresent(int[] a, int s) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= s)
                return true;
        }
        return false;
    }

    public static int minSubArrayLenImprov(int s, int[] a) {
        int minLen = Integer.MAX_VALUE, len = 0, start = 0, end = 0;
        int currSum = 0;
        int n = a.length;
        while (end < n) {
            while (currSum < s && end < n) {
                currSum += a[end++];
            }

            while (currSum >= s && start < n) {
                len = end - start;
                if (len < minLen)
                    minLen = len;
                currSum -= a[start++];
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
