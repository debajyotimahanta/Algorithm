/*---------------------------------------------------------
 *Name: Debajyoti Mahanta
 * login: debajyoti.mahanta@gmail.com
 * date: Aug-27th 2012
 * This program is used to test weather a grid percolates or not using
 *- Creates an N-by-N grid of sites (intially all blocked)
 *- Reads in a sequence of sites (row i, column j) to open.
 * ---------------------------------------------------------*/

public class PercolationStats {

	// this variable is used to create a percolation instance
	private Percolation _percolation;
	// variable to hold number of times to computer
	private double _numberofCompute;
	// number of times it took before it percolated
	private double[] timestoPercolate;

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (N <= 0)
			throw new IllegalArgumentException("N cannot be zero");
		if (T <= 0)
			throw new IllegalArgumentException("T cannot be zero");
		int x;
		int y;
		_numberofCompute = T;
		timestoPercolate = new double[T];
		for (int i = 0; i < _numberofCompute; i++) {
			_percolation = new Percolation(N);
			int count = 0;
			while (!_percolation.percolates()) {
				x = StdRandom.uniform(1, N + 1);
				y = StdRandom.uniform(1, N + 1);
				if (!_percolation.isOpen(x, y)) {
					_percolation.open(x, y);
					count++;

				}

			}
			double d = (double) count / (double) (N * N);
			timestoPercolate[i] = d;
		}

	}

	public double mean() // sample mean of percolation threshold
	{
		return StdStats.mean(timestoPercolate);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(timestoPercolate);
	}

	public static void main(String[] args) // test client, described below
	{

		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		Stopwatch s = new Stopwatch();
		PercolationStats x = new PercolationStats(N, T);
		double time = s.elapsedTime();

		double mu = x.mean();
		double sigma = x.stddev();
		double confminus = mu - ((1.96 * sigma) / Math.sqrt(N));
		double confplus = mu + ((1.96 * sigma) / Math.sqrt(N));

		StdOut.println(time);
		StdOut.println(mu);
		StdOut.println(sigma);
		StdOut.print(Double.toString(confminus));
		StdOut.print(", ");
		StdOut.println(Double.toString(confplus));

	}
}