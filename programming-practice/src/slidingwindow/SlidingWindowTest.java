package slidingwindow;

import java.util.*;

public class SlidingWindowTest {
    public static void main(String[] args) {
//        int[] a = {1,3,2,6,-1,4,1,8,2};
//        findAvg(a, 5);
//        int[] a = {2,1,5,1,3,2};
//        int[] a = {2,3,4,1,5};
//        findMaxSubArraySum(a, 2);
//        findMinLength(new int[]{3,4,1,1,6}, 8);
//        longestSubstring("cbbebi", 10);
//        longestSubstringWithFrequency("ababacb", 3);
        longestPalindomicSubstring("bbbab");
    }
    public static void longestPalindomicSubstring(String s) {
        int max=1;
        for(int i=0;i<s.length();i++) {
            for(int j=i+1;j<s.length();j++) {
                String temp = s.substring(i,j);
                if(isPalindome(temp))
                    max= Math.max(max, temp.length());
                else break;
            }
        }
        System.out.println(max);
    }
    private static boolean isPalindome(String s) {
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    public static boolean check(Map<String,Integer> map, int k) {
        for(Map.Entry<String,Integer> entry: map.entrySet()) {
            if(entry.getValue() < k)
                return false;
        }
        return true;
    }
    public static void longestSubstringWithFrequency(String s, int k) {
        int start=0,end=0,n=s.length();
        int max = 0;
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++) {
            String v = String.valueOf(s.charAt(i));
            map.put(v, map.getOrDefault(v,0)+1);
        }
        System.out.println(map);
        Map<String,Integer> runningMap = new HashMap<>();
        while(end < n) {
            String v = String.valueOf(s.charAt(end));
            if(map.get(v) >= k) {
                int len = end-start+1;
                if(max < len)
                    max = len;
            } else {
                if(check(runningMap, k)) {
                    max = Math.max(max, end-start+1);
                }
                start=end+1;
                end=start;
                continue;
            }
            runningMap.put(v, runningMap.getOrDefault(v,0)+1);
            end++;
        }
        System.out.println(max);
    }

    public static void longestSubstring(String s,int k) {
        int start=0, end=0,n=s.length();
        int max = Integer.MIN_VALUE;
        Set<String> set = new HashSet<>();
        while(end < n) {
            set.add(String.valueOf(s.charAt(end)));
            if(set.size() == k) {
                if(max < (end-start+1))
                    max = end-start+1;
            } else if(set.size() > k) {
                set.remove(String.valueOf(s.charAt(start)));
                start++;
            }
            end++;
        }

        if(max == Integer.MIN_VALUE)
            System.out.println(n);
        else System.out.println(max);
    }

    public static void findMinLengthUsingSlidingWindow(int[] a,int s) {
        int end=0;
        int start=0;
        int sum=0, min=Integer.MAX_VALUE;
        for(int i=0;i<a.length;i++) {
            sum+= a[end];

            while(sum >= s) {
                min = Math.min(min, end-start+1);
                sum-= a[start];
                start++;
            }
        }
        min = min == Integer.MAX_VALUE ? 0 : min;
        System.out.println(min);
    }

    public static void findMinLength(int[] a, int s) {
        int min = a.length;
        //single-pass
        for(int i: a) {
            if(i > s) {
                System.out.println(1);
                return;
            }
        }
        for(int i=0;i<a.length;i++) {
            int sum=0;
            for(int j=i;j<a.length;j++) {
                sum+= a[j];
                if(sum >= s) {
                    int length = j-i+1;
                    if(min > length)
                        min = length;
                    break;
                }
            }
        }
        System.out.println(min);

    }
    public static void findAvg(int[] a, int k) {
        List<Double> list = new ArrayList<>();
        if(k >= a.length) {
            System.out.println((a[0] + a[a.length - 1]) / k);
            return;
        }
        double sum=0.0;
        for(int i=0;i<k;i++)
            sum+= a[i];
        list.add(sum/k);

        for(int i=1;i-1+k<a.length;i++) {
            sum+= a[i-1+k] - a[i-1];
            list.add(sum/k);
        }
        list.forEach(System.out::println);
    }

    public static void findMaxSubArraySum(int[] a, int k) {
        int sum=0;
        int max=0;
        for(int i=0;i<k;i++)
            sum+= a[i];
        if(max < sum)
            max=sum;
        for(int i=1;i-1+k<a.length;i++) {
            sum+= a[i-1+k] - a[i-1];
            if(max<sum)
                max=sum;
        }
        System.out.println(max);
    }
}
