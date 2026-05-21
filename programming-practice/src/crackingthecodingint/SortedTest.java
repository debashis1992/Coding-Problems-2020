package crackingthecodingint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortedTest {
    public static void main(String[] args) {
        int[] nums={4,5,6,7,0,1,2};
        int idx = findMax(nums);
        System.out.println(idx);

        List<String> list=new ArrayList<>();
        list.add("hello");
        list.add("wor||d");
        list.add("aa2/bb");

        String out = list.stream()
//                .map(s -> s.replaceAll("/:", "@"))
                .collect(Collectors.joining("/"));
        System.out.println(out);

        String[] in = out
//                .replaceAll("@","/:")
                .split("/");
        System.out.println(Arrays.toString(in));


    }

    public static int findMax(int[] nums) {
        int f=0, l=nums.length-1;
        while(f<l) {
            int mid=f+(l-f)/2;
            if(nums[f] <= nums[mid]) {
                if(mid+1 < nums.length && nums[mid] < nums[mid+1])
                    f=mid+1;
                else l=mid;
            }
            else l=mid-1;
        }
        return l;
    }
}
