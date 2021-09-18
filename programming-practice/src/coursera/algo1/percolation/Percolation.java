package coursera.algo1.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private final boolean[] isSiteOpen;
    private final int gridLength;
    private final WeightedQuickUnionUF sites;
    private int numberOfOpenSites;
    private final int virtualTopID;
    private final int virtualDownID;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("size should be between 1 and n.");

        sites = new WeightedQuickUnionUF(n * n + 2);
        gridLength = n;
        numberOfOpenSites = 0;
        virtualTopID = n * n;
        virtualDownID = n * n + 1;
        this.isSiteOpen = new boolean[n * n];
        for (int i = 0; i < isSiteOpen.length; i++)
            isSiteOpen[i] = false;

        if (n>2) {
            //connect all top sites with the virtualTopID
            for (int col = 1; col <= n; col++) {
                int rowIndex = find2Dto1DPosition(1, col);
                sites.union(rowIndex, virtualTopID);
            }

            //connect all bottom sites with the virtualDownID
            for (int col = 1; col <= n; col++) {
                int rowIndex = find2Dto1DPosition(n, col);
                sites.union(rowIndex, virtualDownID);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        int currentPosition = find2Dto1DPosition(row, col);
        if (!isSiteOpen[currentPosition]) {
            ++numberOfOpenSites;
            isSiteOpen[currentPosition] = true;

            int topPosition = find2Dto1DPosition(row-1,col);
            int leftPosition = find2Dto1DPosition(row,col-1);
            int downPosition = find2Dto1DPosition(row+1,col);
            int rightPosition = find2Dto1DPosition(row,col+1);

            if(row>1 && isSiteOpen[topPosition])
                sites.union(currentPosition,topPosition);

            if(col>1 && isSiteOpen[leftPosition])
                sites.union(currentPosition,leftPosition);

            if (row<gridLength && isSiteOpen[downPosition])
                sites.union(currentPosition,downPosition);

            if (col<gridLength && isSiteOpen[rightPosition])
                sites.union(currentPosition, rightPosition);

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int currentPosition = find2Dto1DPosition(row, col);
        return this.isSiteOpen[currentPosition];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int currentPosition = find2Dto1DPosition(row, col);
        return isSiteOpen[currentPosition] && sites.find(currentPosition) == sites.find(virtualTopID);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        //check if the virtual top ID is connected to virtual down ID
        if (gridLength>2)
            return sites.find(virtualTopID) == sites.find(virtualDownID);
        else {
            return true;
        }
    }

    private int find2Dto1DPosition(int row, int col) {
        return (row - 1) * gridLength + (col - 1);
    }

    private void validate(int row,int col) {
        if (row <= 0 || row > gridLength || col <= 0 || col > gridLength)
            throw new IllegalArgumentException("Invalid row/col value sent");
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(6);

//        percolation.open(1, 1);
//        StdOut.println("percolation : " + percolation.percolates());
//        System.out.println("Is Full site: " + percolation.isFull(1, 2));
//        StdOut.println("no. of opened sites : " + percolation.numberOfOpenSites());
//        percolation.open(3, 3);
//        StdOut.println("percolation : " + percolation.percolates());
//        StdOut.println("no. of opened sites : " + percolation.numberOfOpenSites());
//
////        percolation.open(2,2);
//        StdOut.println("percolation : " + percolation.percolates());
//        StdOut.println("no. of opened sites : " + percolation.numberOfOpenSites());
//
//        System.out.println("is site open : " + percolation.isOpen(2, 2));
//        StdOut.println("elapsed time: " + stopwatch.elapsedTime());
        percolation.open(1,6);
        percolation.open(2,6);
        percolation.open(3,6);
        percolation.open(4,6);
        percolation.open(5,6);
//        System.out.println(percolation.isFull(6,6));

        percolation.open(5,5);
        percolation.open(4,4);
        percolation.open(3,4);
        percolation.open(2,4);
        percolation.open(2,3);
        percolation.open(2,2);
        percolation.open(2,1);

        percolation.open(3,1);
        percolation.open(4,1);
        percolation.open(5,1);
        percolation.open(5,2);
        percolation.open(6,2);
        percolation.open(5,4);
        System.out.println(percolation.percolates());
        System.out.println(percolation.sites.find(percolation.find2Dto1DPosition(1,6)));
        System.out.println(percolation.sites.find(percolation.find2Dto1DPosition(5,2)));
        System.out.println(percolation.isFull(2,4));

    }
}
