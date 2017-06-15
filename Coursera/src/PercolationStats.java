import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int[] times;
    private final int size;

    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) { throw new IllegalArgumentException(); }
        times = new int[trials];
        size = n;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int tries = 0;
            while (!percolation.percolates()) {
                int a = StdRandom.uniform(1, n + 1), b = StdRandom.uniform(1, n + 1);
                // System.out.println(String.format("(%d, %d)", a, b));
                if(percolation.isOpen(a, b)) continue;
                percolation.open(a, b); tries++;
            } times[i] = tries;
        }
    }

    /**
     * test client
     */
    public static void main(String[] args) {
        System.out.println(new PercolationStats(200, 100));
    }

    @Override
    public String toString() {
        return String.format(
            "mean                    = %f\nstddev                  = %f\n95%% confidence interval = [%f, %f]", mean(),
            stddev(), confidenceLo(), confidenceHi());
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(times) / size / size;
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(times) / size / size;
    }

    /**
     * low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(times.length);
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() { return mean() + 1.96 * stddev() / Math.sqrt(times.length);}
}
