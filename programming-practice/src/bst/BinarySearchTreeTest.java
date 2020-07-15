package bst;

public class BinarySearchTreeTest {

    public static void main(String[] args) {
        int[] a = {-10, -3, 0, 5 ,9};
        BST bst = new BST();
        for(int i: a)
            bst.addNode(i);

//        bst.printInOrderTraversal();
        bst.preOrderTraversal();
    }
}


class Node {
    int value;
    Node leftNode;
    Node rightNode;

    public Node(int value) {
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
    }
}
class BST {
    Node rootNode;
    public void addNode(int value) {
        if(rootNode==null) {
            Node node = new Node(value);
            rootNode = node;
        } else {
            boolean isLess = false;
            Node currNode = rootNode;
            Node prev = null;
            while(currNode!=null) {
                prev = currNode;
                if(value < currNode.value) {
                    currNode = currNode.leftNode;
                    isLess = true;
                } else {
                    currNode = currNode.rightNode;
                    isLess = false;
                }
            }
            currNode = new Node(value);
            if(isLess)
                prev.leftNode = currNode;
            else prev.rightNode = currNode;

        }
    }

    public void printInOrderTraversal() {
        Node currNode = rootNode;
        printInOrderTraversal(currNode);
    }
    private void printInOrderTraversal(Node node) {
        if(node==null)
            return;
        printInOrderTraversal(node.leftNode);
        System.out.println(node.value);
        printInOrderTraversal(node.rightNode);
    }

    public void preOrderTraversal() {
        Node currNode = rootNode;
        printPreOrderTraversal(currNode);
    }
    private void printPreOrderTraversal(Node node) {
        if(node==null)
            return;
        System.out.print(node.value);
        printPreOrderTraversal(node.leftNode);
        printPreOrderTraversal(node.rightNode);

    }

}