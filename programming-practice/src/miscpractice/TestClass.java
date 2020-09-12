package miscpractice;

import java.util.Arrays;

public class TestClass {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        System.out.println(testClass.compareVersion("2.2200.4.5342.200","2.2200.4.5342.201" ));
    }
        public int compareVersion(String v1, String v2) {
            String[] s1=v1.indexOf(".")!=-1 ? v1.split("\\.") : new String[]{v1};
            String[] s2=v2.indexOf(".")!=-1 ? v2.split("\\.") : new String[]{v2};

            removeLeadingZeros(s1);
            removeLeadingZeros(s2);

    //        System.out.println(Arrays.toString(s1)+" "+Arrays.toString(s2));
            int s1len=s1.length, s2len=s2.length;

            int result=0;
            int i=0;
            for(;i<Math.min(s1len,s2len);) {
                if(Integer.parseInt(s1[i]) > Integer.parseInt(s2[i]))
                    return 1;
                if(Integer.parseInt(s1[i]) < Integer.parseInt(s2[i]))
                    return -1;
                i++;
            }
            while(i<s1len) {
                if(!s1[i].equals("") && Integer.parseInt(s1[i]) !=0)
                    return 1;
                i++;
            }
            while(i<s2len) {
                if(!s2[i].equals("") && Integer.parseInt(s2[i])!=0)
                    return -1;
                i++;
            }
            return 0;
        }

        public void removeLeadingZeros(String[] s) {
            for(int i=0;i<s.length;i++) {
                if(s[i].length()>1 && s[i].indexOf("0")!=-1 && s[i].charAt(0)=='0') {
                    int start=0, end=0;
                    for(int j=1;j<s[i].length();j++) {
                        if(s[i].charAt(j)=='0') end=j;
                        else break;
                    }
                    if(end==s[i].length()-1) s[i]="0";
                    else s[i] = s[i].substring(++end);
                }
            }
        }
}
