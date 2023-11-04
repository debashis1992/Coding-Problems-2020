package netcracker;


import java.util.*;
import java.util.stream.Collectors;

public class Tests {
    int[][] dp;
    List<Node> qNodes;
    List<Node> pNodes;
    public static void main(String[] args) {
//
//        0|0|0|0|
//        |0|2|2|0|
//        |0|2|2|0|
//        |0|2|2|0|

        int[][] a = {{0,2,0,0},
                    {0,0,1,0},
                    {0,1,1,0},
                    {0,1,1,0}};

//        System.out.println(calculateTime(a));

        String[] t = {"a", "b", "c", "b", "a", "e"};
//        System.out.println(countPatients(t, 2));

        ListNode l=new ListNode(1);
        l.next=new ListNode(2);
        l.next.next=new ListNode(3);
        l.next.next.next=new ListNode(4);
        l.next.next.next.next=new ListNode(5);

//        rotate(l, 2);

//        System.out.println(new Tests().getNumPath(2,3));

        Node root=new Node(1);
        root.left=new Node(2);
        root.left.left=new Node(4);
        root.left.right=new Node(5);
        root.left.right.left = new Node(7);
        root.right=new Node(3);

        System.out.println(new Tests().lowestCommonAncestor(root, root.left.left, root.left.right.left).data);
    }

    Node lowestCommonAncestor(Node root, Node p, Node q) {
        if(root==null)
            return null;
        search(root, p, q, new ArrayList<>());

        printNodes(pNodes);
        printNodes(qNodes);
        if(pNodes.size() < qNodes.size())
            return pNodes.get(pNodes.size()-1);
        return qNodes.get(qNodes.size()-1);
    }

    void printNodes(List<Node> nodes) {
        List<Integer> list = nodes.stream().map(n -> n.data).collect(Collectors.toList());
        System.out.println(list);
    }

    void search(Node root, Node p, Node q, List<Node> visited) {
        if(root == null)
            return;
        visited.add(root);
        if(root.equals(p)) {
            pNodes = new ArrayList<>(visited);
        }
        else if(root.equals(q)) {
            qNodes = new ArrayList<>(visited);
        }

        search(root.left, p, q, visited);
        search(root.right, p, q, visited);
        visited.remove(root);
    }


    public int getNumPath(int r, int c) {
        dp=new int[r][c];
        for(int i=0;i<r;i++)
            Arrays.fill(dp[i], -1);

        path(0,0,r,c);
        printArray(dp);

        return dp[0][0];
    }

    public void printArray(int[][] dp) {
        for(int i=0;i<dp.length;i++)
            System.out.println(Arrays.toString(dp[i]));
    }
    public int path(int i,int j,int r,int c) {
        if(i>=r || j>=c)
            return 0;
        if(dp[i][j] == -1) {
            if(i==r-1 && j==c-1)
                dp[i][j]=1;
            else dp[i][j] = path(i,j+1,r,c) + path(i+1,j,r,c);
        }
        return dp[i][j];
    }

    public static ListNode rotate(ListNode head, int k) {
        int len=0;
        ListNode c=head;
        while(c!=null) {
            c=c.next;
            len++;
        }
        if(k>=len)
            k=k%len;

        if(k==0)
            return head;

        int i=0;
        ListNode prev=null, curr=head, nextNode=null;
        while(i<(len-k)) {
            nextNode=curr.next;
            prev=curr;
            curr=curr.next;
            i++;
        }

        ListNode c2 = prev.next;
        prev.next = null;

        ListNode c1=c2;
        while(c1!=null && c1.next!=null)
            c1=c1.next;

        c1.next = head;
        return c2;
    }

    public static Map<String, Integer> countPatients(String[] a, int c) {
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("admitted", 0);
        resultMap.put("not-admitted", 0);

        Map<String, Boolean> map = new HashMap<>();

        for(int i=0;i<a.length;i++) {
            if(map.containsKey(a[i])) {
                map.remove(a[i]);
            } else {
                if(map.size() == c)
                    resultMap.put("not-admitted", resultMap.getOrDefault("not-admitted", 0)+1);
                else {
                    map.put(a[i], true);
                    resultMap.put("admitted", resultMap.getOrDefault("admitted", 0)+1);
                }
            }
        }
        return resultMap;
    }

    public static int calculateTime(int[][] a) {
        if(a==null)
            return 0;
        //calculate fresh oranges
        //need to know where rotten oranges are present
        int fresh=0, r=a.length, c=a[0].length;
        Queue<int[]> q = new LinkedList<>();
        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                if(a[i][j] == 1)
                    ++fresh;
                else if(a[i][j] == 2)
                    q.add(new int[]{i,j});
            }
        }
        //  O(r*c) - worst time complexity
        //  O(r*c) - worst space complexity

        int t=0;
        int[][] points = new int[][]{{-1,0}, {1,0}, {0,1}, {0,-1}};
        while(!q.isEmpty()) {
            int s=q.size();
            for(int i=0;i<s;i++) {
                int[] rottenOrangePosition = q.poll();
                for(int[] point: points) {
                    int x = rottenOrangePosition[0]+point[0];
                    int y = rottenOrangePosition[1]+point[1];

                    if(x>=0 && x<r && y>=0 && y<c && a[x][y] == 1) {
                            q.add(new int[]{x, y});
                            a[x][y]=2;
                            --fresh;
                    }
                }
            }
            t++;
            if(fresh == 0)
                return t;
        }
        return (fresh == 0) ? t : -1;
    }


}

class ListNode {
    int data;
    ListNode next;
    public ListNode(int data) {
        this.data = data;
    }
}


class Node {
    int data;
    Node left, right;
    public Node(int data) { this.data = data; }
}