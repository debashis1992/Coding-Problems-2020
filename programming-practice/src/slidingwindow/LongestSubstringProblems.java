package slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringProblems {
    public static void main(String[] args) {
//        String s = "acbabcd";
//        System.out.println(getLongestSubstringWithDistinctCharc(s));
//        String s = "abccde";
//        int k=1;
//        System.out.println(getLongestSubstringWithSameChar(s, k));

        int[] ar = {0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1};
        int k=3;
        System.out.println(getLongestSubArrayWithOnesAfterReplacement(ar, k));
    }

    public static int getLongestSubArrayWithOnesAfterReplacement(int[] nums, int k) {
        int start=0,end=0,maxOcc=0,max=0;
        Map<Integer, Integer> map = new HashMap<>();
        while(end < nums.length) {
            map.put(nums[end], map.getOrDefault(nums[end],0)+1);

            maxOcc = Math.max(maxOcc, map.getOrDefault(1,0));
            if((end - start + 1) - maxOcc > k) {
                map.put(nums[start], map.get(nums[start])-1);
                start++;
            }

            max = Math.max(max, end-start+1);
            end++;
        }
        return max;
    }

    public static int getLongestSubstringWithSameChar(String s, int k) {
        char[] c = s.toCharArray();
        int start=0, end=0, max=0;
        Map<Character, Integer> map = new HashMap<>();
        int maxOcc = 0;

        while(end < c.length) {
            map.put(c[end], map.getOrDefault(c[end],0)+1);

            maxOcc = Math.max(maxOcc, map.get(c[end]));
            if((end - start + 1) - maxOcc > k) {
                char startChar = c[start];
                map.put(startChar, map.get(startChar)-1);
                start++;
            }

            max = Math.max(max, end-start+1);
            end++;
        }
        return max;
    }

    public static int getLongestSubstringWithDistinctCharc(String s) {
        char[] c = s.toCharArray();
        int start=0, end=0, max =0;
        Map<Character, Integer> map = new HashMap<>();
        while(end < c.length) {
            if(map.containsKey(c[end])) {
                start = Math.max(start, map.get(c[end])+1);
                map.put(c[end], end);
            } else {
                map.put(c[end], end);
            }

            max = Math.max(max, (end - start + 1));
            end++;
        }

        return max;
    }


    public static int leftToRight(String s, int k) {
        char[] c = s.toCharArray();
        int start=0, end=0, max=0;
        Map<Character, Integer> map = new HashMap<>();
        map.put(c[end++], 0);
        int n = k;
        while(end < c.length) {
            if(map.containsKey(c[end]))
                map.put(c[end], map.get(c[end])+1);
            else if(!map.containsKey(c[end])) {
                if (n==0) {
                    start = map.get(c[end-1])+1;
                    n=k;
                    map = new HashMap<>();
                    map.put(c[end], end);
                } else {
                    c[end] = c[end-1];
                    map.put(c[end], map.get(c[end])+1);
                    n--;
                }
            }

            max = Math.max(max, end-start+1);
            end++;
        }
        return max;
    }

    public static int rightToLeft(String s, int k) {
        char[] c = s.toCharArray();
        int len = c.length-1;
        int start=len, end=len,max=0;
        int n=k;
        Map<Character, Integer> map = new HashMap<>();
        map.put(c[end--], len);
        while (end >=0) {
            if(map.containsKey(c[end]))
                map.put(c[end], map.get(c[end])-1);
            else if(!map.containsKey(c[end])) {
                if(n==0) {
                    start = map.get(c[end+1]) - 1;
                    n=k;
                    map = new HashMap<>();
                    map.put(c[end], end);
                } else {
                    c[end] = c[end+1];
                    map.put(c[end], map.get(c[end])-1);
                    n--;
                }
            }

            max = Math.max(max, (start-end+1));
            end--;
        }
        return max;
    }

}
