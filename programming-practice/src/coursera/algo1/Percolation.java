package coursera.algo1;

import edu.princeton.cs.algs4.*;

import java.util.Arrays;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[] cells;
    private boolean[] isSiteOpen;
    private int gridLength;
    private WeightedQuickUnionUF sites;
    private int numberOfOpenSites;
    private int virtualTopID;
    private int virtualDownID;

    public Percolation(int n) {
        if(n<=0)
            throw new IllegalArgumentException("size should be between 1 and n.");
        cells = new int[n*n+2];
        sites = new WeightedQuickUnionUF(cells.length);
        gridLength = n;
        numberOfOpenSites = 0;
        virtualTopID = n*n;
        virtualDownID = n+n+1;
        this.isSiteOpen = new boolean[n*n];
        Arrays.fill(isSiteOpen,false);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row<0 || row>gridLength || col<0 || col>gridLength)
            throw new IllegalArgumentException("please check the parameter bounds");
        //top => row=row-1, col=col
        //left=> row=row, col=col-1
        //down=> row=row+1, col=col
        //right=> row=row, col=col+1

        int currentPosition = find2Dto1DPosition(row, col);
        if(row-2>=0) {
            if (row-2==0) //if topmost row is found, link it with virtualTopID
                sites.union(currentPosition, virtualTopID);
            int topPosition = find2Dto1DPosition(row-1, col);
            sites.union(currentPosition, topPosition);
        }
        if(col-2>=0) {
            int leftPosition = find2Dto1DPosition(row, col-1);
            sites.union(currentPosition, leftPosition);
            isSiteOpen[leftPosition]=true;
        }
        if(row<gridLength) {
            if(row==gridLength-1) //if down-most row is found, link it with virtualDownID
                sites.union(currentPosition, virtualDownID);
            int downPosition = find2Dto1DPosition(row+1, col);
            sites.union(currentPosition, downPosition);
            isSiteOpen[downPosition]=true;
        }
        if(col<gridLength) {
            int rightPosition = find2Dto1DPosition(row, col+1);
            sites.union(currentPosition, rightPosition);
            isSiteOpen[rightPosition]=true;
        }
        isSiteOpen[currentPosition]=true;
        ++numberOfOpenSites;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        int currentPosition = find2Dto1DPosition(row, col);
        return this.isSiteOpen[currentPosition];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int currentPosition = find2Dto1DPosition(row, col);
        return this.sites.find(currentPosition) == this.sites.find(this.virtualTopID);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        //check if the virtual top ID is connected to virtual down ID
        return sites.find(virtualTopID) == sites.find(virtualDownID);
    }

    private int find2Dto1DPosition(int row, int col) {
        if(row==0 && col==0) return 0;
        return (row-1) * gridLength + (col-1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        Percolation percolation = new Percolation(3);

        percolation.open(1,1);
        StdOut.println("percolation : "+percolation.percolates());
        StdOut.println("no. of opened sites : "+percolation.numberOfOpenSites());
        percolation.open(3,3);
        StdOut.println("percolation : "+percolation.percolates());
        StdOut.println("no. of opened sites : "+percolation.numberOfOpenSites());

//        percolation.open(2,2);
        StdOut.println("percolation : "+percolation.percolates());
        StdOut.println("no. of opened sites : "+percolation.numberOfOpenSites());

        System.out.println("is site open : "+percolation.isOpen(2,2));
        StdOut.println("elapsed time: "+stopwatch.elapsedTime());
    }
}
