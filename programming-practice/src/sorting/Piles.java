package sorting;

import java.util.*;
import java.util.stream.Collectors;

public class Piles {
    public static void main(String[] args) {
        int[] a= {};
//        merge(a, 0, a.length-1);
//        System.out.println(new Piles().maxCoins(a));
//        System.out.println(Arrays.toString(a));

        int[] arr = {4,3,1,1,3,3,2};
        int k=2;
        System.out.println(new Piles().findLeastNumOfUniqueInts(arr,k));
    }
    public int maxCoins(int[] a) {
        int sum=0;
        int f=0, l=a.length-1;
        while(f<l) {
            sum+= a[++f];
            f++;
            l--;
        }
        return sum;
    }

    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i: arr) {
            if(map.containsKey(i))
                map.put(i, map.get(i)+1);
            else map.put(i,1);
        }
        map = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (old, newValue) -> old, LinkedHashMap::new));

        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(k==0)
                break;
            if(k >= entry.getValue()) {
                k-= entry.getValue();
                entry.setValue(0);
            } else {
                entry.setValue(entry.getValue() - 1);
                k--;
            }
        }
        int count=0;
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue()!=0)
                count++;
        }
        return count;
    }

    public static void merge(int[] a,int start, int end) {
        if(start<end) {
            int middle=(start+end)/2;
            merge(a,start,middle);
            merge(a,middle+1,end);
            sortAndMerge(a, start, middle, end);
        }
    }
    public static void sortAndMerge(int[] a,int start, int middle, int end) {
        int n1=middle-start+1;
        int n2=end-middle;

        int[] left=new int[n1];
        int[] right=new int[n2];

        for(int i=0;i<n1;i++) {
            left[i]=a[start+i];
        }
        for(int i=0;i<n2;i++) {
            right[i] = a[middle+1+i];
        }

        int i=0,j=0,k=start;
        while(i<n1 && j<n2) {
            if(left[i] > right[j])
                a[k++]=left[i++];
            else a[k++]=right[j++];
        }
        while(i<n1) {
            a[k++]=left[i++];
        }
        while(j<n2) {
            a[k++]=right[j++];
        }
    }
}
