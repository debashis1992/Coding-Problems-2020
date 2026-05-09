package sorting;

public class SortedRotatedTest {
    public static void main(String[] args) {

        Solution2 sn=new Solution2();
        int[] a={15,16,19,20,25,1,3,4,5,7,10,14};
        int find = sn.find(a, 1);
        System.out.println("idx: "+find);

    }
}

class Solution2 {
    public int find(int[] a, int t) {
        return find(a,t,0,a.length-1);
    }
    public int find(int[] a, int t, int l, int h) {
        while(l<=h) {
            int mid = l+(h-l)/2;
            if(a[mid] == t) return mid;

            if(a[l] <= mid) {
                if(a[l] <= t && t < a[mid])
                    h=mid-1;
                else l=mid+1;
            } else {
                if(a[mid] >= t && t < a[h])
                    l=mid+1;
                else h=mid-1;
            }
        }
        return -1;
    }
}