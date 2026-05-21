package crackingthecodingint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SymTest {
    public static void main(String[] args) {

        Solution sn=new Solution();
        TreeNode root=new TreeNode(1);
        root.left=new TreeNode(2);
        root.right=new TreeNode(2);
        root.left.left=new TreeNode(3);
        root.left.right=new TreeNode(4);
        root.right.left=new TreeNode(4);
        root.right.right=new TreeNode(3);

        sn.isSymmetric(root);
    }
}

class Solution {
    public boolean isSymmetric(TreeNode root) {

        if(root==null) return true;
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);

        while(!q.isEmpty()) {
            int s=q.size();
            List<Integer> list=new ArrayList<>();
            for(int i=0;i<s;i++) {
                TreeNode node=q.poll();
                if(node==null)
                    list.add(-1000);
                else list.add(node.val);

                if(node!=null) {
                    q.offer(node.left);
                    q.offer(node.right);
                }

            }
            if(!isEqual(list))
                return false;
        }

        return true;

    }

    private boolean isEqual(List<Integer> list) {
        int i=0,j=list.size()-1;
        while(i<j) {
            if(list.get(i++).intValue() !=  list.get(j--).intValue())
                return false;
        }
        return true;
    }
}