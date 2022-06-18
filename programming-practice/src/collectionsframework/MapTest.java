package collectionsframework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTest {
    public static void main(String[] args) {
        //a-z = 97-122
        String s = "java";
        //size = n
        int[] ar = new int[26];
        System.out.println(Arrays.toString(ar));

        //loop = 10^6 == n
        //O(n)
        for(int i=0;i<s.length();i++) {
            char ch = s.charAt(i);
            ar[ch - 'a']++;
        }

        for(int i=0;i<ar.length;i++) {
            if(ar[i] > 0) {
                System.out.println("key: "+(char)(i+'a')+", value: "+ar[i]);
            }
        }

        //print frequency of each character
        //string s = "java"
        //map = {}
        //map.get('j') --> null

        String s2 = "nano";
        //key-> char, value -> frequency
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0;i<s2.length();i++) {
            char ch = s2.charAt(i); //key
            int freq = map.getOrDefault(ch, 0);

            map.put(ch, freq+1);
        }
        System.out.println(map);

        //i=2, key = 'v', freq = map.get('v') = 0
        //map = {'j':1, 'a': 2, 'v':1, }

        //dealing with employee payslips
        // employee -> 10000
        //each employee has 12 month payslip

        //total payslip = 12 * 1000 = 12000, 12K

//        "select * from emp";
//        list = [{}, {}, {}, {}, {}] //12k
//
//        // find all payslips for each employee
//        {
//            emp1: [{}, {}, {}] //12 months
//            ...
//            ...
//            ...
//            emp5: [{}, {}, {}]
//        }
//
//        Map<Integer, List<Payslip>> map = new HashMap<>();
//        List<Payslip> payslipList = map.get(5);

        String name = "codinginjava";
        Map<String, Long> map2 =
                Arrays.stream(name.split("")).
                        collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        System.out.println(map2);

        //{a=1, n=2, o=1} // list of entries
        //Entry<Key,Value>

        for(Map.Entry<String, Long> entry: map2.entrySet()) {
            System.out.println("key: "+entry.getKey()+", value: "+entry.getValue());
        }

    }
}
