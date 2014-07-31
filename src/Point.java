/*************************************************************************
 * 
 * Princeton Algorithms Assignment 3: Pattern Recognition.
 * 
 * @author Milan Fan
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;   // x coordinate
    private final int y;   // y coordinate
    
    private class SlopeOrder implements Comparator<Point>
    {
		@Override
		public int compare(Point p1, Point p2) {
			double s1 = slopeTo(p1);
			double s2 = slopeTo(p2);
			if (s1 < s2) return -1;
			else if (s1 > s2) return 1;
			return 0;
		}
    }

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	double dx = (double) (that.x - this.x);
    	double dy = (double) (that.y - this.y);
    	if (0 == dx && 0 == dy) return Double.NEGATIVE_INFINITY;
    	if (0 == dx) return Double.POSITIVE_INFINITY;
    	if (0 == dy) return +0.0;
		return dy / dx; 
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
    	if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
    	else if (this.x == that.x && this.y == that.y) return 0;
		return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
