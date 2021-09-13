package coursera.algo1.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // n-by-n grid
    private final int n;

    // # of trials
    private final int trials;

    // results of percolations
    private final double[] results;

    // the average value in results
    private final double mean;

    // sample standard deviation of percolation threshold
    private final double stddev;

    // low endpoint of 95% confidence interval
    private final double cfdLow;

    // high endpoint of 95% confidence interval
    private final double cfdHigh;

    // confidence level at 95%
    private static final double CFD_CRITICAL_VAL = 1.96;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("arguments n and trials should be greater than 0");
        }

        this.n = n;
        this.trials = trials;
        this.results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation sy = new Percolation(n);
            while (!sy.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                sy.open(row, col);
            }

            double result = (double) sy.numberOfOpenSites() / (n * n);
            this.results[i] = result;
        }

        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
        this.cfdLow = this.mean - CFD_CRITICAL_VAL * (this.stddev / Math.sqrt(trials));
        this.cfdHigh = this.mean + CFD_CRITICAL_VAL * (this.stddev / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.cfdLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.cfdHigh;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        System.out.println(String.format("%-20s= %d", "n", n));
        System.out.println(String.format("%-20s= %d", "T", t));

        try {
            PercolationStats statsOne = new PercolationStats(n, t);
            System.out.println(String.format("%-20s= %f", "mean", statsOne.mean()));
            System.out.println(String.format("%-20s= %f", "stddev", statsOne.stddev()));
            System.out.println(String.format("%-20s= %f, %f", "95% confidence interval", statsOne.confidenceLo(), statsOne.confidenceHi()));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
