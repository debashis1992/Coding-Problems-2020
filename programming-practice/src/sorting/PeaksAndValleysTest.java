package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PeaksAndValleysTest {
    public static void main(String[] args) {
        int[] a={5, 8, 6, 2, 3, 4, 6};
        Solution5 sn=new Solution5();
        int[] res=sn.find(a);

        System.out.println(Arrays.toString(res));
    }
}

class Solution5 {
    public int[] find(int[] a) {
        int[] res=new int[a.length];

        Arrays.sort(a);
        int i=0,j=a.length-1,k=0;
        while(i<j) {
            res[k++] = a[j--];
            res[k++] = a[i++];
        }
        res[k] = a[i];
        return res;
    }
}

class Country {
    String continent;
    double getPopulation() {
        return new Random().nextDouble(1000);
    }

    public double getPopulation(List<Country> countries, String continent) {
        return countries.stream().filter(country -> country.continent.equals(continent))
                .mapToDouble(Country::getPopulation).sum();
    }
}