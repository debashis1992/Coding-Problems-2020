package sorting;

public class MergeSortForLinkedListTest {
    public static void main(String[] args) {

        MyLinkedList list = new MyLinkedList();
        list.addNode(5);
        list.addNode(4);
        list.addNode(3);
        list.addNode(2);
        list.addNode(7);

        list.print();
        Node node = mergeSortForLinkedList(list.headNode);
        list.print(node);

        MyLinkedList list1 = new MyLinkedList();
        list1.addNode(5);
        list1.addNode(10);
        list1.addNode(15);

        MyLinkedList list2 = new MyLinkedList();
        list2.addNode(2);
        list2.addNode(3);
        list2.addNode(20);

        Node sortedList = sortedMerge(list1.headNode, list2.headNode);
//        list.print(sortedList);

    }
    private static Node getMiddleElement(Node node) {
        Node currNode = node;
        Node slow = currNode, fast = currNode;
        while(fast.nextNode!=null && fast.nextNode.nextNode!=null) {
            slow = slow.nextNode;
            fast = fast.nextNode.nextNode;
        }
        return slow;
    }

    private static Node sortedMerge(Node left, Node right) {
        Node result = null;
        if(left==null)
            return right;
        else if(right==null)
            return left;

        if(left.value < right.value) {
            result = left;
            result.nextNode = sortedMerge(left.nextNode, right);
        } else  {
            result = right;
            result.nextNode = sortedMerge(left, right.nextNode);
        }

        return result;
    }

    public static Node mergeSortForLinkedList(Node node) {
        Node currNode = node;
        if(currNode==null || currNode.nextNode == null)
            return node;

        // get the middle element
        Node middle = getMiddleElement(node);
        Node nextOfMiddle = middle.nextNode;

        // set the next of middle node to null
        middle.nextNode = null;

        // apply mergeSort on left and right list
        Node left = mergeSortForLinkedList(node);
        Node right = mergeSortForLinkedList(nextOfMiddle);

        // sort the left and right elements
        Node sortedList = sortedMerge(left, right);
        return sortedList;
    }
}

class Node {
    int value;
    Node nextNode;
    Node prev;
    public Node(int value) {
        this.value = value;
        this.nextNode = null;
    }
}

class MyLinkedList {

    Node headNode, tailNode;
    public MyLinkedList() {
        this.headNode = null;
        this.tailNode = null;
    }

    public void addNode(int value) {
        Node newNode = new Node(value);
        if(headNode==null) {
            headNode = newNode;
            tailNode = newNode;
        } else {
            tailNode.nextNode = newNode;
            tailNode = newNode;
        }
    }

    public void print() {
        Node currNode = this.headNode;
        while(currNode!=null) {
            System.out.print(currNode.value + " -> ");
            currNode = currNode.nextNode;
        }
        System.out.println("null");
    }

    public void print(Node node) {
        while(node!=null) {
            System.out.print(node.value + " -> ");
            node = node.nextNode;
        }
        System.out.println("null");
    }
}