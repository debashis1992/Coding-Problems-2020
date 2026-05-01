package crackingthecodingint;

public class ValidateBSTTest {
    public static void main(String[] args) {

        TreeNode root=new TreeNode(50);
        root.left=new TreeNode(30);
        root.right=new TreeNode(70);
        root.left.left=new TreeNode(20);
        root.left.right=new TreeNode(40);
        root.left.left.left=new TreeNode(10);

        root.right.left=new TreeNode(60);
        root.right.right=new TreeNode(85);

        root.right.left.left=new TreeNode(55);
        root.right.left.right=new TreeNode(65);

        root.right.right.right=new TreeNode(90);

        System.out.println(validateBST(root));
        System.out.println(lca(root, root.left, root.left.right));

    }

    public static boolean validateBST(TreeNode root) {
        return validateBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static boolean validateBST(TreeNode node, int min, int max) {
        if(node==null) return true;
        if(node.val >= max || node.val <= min) return false;

        boolean left = validateBST(node.left, min, node.val);
        boolean right = validateBST(node.right, node.val, max);

        return left && right;
    }

    public static TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
        if(node==null || node==p || node==q) return node;

        TreeNode left=lca(node.left, p, q);
        TreeNode right=lca(node.right, p, q);

        if(left!=null && right!=null) return node;
        if(left!=null) return left;

        return right;
    }

    public static int countFromNode(TreeNode root, int t) {
        if(root == null) return 0;

        return pathSum(root, t, 0)
                + countFromNode(root.left, t)
                + countFromNode(root.right, t);


    }

    public static int pathSum(TreeNode root, int t, int sum) {
        if(root==null) return 0;

        sum+= root.val;
        int c = (sum == t) ? 1: 0;

        c+= pathSum(root.left, t, sum);
        c+= pathSum(root.right, t, sum);

        return c;
    }
}

class TreeNode {
    int val;
    TreeNode left, right;
    public TreeNode(int val) {
        this.val=val;
    }
}
