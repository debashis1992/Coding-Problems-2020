package dp;

public class SubsetProblem {
    public boolean[][] t;
    public static void main(String[] args) {
//        System.out.println(findSubSet(new int[]{3,34,4,12,2}, 19));
        int[] ar = {2,3,7,8,10};
        int sum = 3;
        System.out.println(findSubSet(ar, ar.length, sum));
        System.out.println(new SubsetProblem().findSubSetUsingTopDown(ar, ar.length, sum));
    }
    public static boolean findSubSet(int[] ar,int sum) {
        int currSum=0;
        for(int i=0;i<ar.length;i++) {
            if(ar[i] + currSum > sum) continue;
            currSum+= ar[i];
            if(currSum==sum)
                return true;
        }
        return false;
    }

    public static boolean findSubSet(int[] ar,int n,int sum) {
        if(sum == 0) return true;
        if(n==0 && sum > 0) return false;
        if(ar[n-1] > sum)
            return findSubSet(ar, n-1, sum);
        else return findSubSet(ar, n-1, sum - ar[n-1])
                || findSubSet(ar, n-1, sum);
    }

    public boolean findSubSetUsingTopDown(int[] ar,int n, int sum) {
        initialize(n, sum);
        for(int i=1;i<n+1;i++) {
            for(int j=1;j<sum+1;j++) {
                if(ar[i-1] > j)
                    t[i][j] = t[i-1][j];
                else t[i][j] = t[i-1][j-ar[i-1]] || t[i-1][j];
            }
        }
        return t[n][sum];
    }
    public void initialize(int n, int sum) {
        t = new boolean[n+1][sum+1];
        for(int i=0;i<n+1;i++) {
            for(int j=0;j<sum+1;j++) {
                if(j==0)
                    t[i][j] = true;
                if(i==0 && j>0)
                    t[i][j] = false;
            }
        }
    }
}
