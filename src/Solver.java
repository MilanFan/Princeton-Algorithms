/**
 * Princeton Algorithms Assignment 4: 8 Puzzle
 *
 * @author Milan Fan
 *
 */
public class Solver {

	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private int moves;
		private SearchNode previous;

		public SearchNode(Board b, int m, SearchNode p) {
			board = b;
			moves = m;
			previous = p;
		}

		@Override
		public int compareTo(SearchNode that) {
			return board.manhattan() + moves
					- (that.board.manhattan() + that.moves);
		}
	}

	private MinPQ<SearchNode> solution;
	private MinPQ<SearchNode> twin;
	private boolean solvable;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		solution = new MinPQ<SearchNode>();
		solution.insert(new SearchNode(initial, 0, null));

		twin = new MinPQ<SearchNode>();
		twin.insert(new SearchNode(initial.twin(), 0, null));

		while (!isDone()) {
			SearchNode n = solution.delMin();
			for (Board b : n.board.neighbors()) {
				if (n.previous == null || !b.equals(n.previous.board)) {
					solution.insert(new SearchNode(b, n.moves + 1, n));
				}
			}

			SearchNode t = twin.delMin();
			for (Board b : t.board.neighbors()) {
				if (t.previous == null || !b.equals(t.previous.board)) {
					twin.insert(new SearchNode(b, t.moves + 1, t));
				}
			}
		}

		solvable = solution.min().board.isGoal();
	}

	private boolean isDone() {
		if (solution.min().board.isGoal()) return true;
		if (twin.min().board.isGoal()) return true;
		return false;
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return solvable;		
	}

	// min number of moves to solve initial board; -1 if no solution
	public int moves() {
		if (!solvable) return -1;
		return solution.min().moves;
	}

	// sequence of boards in a shortest solution; null if no solution
	public Iterable<Board> solution() {
		if (!solvable) return null;

		Stack<Board> q = new Stack<Board>();
		for (SearchNode n = solution.min(); n != null; n = n.previous)
			q.push(n.board);
		return q;
	}

	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
