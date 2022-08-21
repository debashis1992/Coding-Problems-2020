package trees;

import java.lang.reflect.Array;
import java.util.*;

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
//        System.out.println(node2);

        Node node1 = new Node(3);
        node1.left = new Node(5);
        node1.left.left = new Node(6);
        node1.left.right = new Node(2);
        node1.left.right.left = new Node(7);
        node1.left.right.right = new Node(4);
        node1.right = new Node(1);
        node1.right.left = new Node(0);
        node1.right.right = new Node(8);

        System.out.println("lca found : "+new BinaryTree().getLCAMedium(node1,0,8));

        Node node3=new Node(3);
        node3.left=new Node(5);
        node3.left.left=new Node(6);
        node3.left.right=new Node(2);
        node3.left.right.left=new Node(7);
        node3.left.right.right=new Node(4);
        node3.right=new Node(1);
        node3.right.left=new Node(0);
        node3.right.right=new Node(8);

//        System.out.println(nodesAtDistanceK(node3, node3.left, 2));


        int[] p = {8,5,1,7,10,12};
//        Node root = constructBSTFromPreOrder(p);
//        printTree(root);

        constructBSTFromPreOrderTraversal(p);
    }

    public static void constructBSTFromPreOrderTraversal(int[] p) {
        Node root = constructAccordingly(null, Integer.MIN_VALUE, Integer.MAX_VALUE, p, new int[]{0});
        printTree(root);
    }

    private static Node constructAccordingly(Node node, int min, int max, int[] p, int[] i) {
        if(i[0]==p.length)
            return null;

        if(p[i[0]] >=min && p[i[0]]<=max) {
            node = new Node(p[i[0]++]);
            node.left = constructAccordingly(node.left, min, node.data-1, p, i);
            node.right = constructAccordingly(node.right, node.data+1, max, p, i);
        }
        return node;
    }

    private static void printTree(Node node) {
        if(node==null)
            return;
        printTree(node.left);
        System.out.print(node.data+" -> ");
        printTree(node.right);
    }

    public static Node constructBSTFromPreOrder(int[] p) {
        if(p==null || p.length==0)
            return null;

        Node node = new Node(p[0]);
        for(int i=1;i<p.length;i++) {
            insertAccordingly(node, p[i]);
        }

        return node;
    }

    private static Node insertAccordingly(Node node, int val) {
        if(node==null)
            return new Node(val);

        if(node.data > val)
            node.left = insertAccordingly(node.left, val);
        else node.right = insertAccordingly(node.right, val);

        return node;
    }

    public static List<Integer> nodesAtDistanceK(Node node, Node target, int K) {
        if(node==null)
            return new ArrayList<>();

        Map<Node,Node> parentMap = getAllParents(node);
        Set<Node> visited=new HashSet<>();
        Queue<Node> queue=new LinkedList<>();
        queue.offer(target);
        visited.add(target);

        int currLevel=0;
        while(!queue.isEmpty()) {
            int size=queue.size();
            if (currLevel == K)
                break;
            for(int i=0;i<size;i++) {
                Node pop = queue.poll();

                if (parentMap.get(pop) != null && !visited.contains(parentMap.get(pop))) {
                    queue.offer(parentMap.get(pop));
                    visited.add(parentMap.get(pop));
                }
                if (pop.left != null && !visited.contains(pop.left)) {
                    queue.offer(pop.left);
                    visited.add(pop.left);
                }
                if (pop.right != null && !visited.contains(pop.right)) {
                    queue.offer(pop.right);
                    visited.add(pop.right);
                }
            }
            currLevel++;
        }

        List<Integer> result=new ArrayList<>();
        while(!queue.isEmpty()) {
            result.add(queue.poll().data);
        }
        return result;
    }

    private static Map<Node, Node> getAllParents(Node node) {
        if(node==null)
            return new HashMap<>();
        Map<Node,Node> map = new HashMap<>();
        Queue<Node> queue=new LinkedList<>();
        queue.offer(node);
        while(!queue.isEmpty()) {
            Node n = queue.poll();
            if(n.left!=null) {
                map.put(n.left, n);
                queue.offer(n.left);
            }
            if(n.right!=null) {
                map.put(n.right, n);
                queue.offer(n.right);
            }
        }
        return map;
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

    public int heightRec(Node node) {
        if(node==null)  return 0;
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    public int heightIterative(Node node) {
        int c=0;
        Queue<Node> q = new LinkedList<>();
        if(node!=null)
            q.add(node);
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-->0) {
                Node node1 = q.remove();
                if(node1.left!=null)    q.add(node1.left);
                if(node1.right!=null)   q.add(node1.right);
            }
            c++;
        }
        return c;
    }
    public int getLCA(Node node, int v1, int v2) {
        Node temp = node;
        Queue<Integer> queue1 = getPath(temp, v1);
        Queue<Integer> queue2 = getPath(temp, v2);
        int lca = -1;
        for(int i=0;i<Math.min(queue1.size(), queue2.size());i++) {
            int i1 = queue1.poll();
            int i2 = queue2.poll();
            if(i1==i2)
                lca=i1;
        }
        return lca;
    }

    public Queue<Integer> getPath(Node node, int v) {
        Queue<Integer> queue = new LinkedList<>();
        while(node!=null && node.data!=v) {
            queue.add(node.data);
            if(node.data > v)
                node = node.left;
             else
                node = node.right;
        }
        queue.add(node.data);

        return queue;
    }

    public int getLCAMedium(Node node,int v1,int v2) {
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();

        findElement(node,v1,q1);
        findElement(node,v2,q2);
        int lca=0;
        int min = Math.min(q1.size(),q2.size());
        for(int i=0;i<min;i++) {
            int i1=q1.poll();
            int i2=q2.poll();
            if(i1==i2)
                lca=i1;
        }
        return lca;
    }
    public boolean findElement(Node node, int v, Queue<Integer> queue) {
        if(node==null)  return false;
        if(node.data==v)    return true;
        else queue.add(node.data);

        return findElement(node.left,v,queue) ||
                findElement(node.right,v,queue);
    }

    public static void printTopView(Node root) {
        if(root==null)  return;

        Queue<QueueNode> queue = new LinkedList<>();
        Map<Integer,Node> map = new HashMap<>();
        queue.add(new QueueNode(root, 0));

        while(!queue.isEmpty()) {
            QueueNode obj = queue.poll();
            if(!map.containsKey(obj.hd))
                map.put(obj.hd, obj.node);

            if(obj.node.left!=null)
                queue.add(new QueueNode(obj.node.left, obj.hd-1));
            if(obj.node.right!=null)
                queue.add(new QueueNode(obj.node.right, obj.hd+1));
        }

        map.entrySet().stream().forEach((entry) -> {
            System.out.print(entry.getValue()+" ");
        });

    }

    static class QueueNode {
        Node node;
        int hd;
        public QueueNode(Node node, int hd) {
            this.node = node;
            this.hd = hd;
        }
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