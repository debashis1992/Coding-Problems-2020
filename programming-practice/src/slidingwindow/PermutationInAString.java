package slidingwindow;

import java.util.*;

public class PermutationInAString {

    public static List<Integer> findStringAnagrams(String str, String pattern) {
        List<Integer> list = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : pattern.toCharArray())
            map.put(ch, map.getOrDefault(ch, 0) + 1);

        int start = 0, end = 0, matched = 0;
        char[] ch = str.toCharArray();

        while (end < str.length()) {
            char rightCh = ch[end];
            if (map.containsKey(rightCh)) {
                map.put(rightCh, map.get(rightCh) - 1);
                if (map.get(rightCh) == 0)
                    matched++;
            }

            if (matched == map.size())
                list.add(start);

            if (end >= pattern.length() - 1) {
                char leftCh = ch[start++];
                if (map.containsKey(leftCh)) {
                    if (map.get(leftCh) == 0)
                        matched--;
                    map.put(leftCh, map.get(leftCh) + 1);
                }
            }

            end++;
        }
        return list;
    }

    public static boolean findPermutation(String pattern, String str) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : pattern.toCharArray())
            map.put(ch, map.getOrDefault(ch, 0) + 1);

        char[] ch = str.toCharArray();
        int start = 0, end = 0;
        int matched = 0;
        while (end < str.length()) {
            char rightCh = ch[end];
            if (map.containsKey(rightCh)) {
                map.put(rightCh, map.get(rightCh) - 1);
                if (map.get(rightCh) == 0)
                    matched++;
            }

            if (matched == map.size())
                return true;


            if (end >= pattern.length() - 1) {
                char leftCh = ch[start++];
                if (map.containsKey(leftCh)) {
                    if (map.get(leftCh) == 0)
                        matched--;
                    map.put(leftCh, map.get(leftCh) + 1);
                }
            }

            end++;
        }
        return false;
    }

    public static boolean findPermutation2(String pattern, String str) {
        int windowStart = 0, matched = 0;
        Map<Character, Integer> charFrequencyMap = new HashMap<>();
        for (char chr : pattern.toCharArray())
            charFrequencyMap.put(chr, charFrequencyMap.getOrDefault(chr, 0) + 1);

        // our goal is to match all the characters from the 'charFrequencyMap' with the current window
        // try to extend the range [windowStart, windowEnd]
        for (int windowEnd = 0; windowEnd < str.length(); windowEnd++) {
            char rightChar = str.charAt(windowEnd);
            if (charFrequencyMap.containsKey(rightChar)) {
                // decrement the frequency of the matched character
                charFrequencyMap.put(rightChar, charFrequencyMap.get(rightChar) - 1);
                if (charFrequencyMap.get(rightChar) == 0) // character is completely matched
                    matched++;
            }

            if (matched == charFrequencyMap.size())
                return true;

            if (windowEnd >= pattern.length() - 1) { // shrink the window by one character
                char leftChar = str.charAt(windowStart++);
                if (charFrequencyMap.containsKey(leftChar)) {
                    if (charFrequencyMap.get(leftChar) == 0)
                        matched--; // before putting the character back, decrement the matched count
                    // put the character back for matching
                    charFrequencyMap.put(leftChar, charFrequencyMap.get(leftChar) + 1);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String s = "abbcabc";
        String patt = "abc";
        System.out.println(findStringAnagrams(s, patt));
    }

    public static void permute(String s, int l, int r, Set<String> set) {
        if (l == r)
            set.add(s);
        else {
            for (int i = l; i <= r; i++) {
                s = swap(s, l, i);
                permute(s, l + 1, r, set);
                s = swap(s, l, i);
            }
        }

    }

    public static String swap(String s, int i, int j) {
        char[] c = s.toCharArray();
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
        return String.valueOf(c);
    }
}
