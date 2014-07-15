/**
 * Created with IntelliJ IDEA.
 * User: rshen
 * Date: 7/13/14
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class PercolationStats {
    private double[] threshold;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        threshold = new double[T];
        int repeatTime = 0;
        while (repeatTime < T) {
            Percolation test = new Percolation(N);
            double openSiteCount = 0;
            while (!test.percolates())  {
                int chosenSite = StdRandom.uniform(0, N*N-1);
                int coordinateX = chosenSite/N + 1;
                int coordinateY = chosenSite % N + 1;
                if (!test.isOpen(coordinateX, coordinateY)) {
                    test.open(coordinateX, coordinateY);
                    openSiteCount++;
                }
            }
            threshold[repeatTime] =  openSiteCount/(N*N);
            repeatTime++;
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
         return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
          return mean() - 1.96*stddev()/Math.sqrt(threshold.length);
    }

    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(threshold.length);
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            int gridSize = Integer.parseInt(args[0]);
            int counts = Integer.parseInt(args[1]);
            PercolationStats simulation = new PercolationStats(gridSize, counts);
            StdOut.printf("mean                       =%f\n", simulation.mean());
            StdOut.printf("stddev                     =%f\n", simulation.stddev());
            StdOut.printf("95%% confidence interval    =%f, %f\n", simulation.confidenceLo(), simulation.confidenceHi());
        }   else {
            throw new IllegalArgumentException();
        }
        return;
    }
}
