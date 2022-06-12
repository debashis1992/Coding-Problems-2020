package slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {

    public static String findSubstring(String str, String pattern) {
        int start = 0, end = 0, matched = 0, min = str.length();
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : pattern.toCharArray())
            map.put(ch, map.getOrDefault(ch, 0) + 1);

        char[] ch = str.toCharArray();
        int minStart = 0, minEnd = 0;
        while (end < str.length()) {
            char rightCh = ch[end];
            if (map.containsKey(rightCh)) {
                int value = map.get(rightCh);
                map.put(rightCh, value - 1);
                if (value - 1 == 0)
                    matched++;
            }

            while (start < end && matched == map.keySet().size()) {
                char leftCh = ch[start++];
                if (map.containsKey(leftCh)) {
                    int val = map.get(leftCh);
                    map.put(leftCh, val + 1);
                    if (val + 1 == 0)
                        matched--;
                }
                if(min > end-start+1) {
                    minStart = start;
                    minEnd = end;
                    min = end-start+1;
                }
            }
            end++;
        }
        System.out.println(min);
        if (minStart == 0 && minEnd == 0)
            return "";
        return str.substring(minStart, minEnd + 1);
    }

    public static void main(String[] args) {
        String str = "abdbca", pattern = "abc";
        System.out.println(findSubstring(str, pattern));
    }
}
