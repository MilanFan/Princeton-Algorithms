/**
 * Princeton Algorithms Assignment 1: Percolation
 *
 * @author Milan Fan
 *
 */
public class PercolationStats {

	private double[] fraction;	// fraction of sites that are opened when the system percolates
	private int times;			// number of independent computational experiments to be performed

	/**
	 * Perform T independent computational experiments on an N-by-N grid.
	 * @param N length of grid side
	 * @param T number of independent computational experiments to be performed
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0) { throw new IllegalArgumentException(Integer.toString(N)); }
		if (T <= 0) { throw new IllegalArgumentException(Integer.toString(T)); }

		fraction = new double[T];
		times = T;
		for (int t = 0; t < T; t++) {
			Percolation pc = new Percolation(N);
			int count = 0;
			while (!pc.percolates()) {
				int i, j;
				do {
					int r = (int) Math.floor(Math.random() * N * N);
					i = r / N + 1;
					j = r % N + 1;
				} while (pc.isOpen(i, j));
				pc.open(i, j);
				count++;
			}
			fraction[t] = ((double) count) / (N * N);
		}
	}

	/**
	 * Sample mean of percolation threshold.
	 * @return sample mean
	 */
	public double mean() {
		double sum = 0.0;
		for (int i = 0; i < times; i++) {
			sum += fraction[i];
		}
		return sum / times;
	}

	/**
	 * Sample standard deviation of percolation threshold.
	 * @return standard deviation
	 */
	public double stddev() {
		if (1 == times) { return Double.NaN; }
		double m = mean();
		double sum = 0.0;
		for (int i = 0; i < times; i++) {
			sum += Math.pow(fraction[i] - m, 2.0);
		}
		return Math.sqrt(sum / (times - 1));
	}

	/**
	 * Returns lower bound of the 95% confidence interval.
	 * @return lower bound of the 95% confidence interval
	 */
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(times);
	}

	/**
	 * Returns upper bound of the 95% confidence interval.
	 * @return upper bound of the 95% confidence interval
	 */
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(times);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int N = StdIn.readInt();
		int T = StdIn.readInt();
		PercolationStats ps = new PercolationStats(N, T);
		StdOut.println("mean\t\t\t= " + ps.mean());
		StdOut.println("stddev\t\t\t= " + ps.stddev());
		StdOut.println("95% confidence interval\t= "
				+ ps.confidenceLo() + ", "
				+ ps.confidenceHi());
	}
}
