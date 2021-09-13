package coursera.algo1;

import edu.princeton.cs.algs4.*;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    int[] cells;
    int gridLength;
    WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        if(n<=0)
            throw new IllegalArgumentException("size should be between 1 and n.");
        cells = new int[n*n];
        weightedQuickUnionUF = new WeightedQuickUnionUF(cells.length);
        gridLength = n;
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
            int topPosition = find2Dto1DPosition(row-1, col);
            weightedQuickUnionUF.union(currentPosition, topPosition);
        }
        if(col-2>=0) {
            int leftPosition = find2Dto1DPosition(row, col-1);
            weightedQuickUnionUF.union(currentPosition, leftPosition);
        }
        if(row<gridLength) {
            int downPosition = find2Dto1DPosition(row+1, col);
            weightedQuickUnionUF.union(currentPosition, downPosition);
        }
        if(col<gridLength) {
            int rightPosition = find2Dto1DPosition(row, col+1);
            weightedQuickUnionUF.union(currentPosition, rightPosition);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        int currentPosition = find2Dto1DPosition(row, col);
        if(row-2>=0) {
            int topPosition = find2Dto1DPosition(row-1, col);
            return weightedQuickUnionUF.find(currentPosition) == weightedQuickUnionUF.find(topPosition);
        }
        if(col-2>=0) {
            int leftPosition = find2Dto1DPosition(row, col-1);
            return weightedQuickUnionUF.find(currentPosition) == weightedQuickUnionUF.find(leftPosition);
        }
        if(row<gridLength) {
            int downPosition = find2Dto1DPosition(row+1, col);
            return weightedQuickUnionUF.find(currentPosition) == weightedQuickUnionUF.find(downPosition);
        }
        if(col<gridLength) {
            int rightPosition = find2Dto1DPosition(row, col+1);
            return weightedQuickUnionUF.find(currentPosition) == weightedQuickUnionUF.find(rightPosition);
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) { return true; }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int maxPosition = find2Dto1DPosition(gridLength, gridLength);
        int count=0;
        for(int i=0;i<maxPosition;i++) {
            if(weightedQuickUnionUF.find(i) == i)
                ++count;
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        int topMaxPosition = find2Dto1DPosition(0,0);
        int downMaxPosition = find2Dto1DPosition(gridLength, gridLength);

        for(int i=0;i<=topMaxPosition;i++) {
            for(int j=downMaxPosition-gridLength+1;j<=downMaxPosition;j++) {
                if(weightedQuickUnionUF.find(i) == weightedQuickUnionUF.find(j))
                    return true;
            }
        }
        return false;
    }

    public int find2Dto1DPosition(int row, int col) {
        if(row==0 && col==0) return 0;
        return (row-1) * gridLength + col - 1;
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

        percolation.open(2,2);
        StdOut.println("percolation : "+percolation.percolates());
        StdOut.println("no. of opened sites : "+percolation.numberOfOpenSites());
        StdOut.println("elapsed time: "+stopwatch.elapsedTime());
    }
}
