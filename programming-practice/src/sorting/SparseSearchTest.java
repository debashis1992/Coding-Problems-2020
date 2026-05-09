package sorting;

public class SparseSearchTest {
    public static void main(String[] args) {

        String[] s = {"at","","","","ball","","","car","","","dad",""};
        Solution3 sn=new Solution3();
        int idx = sn.find(s, "ball");
        System.out.println("index: "+idx);
    }
}

class Solution3 {
    public int find(String[] s, String t) {
        int l=0, h=s.length-1;

        while(l<=h) {
            int mid=l+(h-l)/2;

            if(s[mid].isEmpty()) {
                int left = mid-1, right=mid+1;

                while(true) {
                    if(left<l && right>h) {
                        //out of bounds
                        return -1;
                    }

                    if(left>=l && !s[left].isEmpty()) {
                        mid=left;
                        break;
                    }
                    if(right<=h && !s[right].isEmpty()) {
                        mid=right;
                        break;
                    }

                    left--;
                    right++;
                }

            }
            int cmp = s[mid].compareTo(t);
            if(cmp == 0) return mid;
            else if(cmp < 0) l=mid+1;
            else h=mid-1;
        }

        return -1;

    }
}
