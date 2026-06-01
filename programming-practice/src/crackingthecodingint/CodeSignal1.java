package crackingthecodingint;

import java.util.Arrays;

public class CodeSignal1 {
    public static void main(String[] args) {

        String s = "112+422";

        String[] ar=s.split("\\+");
        System.out.println(Arrays.toString(ar));

        long minval = Long.MAX_VALUE;
        String ans = "";

        String left = ar[0];
        String right = ar[1];

        for(int i=0;i<left.length();i++) {
            String m1Str = left.substring(0, i);
            String add1Str = left.substring(i);

            long m1 = m1Str.isEmpty() ? 1 : Long.parseLong(m1Str);
            long add1 = Long.parseLong(add1Str);

            for(int j=1;j<=right.length();j++) {
                String add2Str = right.substring(0, j);
                String m2Str = right.substring(j);

                long add2 = Long.parseLong(add2Str);
                long m2 = m2Str.isEmpty() ? 1 : Long.parseLong(m2Str);

                long currentVal = m1 * (add1 + add2) * m2;
                if(currentVal < minval) {
                    minval = currentVal;
                    ans = m1Str  +"*("+ add1Str+"+"+add2Str+")*"+ m2Str;
                }

            }
        }

        System.out.println(minval);
        System.out.println(ans);

    }
}
