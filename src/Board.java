/**
 * Princeton Algorithms Assignment 4: 8 Puzzle
 * 
 * @author Milan Fan
 *
 */
public class Board {

	private int[][] grid;
	private int N;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		N = blocks.length;
		assert N > 1;
		grid = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				grid[i][j] = blocks[i][j];
			}
		}
	}

    // board dimension N
	public int dimension() {
		return N;
	}

	// number of blocks out of place
	public int hamming() {
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] != 0 && grid[i][j] != i * N + j + 1) {
					count++;
				}
			}
		}
		return count;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] != 0) {
					count += Math.abs(i - (grid[i][j] - 1) / N)
							+ Math.abs(j - (grid[i][j] - 1) % N);
				}
			}
		}
		return count;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == N - 1 && j == N - 1) break;
				if (grid[i][j] != i * N + j + 1) {
					return false;
				}
			}
		}
		return true;
	}

	// a board obtained by exchanging two adjacent blocks in the same row
	public Board twin() {
		int[][] t = copy();
		while (true) {
			int row = StdRandom.uniform(N);
			int col = StdRandom.uniform(N - 1);
			if (t[row][col] != 0 && t[row][col + 1] != 0) {
				swap(t, row, col, row, col + 1);
				break;
			}
		}
		return new Board(t);
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;

		Board that = (Board) y;
		if (N != that.N) return false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] != that.grid[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		Queue<Board> q = new Queue<Board>();

		int row = 0;
		int col = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == 0) {
					row = i;
					col = j;
				}
			}
		}

		int[][] t = copy();

		// up
		if (row > 0) {
			swap(t, row, col, row - 1, col);
			q.enqueue(new Board(t));
			swap(t, row - 1, col, row, col);
		}

		// down
		if (row < N - 1) {
			swap(t, row, col, row + 1, col);
			q.enqueue(new Board(t));
			swap(t, row + 1, col, row, col);
		}

		// left
		if (col > 0) {
			swap(t, row, col, row, col - 1);
			q.enqueue(new Board(t));
			swap(t, row, col - 1, row, col);
		}

		// right
		if (col < N - 1) {
			swap(t, row, col, row, col + 1);
			q.enqueue(new Board(t));
			swap(t, row, col + 1, row, col);
		}

		return q;
	}

	// string representation of the board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", grid[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	// copy grid
	private int[][] copy() {
		int[][] t = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				t[i][j] = grid[i][j];
			}
		}
		return t;
	}

	// swap two elements in an array
	private void swap(int[][] a, int x1, int y1, int x2, int y2) {
		int tmp = a[x1][y1];
		a[x1][y1] = a[x2][y2];
		a[x2][y2] = tmp;
	}
}
