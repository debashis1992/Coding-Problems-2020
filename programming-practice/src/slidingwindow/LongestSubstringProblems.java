package slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringProblems {
    public static void main(String[] args) {
        String s = "acbabcd";
        System.out.println(getLongestSubstringWithDistinctCharc(s));
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
}
