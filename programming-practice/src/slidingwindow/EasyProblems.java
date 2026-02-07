package slidingwindow;

import java.util.Stack;

public class EasyProblems {
    public static void main(String[] args) {
        Solution2 sn=new Solution2();
        int[] nums= {-1};

//        sn.findMaxAverage(nums, 1);
//        System.out.println(Math.max(Integer.MIN_VALUE, -1.0));

        char[] c={'a','b','b','b','b','b','b','b','b','b','b','b','b'};
        sn.compress(c);
    }
}


class Solution2 {
    public double findMaxAverage(int[] nums, int k) {

        int i=0,j=0,n=nums.length;
        double maxi=Integer.MIN_VALUE;
        int sum=0;
        while(j<n) {
            sum+= nums[j];
            while(k < j-i+1 && i<n) {
                sum-= nums[i++];
            }
            if(k == j-i+1) {
                double avg = (double)sum/k;
                maxi=Math.max(maxi, avg);
            }
            j++;
        }

        return maxi;
    }

    public int compress(char[] chars) {

        int i=0,j=0;
        int n=chars.length;
        int len=0;
        int count=1;

        while(j<n) {
            char curr=chars[j];
            count=1;
            while(j+1<n && curr == chars[j+1]) {
                j++;
                count++;
            }
            j=j+1;

            if(count == 1) {
                chars[i++] = curr;
            } else {
                Stack<Integer> st=new Stack<>();
                while(count>0) {
                    st.push(count%10);
                    count=count/10;
                }

                chars[i++] = curr;
                while(!st.isEmpty()) {
                    chars[i++] = (char)(st.pop()+'0');
                }
            }

            len=i;
        }

        return len;
    }
}