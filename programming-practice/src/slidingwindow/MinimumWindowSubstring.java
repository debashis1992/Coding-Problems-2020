package slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {

    public static String findSubstring(String str, String pattern) {
        int start=0,end=0,matched=0,min=str.length();
        Map<Character,Integer> hashMap =  new HashMap<>();
        for(char ch: pattern.toCharArray())
            hashMap.put(ch, hashMap.getOrDefault(ch, 0+1));

        char[] ch = str.toCharArray();
        Map<Character,Integer> map = hashMap;
        int minStart=0, minEnd = 0;
        while(end < str.length()) {
            char rightCh = ch[end];
            if(map.containsKey(rightCh)) {
                map.put(rightCh, map.get(rightCh)-1);
                if(map.get(rightCh) == 0)
                    matched++;
            }
            if(matched == hashMap.size()) {
                int length = end-start+1;
                if(min > length) {
                    minStart = start;
                    minEnd = end;
                }
                if(min == pattern.length()) {
                    return str.substring(minStart, minEnd+1);
                }
                map = new HashMap<>(hashMap);
                start++;
                end = start;
            }

            end++;
        }
        return str.substring(minStart, minEnd+1);
    }

    public static void main(String[] args) {
        String str = "aabdec", pattern = "abc";
        System.out.println(findSubstring(str, pattern));
    }
}
