package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DisjointSetTest {
    public static void main(String[] args) {

//        PriorityQueue<long[]> pq=new PriorityQueue<>(Comparator.comparingLong(o -> o[1]));

        DisjointSet dj=new DisjointSet(7);
        dj.unionByRank(1,2);
        dj.unionByRank(2,3);
        dj.unionByRank(4,5);
        dj.unionByRank(6,7);
        dj.unionByRank(5,6);

        if (dj.findPar(3) == dj.findPar(7)) {
            System.out.println("Same");
        } else
            System.out.println("Not Same");

        dj.unionByRank(3, 7);
        if (dj.findPar(3) == dj.findPar(7)) {
            System.out.println("Same");
        } else
            System.out.println("Not Same");

    }

}

class DisjointSet {
    List<Integer> rank;
    List<Integer> parent;
    List<Integer> size;
    public DisjointSet(int n) {
        rank = new ArrayList<>();
        parent = new ArrayList<>();
        size = new ArrayList<>();

        for(int i=0;i<=n;i++) {
            rank.add(0);
            parent.add(i);
            size.add(1);
        }
    }

    public int findPar(int node) {
        if(node == parent.get(node))
            return node;

        int parentNode = findPar(parent.get(node));
        parent.set(node, parentNode);
        return parent.get(node);
    }

    public void unionByRank(int u, int v) {
        int parentU = findPar(u);
        int parentV = findPar(v);

        if(parentU == parentV)
            return;

        if(rank.get(parentU) < rank.get(parentV)) {
            parent.set(parentU, parentV);
        } else if(rank.get(parentV) < rank.get(parentU)) {
            parent.set(parentV, parentU);
        } else {
            parent.set(parentV, parentU);

            int rankU = rank.get(parentU);
            rank.set(parentU, rankU + 1);
        }
    }

    public void unionBySize(int u, int v) {
        int parentU = findPar(u);
        int parentV = findPar(v);

        if(parentU == parentV) return;

        if(rank.get(parentU) < rank.get(parentV)) {
            parent.set(parentU, parentV);
            size.set(parentV, size.get(parentU) + size.get(parentV));
        } else {
            parent.set(parentV, parentU);
            size.set(parentU, size.get(parentU) + size.get(parentV));
        }
    }
}