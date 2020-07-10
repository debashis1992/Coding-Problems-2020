package searching;

public class LeetCodeTest {
    public static void main(String[] args) {
        int[] a = {3,1};
        int target = 2;

        System.out.println(findTarget(a, target));
    }

    public static boolean findTarget(int[] a, int target) {
        if(a==null || a.length==0)
            return false;
        int pivot = -1;
        for(int i=0;i+1<a.length;i++) {
            if(a[i] > a[i+1])
            {
                pivot = i;
                break;
            }
        }
        if(pivot!=-1) {
            if(a[0] <= target && target <= a[pivot])
                return findTarget(a, 0, pivot, target);
            else return findTarget(a, pivot+1, a.length-1, target);
        } else return findTarget(a, 0, a.length-1, target);

    }
    public static boolean findTarget(int[] a,int low,int high,int target) {
        if(low <= high) {
            int mid = (low + high)/2;
            if(a[mid] < target)
                return findTarget(a, mid+1, high, target);
            else if(a[mid] > target)
                return findTarget(a, low, mid-1, target);
            else return true;
        }

        return false;
    }
}
