package miscpractice;

public class SumRootToLeftBinary {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(0);
        node.left.left = new TreeNode(0);
        node.left.left.left = new TreeNode(0);
        node.left.right = new TreeNode(1);
        node.right = new TreeNode(1);
        node.right.left = new TreeNode(0);
        node.right.right = new TreeNode(1);

        System.out.println(new SumRootToLeftBinary().findSum(node));
    }
    private boolean isLeafNode(TreeNode node) {
        if(node!=null && node.left==null && node.right==null) return true;
        return false;
    }
    public int sumOfNodes(String root, String child) {
        String res=root+child;
        return Integer.parseInt(res);
    }
    public int findSum(TreeNode node) {
        if(isLeafNode(node)) return node.val;
        return findSum(node, 0, String.valueOf(node.val));
    }

    public int findSum(TreeNode node, int sum, String root){
        if(node==null) return 0;
        if(isLeafNode(node.left))
            sum+= sumOfNodes(root, String.valueOf(node.left.val));
        if(isLeafNode(node.right))
            sum+= sumOfNodes(root, String.valueOf(node.right.val));
        if(node.left!=null)
            sum+= findSum(node.left,0, String.valueOf(root)+String.valueOf(node.left.val));
        if(node.right!=null)
            sum+= findSum(node.right,0, String.valueOf(root)+String.valueOf(node.right.val));

        return sum;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int data) {
        this.val = data;
    }
}