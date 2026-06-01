package crackingthecodingint;

import java.util.*;

public class PalindromPairsTest {
    public static void main(String[] args) {

        String[] s = {"","a"};
        Solution4 sn=new Solution4();
        List<List<Integer>> list=  sn.palindromePairs(s);
        System.out.println(list);
    }
}


class Solution4 {
    private List<String> allValidSuffixes(String word) {
        List<String> list=new ArrayList<>();
        for(int i=0;i<=word.length();i++) {
            if(isPalindrome(word.substring(0,i))) {
                list.add(word.substring(i));
            }
        }
        return list;
    }
    private List<String> allValidPrefixes(String word) {
        List<String> list=new ArrayList<>();
        for(int i=0;i<word.length();i++) {
            if(isPalindrome(word.substring(i))) {
                list.add(word.substring(0,i));
            }
        }
        return list;
    }

    private boolean isPalindrome(String s) {
        int i=0, j=s.length()-1;
        while(i<j) {
            if(s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }
    public List<List<Integer>> palindromePairs(String[] words) {
        Set<List<Integer>> res=new HashSet<>();
        Map<String,Integer> map=new HashMap<>();
        for(int i=0;i< words.length;i++) {
            map.put(words[i], i);
        }

        for(String currentWord: map.keySet()) {
            int currentIdx = map.get(currentWord);
            String reverseWord = new StringBuilder(currentWord).reverse().toString();

            //case 1, equal length words
            if(map.containsKey(reverseWord) && map.get(reverseWord) != currentIdx) {
                res.add(List.of(currentIdx, map.get(reverseWord)));
            }

            //case 2, word1 length is shorter than word2
            for(String suffix : allValidSuffixes(currentWord)) {
                String reversedWord = new StringBuilder(suffix).reverse().toString();
                if(map.containsKey(reversedWord) && map.get(reversedWord) != currentIdx)
                    res.add(List.of(map.get(reversedWord), currentIdx));
            }

            //case 3, word1 length is larger than word2
            for(String prefix: allValidPrefixes(currentWord)) {
                String reversedWord = new StringBuilder(prefix).reverse().toString();
                if(map.containsKey(reversedWord) && map.get(reversedWord) != currentIdx)
                    res.add(List.of(currentIdx, map.get(reversedWord)));
            }

        }
        return new ArrayList<>(res);
    }
}