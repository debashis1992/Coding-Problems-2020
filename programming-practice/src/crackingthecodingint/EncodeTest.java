package crackingthecodingint;

import java.util.ArrayList;
import java.util.List;

public class EncodeTest {
    public static void main(String[] args) {
        Codec c=new Codec();
        List<String> list=List.of("hello", "abc", "x##y");
        String s = c.encode(list);
        System.out.println(s);

        List<String> decodeList=c.decode(s);
        System.out.println(decodeList);
    }
}

class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb=new StringBuilder();
        for(String s: strs) {
            sb.append(s.length())
                    .append("#")
                    .append(s);
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> list=new ArrayList<>();

        int i=0;
        while(i<s.length()) {
            int j=i;
            while(s.charAt(j)!='#')
                j++;

            int len = Integer.parseInt(s.substring(i,j));
            j++; //skip #

            String ns=s.substring(j, j+len);
            list.add(ns);
            i=j+len;
        }
        return list;

    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));