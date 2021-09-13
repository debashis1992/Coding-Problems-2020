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
        virtualDownID = n + n + 1;
        this.isSiteOpen = new boolean[n * n];
        for (int i = 0; i < isSiteOpen.length; i++)
            isSiteOpen[i] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        int currentPosition = find2Dto1DPosition(row, col);
        if (!isSiteOpen[currentPosition]) {
            int topPosition = find2Dto1DPosition(row - 1, col);
            int leftPosition = find2Dto1DPosition(row, col - 1);
            int downPosition = find2Dto1DPosition(row + 1, col);
            int rightPosition = find2Dto1DPosition(row, col + 1);

            if (row - 2 >= 0 && isSiteOpen[topPosition])
                sites.union(currentPosition, topPosition);

            if (col - 2 >= 0 && isSiteOpen[leftPosition])
                sites.union(currentPosition, leftPosition);

            if (row < gridLength && isSiteOpen[downPosition])
                sites.union(currentPosition, downPosition);

            if (col < gridLength && isSiteOpen[rightPosition])
                sites.union(currentPosition, rightPosition);

            if (row == 1)
                sites.union(currentPosition, virtualTopID);
            if (row == gridLength)
                sites.union(currentPosition, virtualDownID);

            ++numberOfOpenSites;
            isSiteOpen[currentPosition] = true;
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
        return this.isSiteOpen[currentPosition] && this.sites.find(currentPosition) == this.sites.find(this.virtualTopID);
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
        if (row == 0 && col == 0) return 0;
        return (row - 1) * gridLength + (col - 1);
    }

    private void validate(int row,int col) {
        if (row <= 0 || row > gridLength || col <= 0 || col > gridLength)
            throw new IllegalArgumentException("Invalid row/col value sent");
    }

    // test client (optional)
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        Percolation percolation = new Percolation(3);

        percolation.open(1, 1);
        StdOut.println("percolation : " + percolation.percolates());
        System.out.println("Is Full site: " + percolation.isFull(1, 2));
        StdOut.println("no. of opened sites : " + percolation.numberOfOpenSites());
        percolation.open(3, 3);
        StdOut.println("percolation : " + percolation.percolates());
        StdOut.println("no. of opened sites : " + percolation.numberOfOpenSites());

//        percolation.open(2,2);
        StdOut.println("percolation : " + percolation.percolates());
        StdOut.println("no. of opened sites : " + percolation.numberOfOpenSites());

        System.out.println("is site open : " + percolation.isOpen(2, 2));
        StdOut.println("elapsed time: " + stopwatch.elapsedTime());
    }
}
