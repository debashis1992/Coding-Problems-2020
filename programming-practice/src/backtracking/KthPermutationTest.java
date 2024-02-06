package backtracking;

import java.util.ArrayList;
import java.util.List;

public class KthPermutationTest {
    public static void main(String[] args) {

        SolutionTest test=new SolutionTest();
        String res = test.getPermutation(3,5);
        System.out.println(res);
        System.out.println(test.list);
    }
}

class SolutionTest {
    public String getPermutation(int n, int k) {
        List<Integer> a=new ArrayList<>();
        List<List<Integer>> ans=new ArrayList<>();
        int path[]=new int[n];
        sol(ans,a,n,k,path);
        List<Integer> t=ans.get(k-1);
        String s="";
        for(int i=0;i<t.size();i++){
            s=s+Integer.toString(t.get(i));
        }
        return s;
    }

    public void sol(List<List<Integer>> ans,List<Integer> a,int n,int k,int[] path){
        if(a.size()==n){
            ans.add(new ArrayList<>(a));
            return ;
        }
        if(ans.size()==k){
            return ;
        }
        for(int i=0;i<n;i++){
            if(path[i]==0){
                path[i]=1;
                a.add(i+1);
                sol(ans,a,n,k,path);
                a.remove(a.size()-1);
                path[i]=0;
            }
        }
    }


//    public String getPermutation(int n, int k) {
//        StringBuilder sb = new StringBuilder();
//        for(int i=1;i<=n;i++) {
//            sb.append(i);
//        }
//
//        String s=sb.toString();
//        generate(s, 0, s.length()-1, k);
//        return list.get(k-1);
//    }

    public List<String> list=new ArrayList<>();
    public void generate(String s, int l, int r, int k) {

        if(l==r) {
            list.add(s);
            return;
        }

        for(int i=l;i<=r;i++) {
            s = swap(s,i,l);
            generate(s, l+1, r, k);
            s = swap(s,i,l);
        }
    }

    public String swap(String s, int i, int j) {
        char[] c=s.toCharArray();
        char tmp=c[i];
        c[i]=c[j];
        c[j]=tmp;
        return String.valueOf(c);
    }
}
