import java.util.Arrays;

/**
 * Princeton Algorithms Assignment 3: Pattern Recognition
 *
 * @author Milan Fan
 *
 */
public class Fast {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		In in = new In(args[0]);
		int N = in.readInt();
		if (N < 4) return;
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			Point p = new Point(in.readInt(), in.readInt());
			points[i] = p;
			p.draw();
		}
		Quick.sort(points);
		fast(points);
	}

	/**
	 * The fast method.
	 * @param points
	 */
	private static void fast(Point[] points) {
		int N = points.length;
		assert N >= 4;
		for (int i = 0; i < N; i++) {
			Point p = points[i];

			Point[] others = new Point[N - 1];
			for (int j = 0; j < N; j++) {
				if (j < i) others[j] = points[j];
				if (j > i) others[j - 1] = points[j];
			}
			Arrays.sort(others, p.SLOPE_ORDER);

			for (int j = 1, pos = 0, count = 0; j < others.length; j++) {
				boolean flag = Double.compare(p.slopeTo(others[pos]), p.slopeTo(others[j])) == 0;
				if (flag) {
					count++;
				}
				if (!flag || j == others.length - 1) {
					if (count > 1 && others[pos].compareTo(p) >= 0) {
						StdOut.print(p);
						for (int k = 0; k <= count; k++) {
							StdOut.print(" -> " + others[pos + k]);
						}
						StdOut.println();
						p.drawTo(others[pos + count]);
					}
					pos = j;
					count = 0;
				}
			}
		}
	}
}
