package binarysearchtree;

import java.util.*;
import java.util.stream.Collectors;

public class BinarySearchTreeTest {
    public static void main(String[] args) {
        MyBinarySearchTree tree = new MyBinarySearchTree();
//        tree.insert(100);
//        tree.insert(500);
//        tree.insert(700);
//        tree.insert(20);
//        tree.insert(40);
//        tree.insert(30);

        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(40);

        tree.inorder();

        System.out.println(tree.search(20));
//        tree.remove(20);
//        tree.inorder();
//        tree.update(100,50);
        tree.inorder();
        System.out.println("height: "+tree.findHeight());
        tree.levelOrder();

        int[] ar = {8,2,4,10,7};
        mergeSort(0, ar.length-1, ar);
        System.out.println(Arrays.toString(ar));
        BinaryTree bTree = createBinaryTreeForTest(ar);
        tree.convertBTtoBST(bTree, ar);
        bTree.preorder(bTree.root);

        System.out.println("----------");
        int[] arr = new int[]{-10, -3, 0, 5, 9};
        Node node = MyBinarySearchTree.sortedArrToBST(arr);
        MyBinarySearchTree.inorderStatic(node);

    }

    private static BinaryTree createBinaryTreeForTest(int[] ar) {
        BinaryTree binaryTree = new BinaryTree(10);
        binaryTree.root.left = new Node(2);
        binaryTree.root.left.left = new Node(8);
        binaryTree.root.left.right = new Node(4);

        binaryTree.root.right = new Node(7);
        return binaryTree;
    }

    private static void mergeSort(int start, int end, int[] ar) {
        if(start < end) {
            int mid = (start+end)/2;
            mergeSort(start, mid, ar);
            mergeSort(mid+1, end, ar);
            merge(start, mid, end, ar);
        }
    }

    private static void merge(int start, int mid, int end, int[] ar) {
        int n1 = mid-start+1;
        int n2 = end-mid;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for(int i=0;i<n1;i++)
            left[i] = ar[start+i];
        for(int i=0;i<n2;i++)
            right[i] = ar[mid+1+i];

        int i=0,j=0,k=start;
        while(i<n1 && j<n2) {
            if(left[i] < right[j])
                ar[k++] = left[i++];
            else ar[k++] = right[j++];
        }

        while(i<n1)
            ar[k++] = left[i++];
        while(j<n2)
            ar[k++] = right[j++];
    }
}

class BinaryTree {
    Node root;
    public BinaryTree(int val) {
        root = new Node(val);
    }
    public void inorder(Node node) {
        if(node!=null) {
            inorder(node.left);
            System.out.println(node.value);
            inorder(node.right);
        }
    }

    public void preorder(Node node) {
        if(node!=null) {
            System.out.println(node.value);
            preorder(node.left);
            preorder(node.right);
        }
    }
}

class MyBinarySearchTree {
    private static String TREE_EMPTY = "TREE EMPTY";
    Node root;

    private void print(Node node) {
        System.out.println(node.value);
    }

    private boolean isLeafNode(Node node) {
        return node.left==null && node.right == null;
    }

    private boolean hasSingleChild(Node node) {
        return node.left == null || node.right == null;
    }

    public Node insert(int val) {
        Node node = new Node(val);

        if(root == null) {
            root = node;
            return root;
        } else return insert(node, root);
    }

    private Node insert(Node node, Node root) {
        if(root == null) {
            root = node;
            return root;
        }

        if(root.value < node.value)
            root.right = insert(node, root.right);
        else root.left = insert(node, root.left);

        return root;
    }

    public void inorder() {
        if(root != null) {
            inorder(root);
        }
        else System.out.println(TREE_EMPTY);
    }

    private void inorder(Node node) {
        if(node!=null) {
            inorder(node.left);
            print(node);
            inorder(node.right);
        }
    }

    public void preorder() {
        if(root !=null)
            preorder(root);
        else System.out.println(TREE_EMPTY);
    }

    private void preorder(Node node) {
        if(node!=null) {
            print(node);
            preorder(node.left);
            preorder(node.right);
        }
    }

    public void postorder() {
        if(root !=null)
            postorder(root);
        else System.out.println(TREE_EMPTY);
    }

    private void postorder(Node node) {
        if(node!=null) {
            postorder(node.left);
            postorder(node.right);
            print(node);
        }
    }

    public void levelOrder() {
        if(root == null)
            return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            Node node = queue.poll();
            print(node);

            if(node.left!=null)
                queue.add(node.left);
            if(node.right!=null)
                queue.add(node.right);
        }
    }

    public boolean search(int val) {
        if(root == null)
            return false;

        return search(val, root);
    }

    private boolean search(int val, Node node) {
        if(node == null)
            return false;

        if(node.value == val)
            return true;

        if(node.value < val)
            return search(val, node.right);
        else return search(val, node.left);
    }

    private int minValue(Node node) {
        int min = node.value;
        while(node.left!=null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }

    public void remove(int val) {
        if(root == null)
            return;

        remove(val, root);
    }
    private Node remove(int val, Node node) {
        if(node == null)
            return null;

        //value found
        if(node.value == val) {
            //if node is a leaf node
            if(isLeafNode(node))
                return null;

            else if(hasSingleChild(node)) {
                if(node.left!=null)
                    return node.left;
                else return node.right;
            }

            else {
                //find the next min element from the right side of the subtree
                node.value = minValue(node.right);

                //delete the min value from the bst
                node.right = remove(node.value, node.right);
            }
        }

        if(val < node.value)
            node.left = remove(val, node.left);
        else if(val > node.value)
            node.right = remove(val, node.right);

        return node;
    }

    public boolean update(int oldVal, int newVal) {
        if(root == null)
            return false;

        return update(oldVal, newVal, root);
    }
    private boolean update(int oldVal, int newVal, Node node) {
        if(node == null)
            return false;
        if(node.value == oldVal)
            node.value = newVal;

        if(node.value > oldVal)
            return update(oldVal, newVal, node.left);
        else return update(oldVal, newVal, node.right);
    }

    public int findHeight() {
        if(root == null)
            return -1;
        else return findHeight(root);
    }

    private int findHeight(Node node) {
        if(node == null)
            return -1;
        else return 1 + Math.max(findHeight(node.left), findHeight(node.right));
    }

    public void convertBTtoBST(BinaryTree bTree, int[] ar) {
        List<Integer> list = Arrays.stream(ar).boxed().collect(Collectors.toList());
        convertBTToBST(bTree.root, list, 0);
    }

    private void convertBTToBST(Node node, List<Integer> ar, int index) {
        if(node!=null) {
            convertBTToBST(node.left, ar, index);
            node.value = ar.get(index);
            ar.remove(index);
            convertBTToBST(node.right, ar, index);
        }
    }

    public static Node sortedArrToBST(int[] arr) {
        if(arr==null || arr.length==0)
            return null;
        else return sortedArrToBST(arr, 0, arr.length-1);
    }
    private static Node sortedArrToBST(int[] arr, int start, int end) {
        if(start <= end) {
            int mid = (start+end)/2;

            Node root = new Node(arr[mid]);
            root.left = sortedArrToBST(arr, start, mid-1);
            root.right = sortedArrToBST(arr, mid+1, end);
            return root;
        }
        return null;
    }

    public static void inorderStatic(Node node) {
        if(node!=null) {
            inorderStatic(node.left);
            System.out.println(node.value);
            inorderStatic(node.right);
        }
    }
}

class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

