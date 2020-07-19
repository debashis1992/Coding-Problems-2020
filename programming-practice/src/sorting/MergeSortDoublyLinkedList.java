package sorting;

public class MergeSortDoublyLinkedList {
    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        int[] a = {7,3,5,2,6,4,1,8};
        for(int i: a)
            list.addNode(i);
        Node sortedList = sortDoubly(list.headNode);
        list.print(sortedList);
    }

    public static Node sortDoubly(Node head) {
        Node currNode = head;
        if(currNode==null || currNode.nextNode==null)
            return currNode;
        Node middleNode = findMiddleNode(head);
        Node middleNodeNext = middleNode.nextNode;

        middleNode.nextNode = null;
        Node left = sortDoubly(currNode);
        Node right = sortDoubly(middleNodeNext);
        Node result = mergeSortedLinkedLists(left, right);
        return result;

    }
    private static Node mergeSortedLinkedLists(Node left, Node right) {
        Node result = null;
        if(left==null)
            return right;
        else if(right==null)
            return left;
        else {
            if(left.value < right.value) {
                result = left;
                result.nextNode = mergeSortedLinkedLists(left.nextNode, right);
            } else {
                result = right;
                result.nextNode = mergeSortedLinkedLists(left, right.nextNode);
            }
            result.nextNode.prev = result;
            result.prev = null;
        }
        return result;
    }
    private static Node findMiddleNode(Node node) {
        Node currNode = node;
        Node fast = currNode, slow = currNode;
        while(fast.nextNode!=null && fast.nextNode.nextNode!=null) {
            slow = slow.nextNode;
            fast = fast.nextNode.nextNode;
        }
        return slow;
    }
}


