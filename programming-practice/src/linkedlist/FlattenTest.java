package linkedlist;

public class FlattenTest {

    public static void main(String[] args) {

        FNode root=new FNode(5);
        root.bottom=new FNode(7);
        root.bottom.bottom=new FNode(8);
        root.bottom.bottom.bottom=new FNode(10);

        FNode root2 = new FNode(10);
        root2.bottom=new FNode(19);
        root2.bottom.bottom=new FNode(20);
        root2.bottom.bottom.bottom=new FNode(22);
        root2.bottom.bottom.bottom.bottom=new FNode(28);
        root2.bottom.bottom.bottom.bottom.bottom=new FNode(50);

        root.next=root2;

        flatten(root);
        print(root);
    }

    public static void print(FNode root) {
        FNode c=root;
        while(c!=null) {
            System.out.print(c.data+" -> ");
            c=c.bottom;
        }
        System.out.println("NULL");
    }

    public static FNode flatten(FNode root) {
        if(root==null || root.next==null)
            return root;

        root.next=flatten(root.next);
        root=merge(root, root.next);
        return root;
    }

    public static FNode merge(FNode a, FNode b) {
        FNode tmp=new FNode(0);
        FNode c1=tmp;

        while(a!=null && b!=null) {
            if(a.data < b.data) {
                c1.bottom=a;
                a=a.bottom;
            }
            else {
                c1.bottom=b;
                b=b.bottom;
            }
            c1=c1.bottom;
        }
        if(a!=null)
            c1.bottom=a;
        else c1.bottom=b;

        return tmp.bottom;
    }
}


class FNode {
    int data;
    FNode bottom;
    FNode next;
    public FNode(int data) {
        this.data = data;
    }
}