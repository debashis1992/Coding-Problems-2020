package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        String s="How many eggs are in a half-dozen,13?";
//        s="how many Egg]s are , , , , %12 something i like     ";
        s="i really            like coding %%%, 2323 452 (()#$#$@";
//        s="((( **  2323 3423 ";
        System.out.println(howMany(s));
    }
    public static int howMany(String s) {
        //replace all spaces or multiple spaces with pipes
        s=s.replaceAll("\\s+","|");
        //split by pipes
        String[] str=s.split("\\|");
        System.out.println(Arrays.toString(str));
        List<String> strLing = Arrays.stream(str).collect(Collectors.toList());
        int count=strLing.size();
        for(String sList: strLing) {
            for(int i=0;i<sList.length();i++) {
                int ascii=(int)sList.charAt(i);
                // check ascii if is falling under alphabets
                if(ascii==44 || ascii==46 || ascii==63 || ascii==33)
                    continue;
                if(!((ascii>=65 && ascii<=90) || (ascii>=97 && ascii<=122))) {
                    count--;
                    break;
                }
            }

        }
        return count;
    }
}
