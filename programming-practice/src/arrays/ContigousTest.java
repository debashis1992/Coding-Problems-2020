package arrays;

import java.util.*;

public class ContigousTest {
    public static void main(String[] args) {

//        int[] nums={0,1,0,1};
//        int sum=0, max=0;
//        Map<Integer,Integer> map=new HashMap<>();
//        map.put(0, -1); //base case
//        for(int i=0;i<nums.length;i++) {
//            sum+= (nums[i] == 0 ? -1: 1);
//
//            if(map.containsKey(sum)) {
//                max = Math.max(max, i-map.get(sum));
//            }
//            else map.put(sum, i);
//
//        }
//
//        System.out.println(max);


//        TreeNode root=new TreeNode(1);
//        root.left=new TreeNode(3);
//        root.right=new TreeNode(2);
//        root.left.left=new TreeNode(5);
//        root.left.right=new TreeNode(3);
//        root.right.right=new TreeNode(9);
//        Queue<Pair> q=new ArrayDeque<>();
//        q.offer(new Pair(root, 0));
//
//        int max=0;
//
//        while(!q.isEmpty()) {
//            int size=q.size();
//            long min = q.peek().idx;
//
//            long start=0, end=0;
//            for(int i=0;i<size;i++) {
//                Pair p=q.poll();
//                long currIdx = p.idx - min;
//
//                if(i==0) start=currIdx;
//                if(i==size-1) end=currIdx;
//
//                if(p.node.left!=null)
//                    q.offer(new Pair(p.node.left, 2*(int)currIdx));
//                if(p.node.right!=null)
//                    q.offer(new Pair(p.node.right, 2*(int)currIdx+1));
//
//            }
//            max = Math.max(max, (int)(end-start+1));
//
//        }
//        System.out.println("max: "+max);

        Solution3 sn=new Solution3();
        sn.decodeString("3[a2[c]]");
    }
}


class Pair {
    TreeNode node;
    int idx;
    public Pair(TreeNode node, int idx) {
        this.node=node;
        this.idx=idx;
    }

}

class TreeNode {
    int val;
    TreeNode left, right;
    public TreeNode(int val) {
        this.val=val;
    }
}

class Solution3 {
    public String decodeString(String s) {
        Stack<String> st=new Stack<>();
        int n=0;

        for(char c: s.toCharArray()) {
            if(Character.isDigit(c)) {
                n=10*n + (c-'0');
            }
            else {
                if(n > 0) {
                    st.push(String.valueOf(n));
                    n=0;
                }
                if(c == ']') {
                    eval(st);
                }
                else st.push(String.valueOf(c));
            }
        }

        StringBuilder res=new StringBuilder();
        for(Object tmp: st)
            res.append(tmp);

        return res.toString();
    }

    public void eval(Stack<String> st) {
        System.out.println("before: "+st);
        StringBuilder tmp=new StringBuilder();
        while(!st.isEmpty()
                && !st.peek().equals("[")) {
            tmp.append(st.pop());
        }
        if(!st.isEmpty() && st.peek().equals("["))
            st.pop(); //remove [

        String str = tmp.reverse().toString();
        System.out.println("str:"+str);

        int n=1;
        if(!st.isEmpty()) {
            n = Integer.parseInt(st.pop());
        }

        st.push(str.repeat(n));
        System.out.println("after: "+st);
    }
}