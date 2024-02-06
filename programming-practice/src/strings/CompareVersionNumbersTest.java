package strings;

public class CompareVersionNumbersTest {
    static int mod=(int)1e9+7;
    public static void main(String[] args) {

        String s1="8.88888888888888888888888888888", s2="8.888888888888888";
        long num1=0,num2=0;
        int i=0,j=0;
        while(i<s1.length() || j<s2.length()) {

            while(i<s1.length() && s1.charAt(i)!='.') {
                num1 = (num1 * 10 + (s1.charAt(i) - '0'))%mod;
                i++;
            }

            while(j<s2.length() && s2.charAt(j)!='.') {
                num2=(num2*10 + (s2.charAt(j)-'0'))%mod;
                j++;
            }
            System.out.println(num1);
            System.out.println(num2);

            if(num1 < num2) {
                System.out.println(-1);
                break;
            }
            if(num1 > num2) {
                System.out.println(1);
                break;
            }
            num1=num2=0;
            i++;
            j++;
        }

    }
}
