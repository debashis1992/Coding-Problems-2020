package slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringProblems {
    public static void main(String[] args) {
//        String s = "acbabcd";
//        System.out.println(getLongestSubstringWithDistinctCharc(s));
        String s = "AABABBA";
        int k=1;
        System.out.println(Math.max(leftToRight(s,k), rightToLeft(s, k)));
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
