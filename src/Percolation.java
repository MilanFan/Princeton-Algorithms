/**
 * Princeton Algorithms Assignment 1: Percolation.
 *
 * @author Milan Fan
 *
 */
public class Percolation {

	private boolean[][] grid;			// N-by-N grid of site
	private WeightedQuickUnionUF uf;	// union-find object
	private WeightedQuickUnionUF ufFull;// union-find object for full size check
	private int num;					// length of grid size N

	/**
	 * Create N-by-N grid, with all sites blocked.
	 * @param N length of grid side
	 */
	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException(Integer.toString(N));
		}

		grid = new boolean[N][N];
		uf = new WeightedQuickUnionUF(N * N + 2);
		ufFull = new WeightedQuickUnionUF(N * N + 1);
		num = N;
		for (int j = 1; j <= N; j++) {
			uf.union(0, mapGridToUF(1, j));
			uf.union(N * N + 1, mapGridToUF(N, j));
			ufFull.union(0, mapGridToUF(1, j));
		}
	}

	/**
	 * @param i site row
	 * @param j site column
	 * @return true if index out of bounds; false otherwise
	 */
	private boolean isIndexOutOfBounds(int i, int j) {
		return i < 1 || j < 1 || i > num || j > num;
	}

	/**
	 * Map site (row i, column j) in grid to its index in uf.
	 * index 0 is the source, index N*N+1 is the sink
	 * @param i site row
	 * @param j site column
	 * @return site index in uf
	 */
	private int mapGridToUF(int i, int j) {
		if (isIndexOutOfBounds(i, j)) { return -1; }
		return (i - 1) * num + j;
	}

	/**
	 * @param i adjacent site row
	 * @param j adjacent site column
	 * @param cur site index in uf
	 */
	private void unionAdjacent(int i, int j, int cur) {
		int adj = mapGridToUF(i, j);
		if (adj > 0) {
			if (isOpen(i, j)) {
				uf.union(cur, adj);
				ufFull.union(cur, adj);
			}
		}
	}

	/**
	 * Open site (row i, column j) if it is not already.
	 * @param i row
	 * @param j column
	 */
	public void open(int i, int j) {
		if (!isOpen(i, j)) {
			grid[i - 1][j - 1] = true;
		}
		int cur = mapGridToUF(i, j);
		unionAdjacent(i - 1, j, cur);
		unionAdjacent(i + 1, j, cur);
		unionAdjacent(i, j - 1, cur);
		unionAdjacent(i, j + 1, cur);
	}

	/**
	 * Is site (row i, column j) open?
	 * @param i row
	 * @param j column
	 * @return true if the site open; false otherwise.
	 */
	public boolean isOpen(int i, int j) {
		if (isIndexOutOfBounds(i, j)) {
			throw new IndexOutOfBoundsException();
		}

		return grid[i - 1][j - 1];
	}

	/**
	 * Is site (row i, column j) full?
	 * @param i row
	 * @param j column
	 * @return true is the site full; false otherwise
	 */
	public boolean isFull(int i, int j) {
		if (isIndexOutOfBounds(i, j)) {
			throw new IndexOutOfBoundsException();
		}

		if (!isOpen(i, j)) { return false; }
		return ufFull.connected(0, mapGridToUF(i, j));
	}

	/**
	 * Does the system percolate?
	 * @return true if the system percolate; false otherwise
	 */
	public boolean percolates() {
		if (1 == num) { return isOpen(1, 1); }
		return uf.connected(0, num * num + 1);
	}
}
