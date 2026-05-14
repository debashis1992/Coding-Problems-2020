//package crackingthecodingint;
//
//public class PartitionList {
//    public static void main(String[] args) {
//
//        DNode root=new DNode(1);
//        root.next=new DNode(4);
//        root.next.next=new DNode(3);
//        root.next.next.next=new DNode(2);
//        root.next.next.next.next=new DNode(5);
//        root.next.next.next.next.next=new DNode(2);
//
//        print(root);
//        partitionList(root, 3);
//        print(root);
//
//    }
//    public static void print(DNode node) {
//        DNode c=node;
//        while(c!=null) {
//            System.out.print(c.val+ " -> ");
//            c=c.next;
//        }
//        System.out.println("null");
//    }
//
//
//    public static DNode partitionList(DNode node, int t) {
//
//        DNode dummy=new DNode(-1);
//        dummy.next = node;
//
//        DNode high=node, prev=dummy;
//        while(high!=null && high.val <= t) {
//            prev=high;
//            high=high.next;
//        }
//
//        DNode higherLastNode = prev;
//
//        // now check all the remaining nodes from higherNode to end
//        // if found smaller, then just add it in the end of higherLastNode
//        // and now that becomes our new higherLastNode
//
//        DNode curr = higherLastNode.next;
//        while(curr!=null) {
//            if(curr.val < t) {
//                DNode nextNode = curr.next;
//                DNode lessNode = new DNode(curr.val);
//
//                higherLastNode.next = lessNode;
//                lessNode.next = high;
//
//                curr = nextNode;
//            } else {
//                prev = curr;
//                curr = curr.next;
//
//            }
//        }
//
//        return dummy.next;
//
//
//    }
//}
//
