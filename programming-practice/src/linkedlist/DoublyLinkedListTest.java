package linkedlist;

class DNode {
    int data;
    DNode next;
    DNode prev;
    public DNode(int data) {
        this.data = data;
    }
}

class MyDoublyLinkedList {
    DNode head;
    DNode tail;

    public void addNode(int data) {
        DNode newNode = new DNode(data);
        if(head==null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
    }

    public void traverseFwd(DNode node) {
        while(node!=null) {
            System.out.print(node.data + " -> ");
            node = node.next;
        }
        System.out.println();
    }

    public void traverseBwd(DNode node) {
        while(node!=null) {
            System.out.print(node.data + " -> ");
            node = node.prev;
        }
        System.out.println();
    }

    public DNode reverseDDL(DNode head) {
        DNode currNode=head;
        DNode nextNode=null, prevNode=null;
        while(currNode!=null) {
            nextNode=currNode.next;
            currNode.next=prevNode;
            currNode.prev=nextNode;

            prevNode=currNode;
            currNode=nextNode;
        }
        return prevNode;
    }

    public DNode rotate(DNode head, int p) {
        if(head==null) return null;
        DNode currNode=head;
        DNode tail=null;
        while(currNode!=null) {
            tail=currNode;
            currNode=currNode.next;
        }
        while(p-->0) {
            DNode newHeadNode = head.next;
            head.next=null;
            head.prev=tail;
            tail.next=head;
            tail=head;
            head = newHeadNode;
        }
        return head;
    }

    public static Node addOne(Node head)
    {
        int div=add(head);
        if(div>0) {
            Node newNode = new Node(div);
            newNode.next = head;
            head = newNode;
            return newNode;
        } else return head;
    }
    public static int add(Node node) {
        if(node.next==null) {
            int div=0;
            if((node.val+1)/10 > 0)
                div=(node.val+1)/10;
            node.val = (node.val+1)%10;
            return div;
        } else {
            int div = add(node.next);
            if(div!=0) {
                int newValue=(node.val+div)%10;
                div=(node.val+div)/10;
                node.val=newValue;
            }
            return div;
        }
    }
}

public class DoublyLinkedListTest {

    public static void main(String[] args) {
        MyDoublyLinkedList list = new MyDoublyLinkedList();
        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        list.addNode(4);
        list.addNode(5);

        DNode headNode = list.head;
        DNode tailNode = list.tail;
//        list.traverseFwd(headNode);
//        list.traverseBwd(tailNode);

//        DNode newArrangement = list.reverseDDL(headNode);
        DNode node = list.rotate(headNode,2);
    }
}
