package sorting;

public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {

        int[] a={1,3,4,7,10};
        int[] b={2,3,6,15};

        System.out.println(median(a,b));
    }

    public static double median(int[] a,int[] b) {

        int n1=a.length;
        int n2=b.length;
        if(n1 > n2)
            return median(b,a);
        int totalLength = (n1+n2+1)/2;
        int low=0,high=n1;
        while(low<=high) {
            int c1 = (low+high)>>1;
            int c2 = totalLength - c1;

            int l1 = c1==0 ? Integer.MIN_VALUE : a[c1-1];
            int r1 = c1==n1 ? Integer.MAX_VALUE : a[c1];

            int l2 = c2==0 ? Integer.MIN_VALUE : b[c2-1];
            int r2 = c2==n2 ? Integer.MAX_VALUE : b[c2];

            if(l1<=r2 && l2<=r1) {
                if((n1+n2)%2==0)
                    return (Math.max(l1,l2) + Math.min(r1,r2))/2.0;
                else return Math.max(l1,l2);
            }
            else if(l1>r2)
                high=c1-1;
            else low=c1+1;
        }
        return -1.0;
    }
}
