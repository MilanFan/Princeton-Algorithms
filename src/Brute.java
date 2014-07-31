/**
 * Princeton Algorithms Assignment 3: Pattern Recognition.
 *
 * @author Milan Fan
 *
 */
public class Brute {

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
		bruteForce(points);
		StdDraw.show(0);
	}

	/**
	 * The brute force method.
	 * @param points
	 */
	private static void bruteForce(Point[] points) {
		int N = points.length;
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				for (int k = j+1; k < N; k++) {
					if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
					for (int l = k+1; l < N; l++) {
						if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
							StdOut.println(points[i] + " -> " + points[j] + " -> "
									+ points[k] + " -> " + points[l]);
							points[i].drawTo(points[l]);
						}
					}
				}
			}
		}
	}

}
