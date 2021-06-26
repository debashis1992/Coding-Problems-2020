package arrays;

public class NewTest {
    public void rotate(long arr[], long n)
    {
        long temp=arr[(int) (n-1)];
        for(long i=n-1;i>0;i--)
            arr[(int) i]=arr[(int) (i-1)];
        arr[0]=temp;

    }

}
