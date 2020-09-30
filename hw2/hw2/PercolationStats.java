package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private double[] thresholds;
    private PercolationFactory pf;

    /** Perform T independent experiments on an N-by-N grid.
     *
     * @note throw a java.lang.IllegalArgumentException if N <= 0 or T <= 0
     */
    public PercolationStats(int N, int T, PercolationFactory pf) throws IllegalArgumentException{
        if (N <= 0) {
            throw new IllegalArgumentException(
                    "N should be greater than 0 but given N = " + N + "."
            );
        }
        if (T <= 0) {
            throw new IllegalArgumentException(
                    "T should be greater than 0 but given N = " + T + "."
            );
        }

        this.N = N;
        this.T = T;
        thresholds = new double[T];
        this.pf = pf;

        simulate();
    }

    /* Run T simulations and record the results*/
    private void simulate() {
        for (int t = 0; t < T; t++) {
            Percolation system = pf.make(N);
            while (!system.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!system.isOpen(row, col)) {
                    system.open(row, col);
                }
            }
            double threshold = (double) system.numberOfOpenSites() / (N * N);
            thresholds[t] = threshold;
        }
    }

    /* Return the mean of the thresholds */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /* Return the standard deviation of thresholds*/
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /* Return the low confidence of thresholds*/
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    /* Return the high confidence of thresholds*/
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }
}
