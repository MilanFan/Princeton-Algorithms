/**
 * Princeton Algorithms Assignment 5: Kd-Trees
 * 
 * @author Milan Fan
 *
 */
public class PointSET {
	private SET<Point2D> ps;

	// construct an empty set of points
	public PointSET() {
		ps = new SET<Point2D>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return ps.isEmpty();
	}

	// number of points in the set
	public int size() {
		return ps.size();
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p != null) ps.add(p);
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		if (p != null) return ps.contains(p);
		return false;
	}

	// draw all of the points to standard draw
	public void draw() {
		if (!isEmpty()) {
			for (Point2D p : ps) {
				p.draw();
			}
		}
	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (isEmpty() || rect == null) return null;
		
		Queue<Point2D> q = new Queue<Point2D>();
		for (Point2D p : ps) {
			if (rect.contains(p))
				q.enqueue(p);
		}
		return q;
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		if (isEmpty()) return null;

		Point2D n = ps.min();
		for (Point2D p2 : ps) {
			if (p.distanceSquaredTo(p2) < p.distanceSquaredTo(n))
				n = p2;
		}
		return n;
	}
}
