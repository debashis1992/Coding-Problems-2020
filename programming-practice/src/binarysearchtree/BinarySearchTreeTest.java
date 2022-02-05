package binarysearchtree;

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
        tree.remove(20);
        tree.inorder();
    }
}

class MyBinarySearchTree {
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
        else System.out.println("Tree empty");
    }

    private void inorder(Node node) {
        if(node!=null) {
            inorder(node.left);
            print(node);
            inorder(node.right);
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
        while(node!=null) {
            min = node.value;
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

