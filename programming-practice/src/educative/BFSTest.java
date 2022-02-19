package educative;

import java.util.*;
import java.util.stream.Collectors;

public class BFSTest {
    static class TreeInfo {
        int height;
        int diameter;

        public TreeInfo(int height, int diameter) {
            this.height = height;
            this.diameter = diameter;
        }
    }
    public static List<String> paths = new ArrayList<>();
    public static List<Integer> vals = new ArrayList<>();
    public static int count = 0;
    public static Set<List<String>> path = new LinkedHashSet<>();

    public static String regex = "->";

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

//        for(int i:list)
//            System.out.println(i);

//        System.out.println(sum(root, 23));
//        System.out.println(sum(root, 16));

        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(7);
        root2.right = new TreeNode(9);
        root2.right.left = new TreeNode(2);
        root2.right.right = new TreeNode(9);

//        root2.right.left = new TreeNode(2);
//        root2.right.right = new TreeNode(7);
//
//        System.out.println(allPaths(root2, 12));
        int[] sequence = {1,9,9};
//        System.out.println(findPath(root2, sequence));

        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(0);
        root3.right = new TreeNode(1);
        root3.left.right = new TreeNode(1);
        root3.right.left = new TreeNode(6);
        root3.right.right = new TreeNode(5);

//        System.out.println(findPath(root3, new int[] {1,0,7}));
//        System.out.println(findPath(root3, new int[] {1,1,6}));

        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(7);
        root4.right = new TreeNode(9);
        root4.left.left = new TreeNode(6);
        root4.left.right = new TreeNode(5);
        root4.right.left = new TreeNode(2);
        root4.right.right = new TreeNode(3);

        TreeNode root5 = new TreeNode(12);
        root5.left = new TreeNode(7);
        root5.right = new TreeNode(1);
        root5.left.left = new TreeNode(4);
        root5.right.left = new TreeNode(10);
        root5.right.right = new TreeNode(5);

//        System.out.println(countPaths(root4, 12));
        TreeNode node1 = new TreeNode(3);
        node1.left = new TreeNode(4);
        node1.right = new TreeNode(5);
        node1.left.left = new TreeNode(1);
        node1.left.right = new TreeNode(2);
        node1.left.right.left = new TreeNode(0);

        TreeNode node2 = new TreeNode(4);
        node2.left = new TreeNode(1);
        node2.right = new TreeNode(2);

        System.out.println(isSubtree(node1, node2));
    }

    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(subRoot == null)
            return true;
        if(root == null)
            return false;

        if(root.val == subRoot.val) {
            if(isIdentical(root, subRoot))
                return true;
        }
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean isIdentical(TreeNode root, TreeNode subRoot) {
        if(root == null && subRoot == null)
            return true;
        if(root == null || subRoot == null)
            return false;

        if(root.val == subRoot.val)
            return isIdentical(root.left, subRoot.left) && isIdentical(root.right, subRoot.right);
        return false;
    }

    public static TreeInfo diameter2(TreeNode node) {
        if(node == null)
            return new TreeInfo(0,0);

        TreeInfo left = diameter2(node.left);
        TreeInfo right = diameter2(node.right);

        int height = 1 + Math.max(left.height, right.height);

        int diam1 = left.diameter;
        int diam2 = right.diameter;
        int diam3 = left.height + right.height + 1;

        int diameter = Math.max(Math.max(diam1, diam2), diam3);
        return new TreeInfo(height, diameter);
    }

    public static int maxDepth(TreeNode node) {
        if(node == null)
            return 0;

        return 1+Math.max(maxDepth(node.left), maxDepth(node.right));
    }

    public static int findDiameter(TreeNode node) {
        if(node == null)
            return 0;

        int d1 = findDiameter(node.left);
        int d2 = findDiameter(node.right);
        int d3 = maxDepth(node.left)+maxDepth(node.right)+1;

        return Math.max(d1, Math.max(d2, d3));
    }

    public static int countPaths(TreeNode root, int S) {
        List<Integer> currentPath = new LinkedList<>();
        return countPathsRecursive(root, S, currentPath);
    }

    private static int countPathsRecursive(TreeNode currentNode, int S, List<Integer> currentPath) {
        if (currentNode == null)
            return 0;

        // add the current node to the path
        currentPath.add(currentNode.val);
        int pathCount = 0, pathSum = 0;
        // find the sums of all sub-paths in the current path list
        ListIterator<Integer> pathIterator = currentPath.listIterator(currentPath.size());
        while (pathIterator.hasPrevious()) {
            pathSum += pathIterator.previous();
            // if the sum of any sub-path is equal to 'S' we increment our path count.
            if (pathSum == S) {
                pathCount++;
            }
        }

        // traverse the left sub-tree
        pathCount += countPathsRecursive(currentNode.left, S, currentPath);
        // traverse the right sub-tree
        pathCount += countPathsRecursive(currentNode.right, S, currentPath);

        // remove the current node from the path to backtrack,
        // we need to remove the current node while we are going up the recursive call stack.
        currentPath.remove(currentPath.size() - 1);

        return pathCount;
    }

    public static boolean findPath(TreeNode node, int[] sequence) {
        return paths(node, sequence, "");
    }

    private static boolean paths(TreeNode node, int[] sequence, String path) {
        if(node == null)
            return false;

        if(node.left == null && node.right == null) {
            //check if path is equal to sequence
            path+= node.val;
//            System.out.println("paths: "+path);
            int[] p = Arrays.stream(path.split("->")).collect(Collectors.toList())
                    .stream().mapToInt(x -> Integer.parseInt(x)).toArray();
            return Arrays.equals(p, sequence);
        }

        return paths(node.left, sequence, path.isEmpty() ? node.val+"->" : path+node.val+"->")
                || paths(node.right, sequence, path.isEmpty() ? node.val+"->" : path+node.val+"->");
    }

    public static int sumPath(TreeNode node) {
        vals = new ArrayList<>();
        sumPath(node, 0);
        System.out.println("vals:"+vals);
        return vals.stream().reduce(0, Integer::sum);
    }

    private static void sumPath(TreeNode node, int num) {
        if(node == null)
            return;

        if(node.left == null && node.right == null) {
            num=num*10 + node.val;
            vals.add(Integer.valueOf(num));
        }
        sumPath(node.left, num*10 + node.val);
        sumPath(node.right, num*10 + node.val);
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
