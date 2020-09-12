package codeforces.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    public TreeNode(int data) {
        this.data = data;
    }
}
public class TreeTest {
    public static void main(String[] args) {

    }

    public static List<Integer> getAllElements(TreeNode node1, TreeNode node2) {
        List<Integer> list = new ArrayList<>();
        traverseTree(node1, list);
        traverseTree(node2, list);
        Collections.sort(list);
        return list;
    }
    private static void traverseTree(TreeNode node, List<Integer> list) {
        if(node!=null) {
            list.add(node.data);
            traverseTree(node.left,list);
            traverseTree(node.right,list);
        }
    }
}
