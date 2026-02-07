package testing;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Testclass {
    public static void main(String[] args) {

        char[][] a={
                {'.','.','#'},
                {'.','.','.'},
                {'.','.','.'}
        };
//        Solution solution = new Solution();
//        int ans = solution.validate(a);
//        System.out.println("ans: "+ans);
//        solution.validate2(a, 3);
//        System.out.println(ans);
//        if(ans < 5)
//            System.out.println("yes");
//        else System.out.println("no");


        String[] names = {"sam","sams","walmart","walmart","wm","teams","sam","teams"};
        Map<String, Integer> map = new HashMap<>();
        Map<String, Long> freqMap = Arrays.stream(names).collect(
                Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()));

        System.out.println(freqMap);

        List<Integer> integers=Arrays.asList(1,2,2,3,4,4,4,5);
        int sum = integers.stream().mapToInt(x -> x).sum();
        System.out.println(sum);

        Department d1 = new Department(1, "cs");
        Department d2 = new Department(2, "ee");

        Employee emp1 = new Employee(1, d1, 1000);
        Employee emp2 = new Employee(2, d2, 34);
        Employee emp3 = new Employee(2, d1, 34324);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.addAll(Arrays.asList(emp1, emp2, emp3));

        Map<Department, Long> departmentLongMap =
                employeeList.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        System.out.println(departmentLongMap);

        String str = "abbcdd";
        String obj = str.chars().distinct().mapToObj(x -> String.valueOf((char)x))
                .collect(Collectors.joining(""));

        System.out.println(obj);


        ListNode dummy = new ListNode(1);
        dummy.next = new ListNode(2);
        dummy.next.next = new ListNode(3);
        dummy.next.next.next = new ListNode(4);
        dummy.next.next.next.next = new ListNode(5);


//        1 -> 2 -> 3 -> 4 -> 5
//        -1prev -> 1c -> 2n -> 3nn -> 4 -> 5
//        -1prev -> 2 -> 1c -> 3n -> 4nn -> 5
        //        -1prev -> 3n -> 2pp -> 1c -> 4n -> 5
        //        -1prev -> 4n -> 3pp -> 2 -> 1c -> 5nn




        int len=0;
        ListNode curr = dummy;
        while(curr!=null) {
            ++len;
            curr=curr.next;
        }

        int k=5;

        ListNode dummy2 = new ListNode(-1);
        dummy2.next = dummy;

        ListNode prev = dummy2;
        while(len>=k) {
            ListNode c = prev.next, n = c.next;
            for(int i=1;i<k;i++) {
                ListNode nn=n.next;
                c.next = nn;
                ListNode pp = prev.next;
                prev.next = n;
                n.next = pp;
                n=nn;
            }
            prev = c;
            len-=k;
        }

        curr = dummy2.next;
        while(curr!=null) {
            System.out.print(curr.val + " -> ");
            curr=curr.next;
        }
        System.out.println();


        Solution sn=new Solution();
        int[][] a2 = {
                {1,2,5},
                {2,1,2},
                {5,7,2}
        };

        int ans = sn.getMinPathValue(a2);
        System.out.println("ans: "+ans);

        String[] s = {"bar","foo","the"};
        sn.permute(s);

    }
}


class ListNode {
    int val;
    ListNode next;
    public ListNode(int val) {
        this.val=val;
    }
}


class Employee {
    int id;
    Department department;
    int salary;
    public Employee(int id, Department department, int salary) {
        this.id=id;
        this.department=department;
        this.salary=salary;
    }

    public Department getDepartment() { return department; }
    public int getSalary() { return salary; }

}
class Department {
    int id;
    String name;

    public Department(int id, String name) {
        this.id=id;
        this.name=name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


class Solution {
    int[][] vis;
    int[][] dp;

    public void permute(String[] s) {
        List<String> list=new ArrayList<>();
//        char[] ch=s.toCharArray();

        permute(0, list, s);
        System.out.println(list);
        String whole = "barfoofoobarthefoobarman";
        List<Integer> indexList = new ArrayList<>();
        for(String word: list) {
            int idx = whole.indexOf(word);
            if(idx!=-1)
                indexList.add(idx);
        }


        System.out.println("index list: "+indexList);
    }

    private void permute(int idx, List<String> list, String[] s) {
        if(idx == s.length) {
            StringBuilder sb = new StringBuilder();
            for(String c: s)
                sb.append(c);
            list.add(sb.toString());
            return;
        }

        for(int i=idx;i<s.length;i++) {
            swap(s, idx, i);
            permute(idx+1, list, s);
            swap(s, idx, i);
        }
    }

    private void swap(String[] ch, int i, int j) {
        String tmp=ch[i];
        ch[i]=ch[j];
        ch[j]=tmp;
    }


    public int getMinPathValue(int[][] a) {
        int r=a.length, c=a[0].length;
        vis=new int[r][c];

        PriorityQueue<int[]> pq=new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        pq.offer(new int[]{0,0,a[0][0]});


        while(!pq.isEmpty()) {

            int[] tmp=pq.poll();
            int t=tmp[2];
            vis[tmp[0]][tmp[1]]=1;
            if(tmp[0]==r-1 && tmp[1]==c-1)
                return t;

            int[][] points = {{1,0},{-1,0},{0,1},{0,-1}};
            for(int[] point: points) {
                int x=point[0]+tmp[0], y=point[1]+tmp[1];

                if(x>=0 && x<r && y>=0 && y<c && vis[x][y]==0) {
                    pq.offer(new int[]{x,y,Math.max(t, a[x][y])});
                }
            }

        }

        return -1;

    }

    public int validate(char[][] a) {
        int r=a.length, c=a[0].length;
        vis=new int[r][c];

        PriorityQueue<int[]> q=new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        if(a[0][0]=='.') {
            q.offer(new int[]{0, 0, 0});
            vis[0][0] = 1;
        }

        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0;i<size;i++) {
                int[] p = q.poll();
                int t= p[2];
                if(p[0] == r-1 && p[1] == c-1)
                    return t;

                int[][] points = {{-1,0},{1,0},{0,1},{0,-1}};

                for(int[] point: points) {
                    int x=p[0]+point[0], y=p[1]+point[1];

                    if(x>=0 && x<r && y>=0 && y<c && a[x][y]!='#' && vis[x][y]==0) {
                        vis[x][y]=1;
                        q.offer(new int[]{x,y,t+1});
                    }
                }
            }
        }

        return -1;

    }

    public void validate2(char[][] a, int m) {
        int r=a.length, c=a[0].length;
        vis=new int[r][c];
        dp=new int[r][c];
        for(int[] row: dp)
            Arrays.fill(row, -1);

        int ans = solve(0,0,r,c, a);

        for(int[] row: dp)
            System.out.println(Arrays.toString(row));
        System.out.println("ans: "+ans);
        if(ans <= m)
            System.out.println("yes");
        else System.out.println("no");

    }

    private int solve(int i, int j, int r, int c, char[][] a) {
        if(i==r-1 && j==c-1) return 0;

        if(dp[i][j]!=-1) return dp[i][j];
        int[][] points = {{-1,0},{1,0},{0,1},{0,-1}};
        int min = (int)1e9;

        for(int[] point: points) {
            int x = i+point[0], y=j+point[1];
            if(x>=0 && x<r && y>=0 && y<c && a[x][y]!='#' && vis[x][y]==0) {
                vis[x][y] = 1;
                dp[i][j] = 1+Math.min(min, solve(x,y,r,c,a));
                vis[x][y]=0;
            }
        }
        return dp[i][j];
    }

}
