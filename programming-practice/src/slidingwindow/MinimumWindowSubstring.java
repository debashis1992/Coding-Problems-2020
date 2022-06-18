package slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {

    public static String findSubstring(String str, String pattern) {
        int i = 0, j = 0;
        int minLen = Integer.MAX_VALUE;
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : pattern.toCharArray())
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        int count = map.size();
        String minWindow = "";
        char[] ch = str.toCharArray();

        while (j < ch.length) {
            if (map.containsKey(ch[j])) {
                int value = map.get(ch[j]);
                map.put(ch[j], --value);
                if (value == 0)
                    count--;
            }
            while (count == 0) {
                if(minLen > (j-i+1)) {
                    minLen = j-i+1;
                    minWindow = str.substring(i, j + 1);
                }
                char startCh = ch[i];
                if (map.containsKey(startCh)) {
                    int startValue = map.get(startCh);
                    map.put(startCh, ++startValue);
                    if (startValue > 0)
                        count++;
                }
                i++;
            }
            j++;
        }
        return minWindow;
    }

    public static void main(String[] args) {
        String str = "ADOBECODEBANCAAAAABRREEEADAJJOJO", pattern = "ABC";
        System.out.println(findSubstring(str, pattern));
    }
}
