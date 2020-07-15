package searching;

public class FindMissingPositive {

    public static void main(String[] args) {
        int[] a = {3,4,-1,1};
        System.out.println(firstMissingPositive(a));
    }

    public static int firstMissingPositive(int[] nums) {
        if(nums.length==0) return 1;
        for(int i=0;i<nums.length;){
            if(nums[i]>nums.length || nums[i]<=0){           //only positive values
                i++;
                continue;
            }else if(nums[nums[i]-1]==nums[i]){    //checking whether the element is at its position or not
                i++;
            }
            // swapping the element to its desired position
            else{

                int temp=nums[nums[i]-1];
                nums[nums[i]-1]=nums[i];
                nums[i]=temp;
            }
        }
        // finding the smallest positive element out of its position
        int i=0;
        for(i=0;i<nums.length;i++){
            if(nums[i]!=i+1) return i+1;
        }

        return nums[i-1]+1;
    }
}
