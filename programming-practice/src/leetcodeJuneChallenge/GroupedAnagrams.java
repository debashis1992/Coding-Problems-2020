package leetcodeJuneChallenge;

import java.util.*;
import java.util.stream.Collectors;

public class GroupedAnagrams {
    public static void main(String[] args) {
        String[] s = {"eat", "tea", "tan", "ate", "nat", "bat"};
        String[] s2 = {"abc","def","fad","bca","xyz","efd"};
        List<List<String>> finalList =
                findGroupedAnagrams(s2);
        System.out.println(finalList);

    }
    public static List<List<String>> findGroupedAnagrams(String[] s) {
        List<String> sortedList = Arrays.stream(s).map(x -> {
            char[] c = x.toCharArray();
            Arrays.sort(c);
            return new String(c);
        }).collect(Collectors.toList());

        String[] sortedArr = sortedList.toArray(new String[0]);
        Map<String,List<String>> map = new HashMap<>();
        for(int i=0;i<sortedArr.length;i++) {
            List<String> list = null;
            if(map.get(sortedArr[i])==null) {
                list = new ArrayList<>();
            } else {
                list = map.get(sortedArr[i]);
            }
            list.add(s[i]);
            map.put(sortedArr[i], list);
        }
//        System.out.println(map);
        List<List<String>> finalList = map.entrySet().stream().map(x -> x.getValue()).collect(Collectors.toList());
        return finalList;

    }


}
