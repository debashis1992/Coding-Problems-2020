package trees;

public class BinaryTreeToDLL {
    public static void main(String[] args) {
        Node root = new Node(10);
        root.left = new Node(12);
        root.right = new Node(15);

        BinaryTreeTest binaryTreeTest = new BinaryTreeTest();
        Node node = binaryTreeTest.convertBTToDLL(root);
        Node c = node;
        while(c.left!=null)
            c=c.left;
        binaryTreeTest.print(c);
    }
}

class Node {
    int data;
    Node left, right;
    public Node(int data) {
        this.data=data;
    }
}

class BinaryTreeTest {

    public void print(Node node) {
        Node curr=node;
        while(curr!=null) {
            System.out.print(curr.data+" -> ");
            curr=curr.right;
        }
        System.out.println();
    }
    public Node convertBTToDLL(Node node) {
        if(node==null)
            return null;

        if(node.left!=null) {

            Node ln = convertBTToDLL(node.left);
            while(ln.right!=null)
                ln=ln.right;

            ln.right=node; // right means next
            node.left=ln; // left means prev
        } if(node.right!=null) {

            Node rn = convertBTToDLL(node.right);
            while(rn.left!=null)
                rn=rn.left;

            rn.left = node;
            node.right = rn;
        }
        return node;
    }
}
