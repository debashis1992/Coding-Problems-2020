package binarysearchtree;

import java.util.Stack;

public class PostOrder {
    public static void main(String[] args) {

    }

    public static void postOrder(Node1 root) {
        Stack<Node1> st=new Stack<>();
        if(root==null) return;

        Node1 curr=root, lastVisited=null;

        while(curr!=null || !st.isEmpty()) {
            if(curr!=null) {
                st.push(curr);
                curr=curr.left;
            }
            else {
                Node1 peek = st.peek();
                if(peek.right!=null && lastVisited!=peek.left) {
                    curr = peek.right;
                }
                else {
                    System.out.println(peek.val);
                    lastVisited = st.pop();
                }
            }

        }
    }
}

class Node1 {
    int val;
    Node1 left, right;
}

