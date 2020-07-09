package searching;

public class ClosestSum {

    public static void main(String[] args) {

        int[] a = {10, 22, 28, 29, 30, 40};
        int[] b = {1,3,4,7,10};
        int y = 15;
        int x = 54;
        findClosestPair(b, y);

    }

    public static void findClosestPair(int[] a, int x) {
        int min_l = 0, min_h = 0, min = Integer.MAX_VALUE;
        int l=0, h=a.length-1;
        while(l<=h) {
            int sum = a[l] + a[h];
            int diff = Math.abs(sum - x);

            if(diff < min) {
                min_l = l;
                min_h = h;
                min = diff;
            }

            if(sum - x > 0)
                h--;
            else l++;
        }

        System.out.println("Pairs : "+a[min_l]+" ,"+a[min_h]);

    }
}
