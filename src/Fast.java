import java.util.Arrays;

/**
 * Princeton Algorithms Assignment 3: Pattern Recognition.
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
	 * Check if the point visited before, to avoid subsegments output.
	 * @param points
	 * @param i
	 * @param q
	 * @return
	 */
	private static boolean isVisited(double[] slopes, int len, double slp) {
		for (int i = 0; i < len; i++) {
			if (Double.compare(slopes[i], slp) == 0) return true;
		}
		return false;		
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
			
			Point[] others = new Point[N-i-1];
			for (int j = i+1; j < N; j++) {
				others[j-i-1] = points[j];
			}
			Arrays.sort(others, p.SLOPE_ORDER);
			
			double[] slopes = new double[i];
			for (int j = 0; j < i; j++) {
				slopes[j] = p.slopeTo(points[j]);
			}
			
			for (int j = 1, pos = 0, count = 0; j < others.length; j++) {
				if (isVisited(slopes, i, p.slopeTo(others[j]))) continue;
				boolean flag = Double.compare(p.slopeTo(others[pos]), p.slopeTo(others[j])) == 0;
				if (flag) {
					count++;
				} 
				if (!flag || j == others.length-1) {
					if (count > 1) {
						StdOut.print(p);
						for (int k = 0; k <= count; k++) {
							StdOut.print(" -> " + others[pos+k]);
						}
						StdOut.println();
						p.drawTo(others[pos+count]);
					}
					pos = j;
					count = 0;
				}
			}
		}
	}
}
