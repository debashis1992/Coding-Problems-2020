package backtracking;

import java.util.ArrayList;
import java.util.List;

public class IpAddressTest {
    public static void main(String[] args) {
        List<String> list= new Solution().restoreIpAddresses("101023");
        System.out.println(list);
    }
}


class Solution {
    List<String> res;
    public List<String> restoreIpAddresses(String s) {

        res=new ArrayList<>();
        int n=s.length();
        f(s, 0, "", 0, n);
        return res;
    }

    public void f(String s, int index, String ip, int dots, int n) {
        //base case
        if(dots==3) {
            if(isvalid(s.substring(index))) {
                ip+= s.substring(index);
                res.add(ip);
            }

            return;
        }

        for(int i=index;i<n;i++) {
            String newNumber= s.substring(index, i+1);
            if(isvalid(newNumber)) {
                int k = newNumber.length();
                ip += newNumber + ".";

                f(s, i + 1, ip, dots + 1, n);
                //backtrack
                ip = ip.substring(0, ip.length() - k - 1);
            }
        }
    }

    public boolean isvalid(String s) {
        if(s.length() > 3 || s.length()==0 || (s.charAt(0)=='0' && s.length()>1) || Integer.parseInt(s) > 255)
            return false;
        return true;
    }
}