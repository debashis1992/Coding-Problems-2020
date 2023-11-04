package trees;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TraversalTests {
    public static void main(String[] args) {

        TreeNode node=new TreeNode(4);
        node.left=new TreeNode(1);
        node.right=new TreeNode(5);
        node.left.right=new TreeNode(2);

        String s=serialize(node);
        System.out.println(s);

        TreeNode node2 = deserialize(s);
        System.out.println(node2);
    }

    public static void inorder(TreeNode root) {
        TreeNode curr=root;
        Stack<TreeNode> stack=new Stack<>();

        while(curr!=null || !stack.isEmpty()) {
            while(curr!=null) {
                stack.push(curr);
                curr=curr.left;
            }

            curr=stack.pop();
            System.out.println(curr.val);
            curr=curr.right;
        }
    }

    public static void preorder(TreeNode root) {
        // preorder: Root -> L -> R

        TreeNode curr=root;
        Stack<TreeNode> st=new Stack<>();
        st.push(curr);

        while(!st.isEmpty()) {
            TreeNode node=st.pop();
            System.out.println(node.val);
            if(node.right!=null)
                st.push(node.right);
            if(node.left!=null)
                st.push(node.left);
        }
    }

    public static void postorder(TreeNode root) {

    }

    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);

        while(!q.isEmpty()) {
            TreeNode n=q.poll();
            if(n==null)
                sb.append("n->");
            else {
                sb.append(n.val).append("->");
                q.offer(n.left);
                q.offer(n.right);
            }
        }

        return sb.toString();
    }

    public static TreeNode deserialize(String s) {
        if(s==null || s.isEmpty()) return null;

        String[] d=s.split("->");
        System.out.println(Arrays.toString(d));
        Queue<TreeNode> q=new LinkedList<>();

        TreeNode root=new TreeNode(Integer.parseInt(d[0]));
        q.offer(root);
        for(int i=1;i<d.length;i++) {
            TreeNode node = q.poll();
            if(!d[i].equals("n")) {
                node.left = new TreeNode(Integer.parseInt(d[i]));
                q.offer(node.left);
            }
            if(!d[++i].equals("n")) {
                node.right = new TreeNode(Integer.parseInt(d[i]));
                q.offer(node.right);
            }
        }
        return root;
    }
}

class TreeNode {
    int val;
    TreeNode left, right;
    public TreeNode(int val) {
        this.val=val;
    }
}
