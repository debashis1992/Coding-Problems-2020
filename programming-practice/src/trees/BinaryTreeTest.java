package trees;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeTest {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
//        tree.addNode(2);
//        tree.addNode(1);
//        tree.addNode(5);
//        tree.addNode(7);
//        tree.addNode(4);

//        tree.deleteNode(5);
//        System.out.println(tree);
//        System.out.println(tree.findElement(7));
//        tree.performPostOrderTraversal();
//        tree.performBreadthFirstSearchTraversal();
          int[] a = {1,7,5,50,40,10};
          Node node = tree.constructBSTFromPostOrder(a, a.length);
//          System.out.println(node);

        int[] inorder = {9,3,15,20,7};
        int[] postorder = {9,15,7,20,3};
        Node node2 = tree.constructBSTFromInorderAndPostOrder(inorder, postorder, postorder.length);
        System.out.println(node2);

    }
}


class BinaryTree {
    public Node rootNode;
    public void addNode(int data) {
        rootNode = recursiveAddNode(rootNode, data);
    }
    public Node recursiveAddNode(Node currNode, int data) {
        if(currNode==null) {
            currNode = new Node(data);
            return currNode;
        }
        if(data < currNode.data)
            currNode.left = recursiveAddNode(currNode.left, data);
        else if(data > currNode.data)
            currNode.right = recursiveAddNode(currNode.right, data);
        else {
            return currNode;
        }
        return currNode;
    }

    public boolean findElement(int data) {
        if(rootNode==null)
            return false;
        else {
           return recursiveFindElement(rootNode, data);
        }
    }
    private boolean recursiveFindElement(Node currNode, int data) {
        if(currNode==null)
            return false;
        if(data==currNode.data)
            return true;
        return (data < currNode.data) ?
                recursiveFindElement(currNode.left, data) :
                recursiveFindElement(currNode.right, data);
    }

    public void deleteNode(int data) {
        if(rootNode==null) {
            System.out.println("Binary Tree is empty. No data to delete.");
            return;
        }
        rootNode = recursiveDeleteNode(rootNode, data);
    }

    public Node recursiveDeleteNode(Node currNode, int data) {
        if(currNode==null)
            return null;
        if(data==currNode.data) {
            // element was found, continue deletion logic
            if(currNode.left==null && currNode.right==null)
                return null;
            if(currNode.left==null)
                return currNode.right;
            if(currNode.right==null)
                return currNode.left;
            else {
                // if the currNode is having 2 children
                // find the smallest value
                int smallestValue = findSmallestValue(currNode);
                currNode.data = smallestValue;
                currNode.right = recursiveDeleteNode(currNode.right, smallestValue);
                return currNode;
            }
        }
        if (data < currNode.data) {
            currNode.left = recursiveDeleteNode(currNode.left, data);
            return currNode;
        }
        else currNode.right = recursiveDeleteNode(currNode.right, data);
        return currNode;

    }

    private int findSmallestValue(Node currNode) {
        if(currNode.left==null)
            return currNode.data;
        else return findSmallestValue(currNode.left);

    }

    public void performInOrderTraversal() {
        performInOrderTraversalRecursively(rootNode);
        System.out.println();
    }
    private void performInOrderTraversalRecursively(Node currNode) {
        if(currNode==null)  return;
        performInOrderTraversalRecursively(currNode.left);
        System.out.print(currNode.data+" -> ");
        performInOrderTraversalRecursively(currNode.right);
    }
    public void performPreOrderTraversal() {
        performPreOrderTraversalRecursively(rootNode);
        System.out.println();
    }
    private void performPreOrderTraversalRecursively(Node currNode) {
        if(currNode==null)
            return;
        System.out.print(currNode.data + " -> ");
        performPreOrderTraversalRecursively(currNode.left);
        performPreOrderTraversalRecursively(currNode.right);
    }
    public void performPostOrderTraversal() {
        performPostOrderTraversalRecursively(rootNode);
    }
    private void performPostOrderTraversalRecursively(Node currNode) {
        if(currNode==null)
            return;
        performPostOrderTraversalRecursively(currNode.left);
        performPostOrderTraversalRecursively(currNode.right);
        System.out.print(currNode.data + " -> ");
    }
    public void performBreadthFirstSearchTraversal() {
        performBreadthFirstSearchTraversalRecursively(rootNode);
    }
    private void performBreadthFirstSearchTraversalRecursively(Node currNode) {
        if(currNode==null)
            return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(currNode);
        while(!queue.isEmpty()) {
            Node node = queue.remove();
            System.out.print(node.data+" -> ");
            if(node.left!=null)
                queue.add(node.left);
            if(node.right!=null)
                queue.add(node.right);
        }
    }

    public Node invertTree() {
        return invertTreeRecursively(rootNode);
    }
    private Node invertTreeRecursively(Node node) {
        if(node==null)
            return null;
        if(node.left==null && node.right==null)
            return node;
        invertTreeRecursively(node.left);
        invertTreeRecursively(node.right);
        Node tempNode = node.left;
        node.left = node.right;
        node.right = tempNode;
        return node;
    }

    public void rightViewOfTree() {
        List<Integer> list = new ArrayList<>();
        Node node = rootNode;
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while(!queue.isEmpty()) {
            int n = queue.size();
            for(int i=0;i<n;i++) {
                Node temp = queue.remove();
                if(i==n-1)
                  list.add(temp.data);

                if(temp.left!=null)
                    queue.add(temp.left);
                if(temp.right!=null)
                    queue.add(temp.right);
            }
        }
        System.out.println("Right View -> "+list);
    }

    public void leftSideView() {
        List<Integer> list = new ArrayList<>();
        Node node = rootNode;
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while(!queue.isEmpty()) {
            int n = queue.size();
            for(int i=0;i<n;i++) {
                Node temp = queue.remove();
                if(i==n-1)
                    list.add(temp.data);
                if(temp.right!=null)
                    queue.add(temp.right);
                if(temp.left!=null)
                    queue.add(temp.left);
            }
        }
        System.out.println("Left View -> "+list);
    }

    public Node constructBSTFromPostOrder(int[] post, int n) {
        for(int i=n-1;i>=0;i--) {
            addNode(post[i]);
        }
        return rootNode;
    }

    public Node constructBSTFromInorderAndPostOrder(int[] inorder, int[] postorder,int n) {
        Index pIndex = new Index();
        pIndex.index = n-1;
        return buildUtil(inorder, postorder,0,n-1,pIndex);
    }
    private Node buildUtil(int[] in,int[] post,int start,int end, Index pIndex) {

        if(start>end)
            return null;
        Node node = new Node(post[pIndex.index]);
        (pIndex.index)--;
        // If this node has no children then return
        if(start==end)
            return node;
        // search element in inorder
        int index  = search(in, start, end, node.data);
        node.right = buildUtil(in, post, index+1,end,pIndex);
        node.left = buildUtil(in, post,start,index-1,pIndex);
        return node;
    }
    private int search(int[] in, int start,int end,int p) {
        for(int i=start;i<=end;i++) {
            if(p==in[i])
                return i;
        }
        return -1;
    }

}

class Index {
    int index;
}

class Node {
    int data;
    Node left;
    Node right;
    public Node(int data) {
        this.data = data;
    }
}