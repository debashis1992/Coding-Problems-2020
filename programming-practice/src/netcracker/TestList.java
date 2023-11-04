package netcracker;

import java.util.*;
import java.util.stream.Collectors;

public class TestList {
    public static void main(String[] args) {
        //input_String = "abbcfba" , distinct = [a,b,c,f]


        // "abbcffba"
        // output: cfba

        // "abbcfaab"
        // output: bcfa
        // shortest substring that has all the distinct elements

        String input_String = "abbcfba" ;
        String input_String2 = "abbcffba" ;
        String input_String3 = "abbcfaab" ;
        String input_String4 = "abccccccccccccfffffaabcfffffffff";

        String[] inputs = {
                "abbcfba",
                "abbcffba",
                "abbcfaab",
                "abccccccccccccfffffaabcfffffffff"
        };

        char[] distinct = {'a','b','c','f'};
        for(String input: inputs) {
            System.out.println(shortestSubstring(input, distinct));
        }

    }

    public static String shortestSubstring(String s, char[] distinct) {

        if(null == s || s.isEmpty())
            return null;
        char[] c=s.toCharArray();

        Map<Character,Integer> map = new HashMap<>();
        int min=s.length();
        String ans=null;
        int i=0,j=0;
        while(j < c.length) {
            map.put(c[j], map.getOrDefault(c[j], 0)+1);

            while(distinct.length == map.size()) {
                char start = c[i];
                if(map.get(start) == 1)
                    map.remove(start);
                else map.put(start, map.get(start)-1);
                if(min > (j-i+1)) {
                    min = j-i+1;
                    ans = s.substring(i, j+1);
                }
                i++;
            }

            j++;
        }

        return ans;

    }





}
