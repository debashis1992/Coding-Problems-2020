package searching;

public class PeakElement {

    public static void main(String[] args) {

        int[] a = {20,10,20,15};
        int[] arr = {5,7,8,9,10, 1 ,2};
        System.out.println(findMin(arr));
    }
    public static int findMin(int[] arr) {
        return findMin(0, arr.length - 1, arr);
    }
    private static int findMin(int l, int h, int[] a) {
        int mid = -1;
        while(l<h) {
            if(h==l+1 && a[l] < a[h])
                return l;
            mid = (l+h)/2;
            if(a[mid] > a[l] && a[mid] > a[h])
                l = mid+1;
            else if(a[mid] > a[l] && a[mid] < a[h]) {
                h = mid - 1;
            } else if(a[mid] < a[l] && a[mid] < a[h])
                return mid;
        }
        return mid;
    }


    public static int findPeakElement(int[] a) {
        return findPeakElement(0, a.length-1, a);
    }
    public static int findPeakElement(int l, int h, int[] a) {
        int mid = (l+h)/2;
        if((mid==0 && a[mid] > a[mid+1])
                || (mid == a.length-1 && a[mid] > a[mid-1])
                || ((a[mid] > a[mid-1]) && (a[mid]> a[mid+1])) )
            return mid;
        else if(a[mid-1] > a[mid])
            return findPeakElement(l, mid-1,a);
        else if(a[mid] < a[mid+1])
            return findPeakElement(mid+1, h, a);
        else return -1;
    }
}
