package netcracker;

import java.util.*;

public class InterviewTest {
    public static void main(String[] args) {


//        Length of the longest substring without repeating characters
//        input = abcdae
//        output = bcdae
//
//        input = dddd
//        output = 1
//
//        String input = "abcded";
//        System.out.println(find(input));

//        Node root=new Node(8);
//        root.right=new Node(9);
//        root.right.right=new Node(10);
//        root.right.right.left=new Node(11);
//        root.right.right.left.left=new Node(12);
//
//        flatten(root);
//        print(root);
        int[] nums = {0,1,0,3,2,3};
        int k=3;

//        int[] res = maxSlidingWindow(nums, k);
//        System.out.println(res);

        int res = lengthOfLIS(nums);
    }

    public static int lengthOfLIS(int[] nums) {

        int ans=1, n=nums.length;
        int[] dp=new int[n];
        Arrays.fill(dp, 1);

        for(int i=0;i<n;i++) {
            for(int j=0;j<i;j++) {
                if(nums[j] < nums[i])
                    dp[i] = Math.max(dp[i], dp[j]+1);
                ans = Math.max(ans, dp[i]);
            }
        }

        return ans;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {

        PriorityQueue<int[]> pq=new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        List<Integer> list=new ArrayList<>();

        for(int i=0;i<nums.length;i++) {
            while(!pq.isEmpty() && pq.peek()[1] <= i-k)
                pq.poll();

            pq.offer(new int[]{nums[i], i});
            if(i>=k-1)
                list.add(pq.peek()[0]);

        }

        return list.stream().mapToInt(x -> x).toArray();
    }

    private static void print(Node node) {
        Node c=node;
        while(c!=null) {
            System.out.print(c.data+" -> ");
            c=c.right;
        }
        System.out.println("null");
    }

    public static void flatten(Node node) {
        visit(node);
    }

    private static void visit(Node node) {
        if(node==null)
            return;

        if(node.left==null && node.right==null)
            return;
        if(node.left!=null) {
            visit(node.left);

            Node temp=node.right;
            node.right=node.left;
            node.left=null;
            Node c=node.right;
            while(c.right!=null)
                c=c.right;

            c.right=temp;
        }
        visit(node.right);
    }


    public static int find(String s) {
        if(null == s || s.isEmpty())
            return 0;

        char[] c = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        int start=0, end=0, n = c.length;
        int maxLen = 0;
        while(end < n) {
            if(!map.containsKey(c[end])) {
                map.put(c[end], end);
            }
            else {
                int index = map.get(c[end]);
                while(start <= index) {
                    char ch = c[start];
                    map.remove(ch);
                    start++;
                }
                map.put(c[end], end);
            }
            maxLen = Math.max(maxLen, end-start+1);
            end++;
        }
        return maxLen;
    }
}
