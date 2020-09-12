package codeforces.practice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartitionLabelTest {
    public static void main(String[] args) {
        String s = "qiejxqfnqceocmy";
        System.out.println(partitionLabels(s));
    }

    public static List<Integer> partitionLabels(String s) {
        Map<Character, List<Integer>> map = new HashMap<>();
        for(int i=0;i<s.length();i++) {
            if(map.containsKey(s.charAt(i))) {
                map.get(s.charAt(i)).add(i);
            } else {
                List<Integer> list=new ArrayList<>();
                list.add(i);
                map.put(s.charAt(i), list);
            }
        }

        List<Integer> groupedItems = new ArrayList<>();
        for(int i=0;i<s.length();) {
            char c=s.charAt(i);
            List<Integer> occ=map.get(c);
            int f=occ.get(0);
            int l=occ.get(occ.size()-1);
            int start=f, end=l;
            for(int j=f+1;j<l;j++) {
                char c2 = s.charAt(j);
                List<Integer> occ2=map.get(c2);
                int l2=occ2.get(occ2.size()-1);
                if(l2>end) {
                    end=l2;
                    l=end;
                }
            }
            i=end+1;
            groupedItems.add(end-start+1);
        }
        return groupedItems;
    }
}
