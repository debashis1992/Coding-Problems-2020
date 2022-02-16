package educative;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSTest {
    public static List<String> paths = new ArrayList<>();
    public static void main(String[] args) {
//        TreeNode node = new TreeNode(1);
//        node.left = new TreeNode(2);
//        node.left.left = new TreeNode(4);
//        node.left.right = new TreeNode(5);
//
//        node.right = new TreeNode(3);
//        node.right.left = new TreeNode(6);
//        node.right.right = new TreeNode(7);
//        node.right.right.right = new TreeNode(10);
//        TreeNode root = new TreeNode(12);
//        root.left = new TreeNode(7);
//        root.right = new TreeNode(1);
//        root.right.left = new TreeNode(10);
//        root.right.right = new TreeNode(5);
//        System.out.println("Tree Minimum Depth: " + findDepth(root));
//        root.left.left = new TreeNode(9);
//        root.right.left.left = new TreeNode(11);
//        System.out.println("Tree Minimum Depth: " + findDepth(root));

        TreeNode root = new TreeNode(12);
        root.left = new TreeNode(7);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(9);
        root.right.left = new TreeNode(10);
        root.right.right = new TreeNode(5);
//        root.left.left.left = new TreeNode(3);
        List<Integer> list = rightView(root);

        for(int i:list)
            System.out.println(i);

        System.out.println(sum(root, 23));
        System.out.println(sum(root, 16));

        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(7);
        root2.right = new TreeNode(9);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(5);

        root2.right.left = new TreeNode(2);
        root2.right.right = new TreeNode(7);

        System.out.println(allPaths(root2, 12));
    }

    public static boolean sum(TreeNode node, int x) {
        if (node == null)
            return false;

        // if the current node is a leaf and its value is equal to the sum, we've found a path
        if (node.val == x && node.left == null && node.right == null)
            return true;

        // recursively call to traverse the left and right sub-tree
        // return true if any of the two recursive call return true
        return sum(node.left, x - node.val) || sum(node.right, x - node.val);
    }

    public static List<List<Integer>> allPaths(TreeNode node, int x) {
        paths = new ArrayList<>();
        path(node, x, "");
        List<List<Integer>> list = new ArrayList<>();
        for(String p: paths) {
            List<Integer> li = new ArrayList<>();
            String[] temp = p.split(",");
            for(String s: temp)
                li.add(Integer.valueOf(s));
            list.add(li);
        }
        return list;
    }

    private static boolean path(TreeNode node, int x, String path) {
        if(node == null)
            return false;

        if(node.val == x && node.left == null && node.right == null) {
            path=path+","+node.val;
            paths.add(path);
            return false;
        }
        return path(node.left, x - node.val, path.isEmpty() ? String.valueOf(node.val) : path+","+node.val) ||
            path(node.right, x - node.val, path.isEmpty() ? String.valueOf(node.val) : path+","+node.val);
    }

    public static List<List<Integer>> levelOrderTraversal(TreeNode node) {
        if(node==null)
            return null;

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);

        while(!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            for(int i=0;i<size;i++) {
                TreeNode treeNode = queue.poll();
                list.add(treeNode.val);

                if(treeNode.left!=null)
                    queue.offer(treeNode.left);
                if(treeNode.right!=null)
                    queue.offer(treeNode.right);
            }

            result.add(0, list);
        }
        return result;
    }

    public static List<List<Integer>> zigzag(TreeNode node) {
        if(node==null)
            return null;

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);


        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            ++level;
            List<Integer> list = new ArrayList<>();
            for(int i=0;i<size;i++) {
                TreeNode treeNode = queue.poll();
                if(level%2!=0)
                    list.add(treeNode.val);
                else list.add(0, treeNode.val);

                if(treeNode.left!=null)
                    queue.offer(treeNode.left);
                if(treeNode.right!=null)
                    queue.offer(treeNode.right);
            }

            result.add(list);
        }
        return result;
    }

    public static int findDepth(TreeNode node) {
        if(node==null)
            return 0;
        if(node.left==null && node.right==null)
            return 1;
        if(node.left!=null && node.right!=null)
            return 1 + Math.min(findDepth(node.left), findDepth(node.right));
        if(node.left==null)
            return 1+findDepth(node.right);
        if(node.right==null)
            return 1+findDepth(node.left);

        return 1 + Math.min(findDepth(node.left), findDepth(node.right));
    }

    public static List<Integer> rightView(TreeNode node) {
        if(node == null)
            return new ArrayList<>();

        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(node);

        while(!q.isEmpty()) {
            int size = q.size();

            for(int i=0;i<size;i++) {
                TreeNode n = q.poll();
                //get the last node from each level
                //similarly, for left view, it should be the first node from each level
                if(i == size-1)
                    result.add(n.val);

                if(n.left!=null)
                    q.offer(n.left);
                if(n.right!=null)
                    q.offer(n.right);

            }
        }
        return result;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode() {
    }
    public TreeNode(int val) {
        this.val=val;
    }
}
