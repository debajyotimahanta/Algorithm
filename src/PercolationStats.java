/*---------------------------------------------------------
 *Name: Debajyoti Mahanta
 * login: debajyoti.mahanta@gmail.com
 * date: Aug-27th 2012
 * This program is used to test weather a grid percolates or not using
 *- Creates an N-by-N grid of sites (initially all blocked)
 *- Reads in a sequence of sites (row i, column j) to open.
 * ---------------------------------------------------------*/

public class PercolationStats {
    private Percolation objPercolation;
    // variable to hold number of times to computer
    private double numberofCompute;
    // number of times it took before it percolated
    private double[] timestoPercolate;

    // perform T independent computational experiments on an N-by-N grid

    /*---------------------------------------------------------
     * This is the constructed that takes
     * the grid size Gridsize and number of time to compute as Times
     * It throws exception if any of them is less then 0
     * It sets the private variable
     * it looks through top array and check if they are connected.
     * ---------------------------------------------------------*/
    public PercolationStats(final int gridSize, final int times) {
        if (gridSize <= 0)
            throw new IllegalArgumentException("Gridsize cannot be zero");
        if (times <= 0)
            throw new IllegalArgumentException("Times cannot be zero");
        int x;
        int y;
        numberofCompute = times;
        timestoPercolate = new double[times];
        for (int i = 0; i < numberofCompute; i++) {
            objPercolation = new Percolation(gridSize);
            int count = 0;
            while (!objPercolation.percolates()) {
                x = StdRandom.uniform(1, gridSize + 1);
                y = StdRandom.uniform(1, gridSize + 1);
                if (!objPercolation.isOpen(x, y)) {
                    objPercolation.open(x, y);
                    count++;

                }

            }
            double d = (double) count / (double) (gridSize * gridSize);
            timestoPercolate[i] = d;
        }

    }

    /*---------------------------------------------------------
     * Get the mean of all the percolation threshold
     * ---------------------------------------------------------*/
    public double mean() {
        return StdStats.mean(timestoPercolate);
    }

    /*---------------------------------------------------------
     *  sample standard deviation of percolation threshold
     * ---------------------------------------------------------*/
    public double stddev() {
        return StdStats.stddev(timestoPercolate);
    }

    /*---------------------------------------------------------
     *  the main method used for testing via command line
     * ---------------------------------------------------------*/
    public static void main(String[] args) // test client, described below
    {

        int size = Integer.parseInt(args[0]);
        int times = Integer.parseInt(args[1]);
        Stopwatch s = new Stopwatch();
        PercolationStats x = new PercolationStats(size, times);
        double time = s.elapsedTime();

        double mu = x.mean();
        double sigma = x.stddev();
        double confminus = mu - ((1.96 * sigma) / Math.sqrt(size));
        double confplus = mu + ((1.96 * sigma) / Math.sqrt(times));

        StdOut.println(time);
        StdOut.println(mu);
        StdOut.println(sigma);
        StdOut.print(Double.toString(confminus));
        StdOut.print(", ");
        StdOut.println(Double.toString(confplus));

        // Percolation x = new Percolation(1);
        // boolean y = x.percolates();
        // x.open(1, 1);
        // y = x.percolates();

    }
}